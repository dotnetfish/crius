/*

package com.farsunset.lvxin.ui.trend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.TypeReference;
import com.farsunset.cim.client.constant.CIMConstant;
import com.farsunset.lvxin.R;
import com.farsunset.lvxin.app.ClientConfig;
import com.farsunset.lvxin.app.Constant;
import com.farsunset.lvxin.app.Global;
import com.farsunset.lvxin.app.GlobalMediaPlayer;
import com.farsunset.lvxin.bean.Bottle;
import com.farsunset.lvxin.bean.Page;
import com.farsunset.lvxin.bean.User;
import com.farsunset.lvxin.db.BottleDBManager;
import com.farsunset.lvxin.db.FriendDBManager;
import com.farsunset.lvxin.db.MessageDBManager;
import com.farsunset.lvxin.network.FileUploader;
import com.farsunset.lvxin.network.HttpAPIRequester;
import com.farsunset.lvxin.network.HttpAPIResponser;
import com.farsunset.lvxin.network.OSSFileLoader;
import com.farsunset.lvxin.ui.base.CIMMonitorActivity;
import com.farsunset.lvxin.util.AnimationTools;
import com.farsunset.lvxin.util.AppTools;
import com.farsunset.lvxin.util.StringUtils;
import com.farsunset.lvxin.widget.BottlePreviewDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DriftBottleActivity extends CIMMonitorActivity implements HttpAPIResponser, BottlePreviewDialog.OnHandleListener {
    Bottle bottle;
    BottlePreviewDialog bottleDialog;
    ImageView emptyBottle;
    public long endTime;
    boolean isVoiceRecording;
    private MediaRecorder mediaRecorder;
    private EditText messageEditText;
    private TextView newBottleMsgCountLabel;
    DriftBottleActivity.RecordBroadcastReceiver recordBroadcastReceiver;
    PendingIntent recordPendingIntent;
    Intent recordReceiverIntent;
    TextView recordTime;
    TextView recordingHint;
    HttpAPIRequester requester;
    ImageView spoondrift;
    public long startTime;
    AnimationSet throwAnimation;
    Button throwButton;
    User user;
    private File voiceFile;
    View voiceRecordingPanel;
    
    public void onClick(View v) {
        if((v.getId() == R.id.tab_throw_bottle) && (isReady())) {
            findViewById(R.id.view_throw_bottle).setVisibility(View.VISIBLE);
            findViewById(R.id.emptyBottle).setVisibility(View.GONE);
            findViewById(R.id.view_bottle_table).setVisibility(View.GONE);
            messageEditText.setVisibility(View.GONE);
            findViewById(R.id.bottomThrowView).setVisibility(View.VISIBLE);
            findViewById(R.id.keyboardSwitchButton).setVisibility(View.VISIBLE);
            findViewById(R.id.voiceSwitchButton).setVisibility(View.GONE);
            throwButton.setText(R.string.text_bottle_press_record);
        }
        if((v.getId() == R.id.tab_get_bottle) && (isReady())) {
            findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night_light);
            spoondrift.setVisibility(View.VISIBLE);
            requester.execute(new TypeReference<DriftBottleActivity>() {}.getType(), null, BOTTLE_GET_URL);
        }
        if((v.getId() == R.id.tab_bottle_record) && (isReady())) {
            startActivity(new Intent(this, BottleListActivity.class));
        }
        if(v.getId() == R.id.voiceSwitchButton) {
            v.setVisibility(View.GONE);
            findViewById(R.id.keyboardSwitchButton).setVisibility(View.VISIBLE);
            throwButton.setText(R.string.text_bottle_press_record);
            messageEditText.setVisibility(View.GONE);
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(messageEditText.getWindowToken(), 0x0);
        }
        if(v.getId() == R.id.keyboardSwitchButton) {
            v.setVisibility(View.GONE);
            throwButton.setText(R.string.text_bottle_throw);
            findViewById(R.id.voiceSwitchButton).setVisibility(View.VISIBLE);
            messageEditText.setVisibility(View.VISIBLE);
        }
        if((v.getId() == R.id.throwButton) && (!TextUtils.isEmpty(messageEditText.getText().toString()))) {
            ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(messageEditText.getWindowToken(), 0x0);
            apiParams.put("content", messageEditText.getText().toString());
            apiParams.put("type", Integer.valueOf(0x0));
            apiParams.remove("gid");
            throwBottle(BOTTLE_THROW_URL);
        }
        if((v.getId() == R.id.bottle_paper) || (v.getId() == R.id.bottle_voice)) {
            bottleDialog.showBottle(bottle);
            bottleDialog.show();
            spoondrift.setVisibility(View.GONE);
            findViewById(R.id.bottle_starfish).setVisibility(View.GONE);
            findViewById(R.id.bottle_paper).setVisibility(View.GONE);
            findViewById(R.id.bottle_voice).setVisibility(View.GONE);
        }
    }
    
    private boolean isReady() {
        if(spoondrift.getVisibility() == View.VISIBLE) {
            return false;
        }
        if(findViewById(R.id.bottle_starfish).getVisibility() != View.VISIBLE) {
            if(findViewById(R.id.bottle_paper).getVisibility() != View.VISIBLE) {
                boolean localboolean1 = findViewById(R.id.bottle_voice).getVisibility() !=View.VISIBLE;
                return  localboolean1;
            }
        }
        return false;
    }
    
    private void throwBottle(final String url) {
        findViewById(R.id.emptyBottle).setVisibility(View.VISIBLE);
        findViewById(R.id.letterPaper).setVisibility(View.VISIBLE);
        findViewById(R.id.bottleBox).setVisibility(View.VISIBLE);
        findViewById(R.id.view_throw_bottle).setBackgroundResource(R.color.transparent);
        findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night_light);
        findViewById(R.id.bottomThrowView).setVisibility(View.GONE);
        messageEditText.setVisibility(View.GONE);
        throwAnimation = (AnimationSet)AnimationUtils.loadAnimation(this, R.anim.bottile_paper);
        int[] loc1 = new int[0x2];
        findViewById(R.id.bottleBox).getLocationInWindow(loc1);
        loc1[0x2] = (getResources().getDisplayMetrics().widthPixels / 0x3);
        loc1[0x3] = (getResources().getDisplayMetrics().heightPixels / 0x4);
        TranslateAnimation t = new TranslateAnimation(0.0f, (float)-loc1[0x2], 0.0f, (float)-loc1[0x3]);
        throwAnimation.addAnimation(t);
        AnimationTools.start(throwAnimation, findViewById(R.id.bottleBox), new AnimationTools.OnAnimationListener() {
            

            
            public void onAnimationEnd(View view) {
                findViewById(R.id.bottleBox).setVisibility(View.GONE);
            }
            
            public void onAnimationStart(View view) {
                requester.execute(null, null, url);
            }
        });
       final AlphaAnimation at = new AlphaAnimation(0.0f, 0.0f);
        at.setDuration(0x5dc);
        AnimationTools.start(at, findViewById(R.id.rootView), new AnimationTools.OnAnimationListener() {
            

            public void onAnimationEnd(View view) {
                spoondrift.setVisibility(View.VISIBLE);
                AnimationTools.start(at, findViewById(R.id.rootView), new AnimationTools.OnAnimationListener() {

                    public void onAnimationEnd(View view) {
                        DriftBottleActivity.this.hideThrowView();
                    }
                    
                    public void onAnimationStart(View view) {
                    }
                });
            }
            
            public void onAnimationStart(View view) {
            }
        });
    }
    
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean bool2 = false;
        if (findViewById(R.id.view_throw_bottle).getVisibility() != 0) {
            return super.dispatchTouchEvent(event);
        }
        int[] arrayOfInt1 = new int[4];
        int[] locationArray=new int[2];
        this.messageEditText.getLocationInWindow(locationArray);
        arrayOfInt1[0]=locationArray[0];
        arrayOfInt1[1]=locationArray[1];
        arrayOfInt1[2] = (arrayOfInt1[0] + this.messageEditText.getWidth());
        arrayOfInt1[3] = (arrayOfInt1[1] + this.messageEditText.getHeight());
        int[] arrayOfInt2 = new int[4];
        findViewById(R.id.bottomThrowView).getLocationInWindow(locationArray);
        arrayOfInt2[0]=locationArray[0];
        arrayOfInt2[1]=locationArray[1];
        arrayOfInt2[2] = (arrayOfInt2[0] + findViewById(R.id.bottomThrowView).getWidth());
        arrayOfInt2[3] = (arrayOfInt2[1] + findViewById(R.id.bottomThrowView).getHeight());
        int[] arrayOfInt3 = new int[4];
        this.throwButton.getLocationInWindow(locationArray);
        arrayOfInt3[0]=locationArray[0];
        arrayOfInt3[1]=locationArray[1];
        arrayOfInt3[2] = (arrayOfInt2[0] + this.throwButton.getWidth());
        arrayOfInt3[3] = (arrayOfInt2[1] + this.throwButton.getHeight());
        switch (event.getAction())
        {
        }
        //for (;;)
      //  {

            if ((!AppTools.contains(arrayOfInt1, event)) && (!AppTools.contains(arrayOfInt2, event)))
            {
                //hideThrowView();
                return super.dispatchTouchEvent(event);
            }
            if ((AppTools.contains(arrayOfInt3, event)) && (this.messageEditText.getVisibility() == View.GONE))
            {
                GlobalMediaPlayer.getPlayer().start(R.raw.startrecord);
                ((AlarmManager)getSystemService(Context.ALARM_SERVICE)).setRepeating(0, SystemClock.elapsedRealtime() + 1000L, 1000L, this.recordPendingIntent);
                this.isVoiceRecording = true;
                this.throwButton.setSelected(true);
                this.throwButton.setText(R.string.label_chat_soundrecord_pressed);
                this.recordingHint.setText(R.string.label_chat_soundcancle);
                try
                {
                    this.voiceRecordingPanel.setVisibility(View.VISIBLE);
                    this.mediaRecorder = new MediaRecorder();
                    this.voiceFile = new File(Constant.CACHE_DIR_VOICE + "/" + StringUtils.getUUID());
                    this.voiceFile.createNewFile();
                    this.mediaRecorder.setAudioSource(1);
                    this.mediaRecorder.setOutputFormat(3);
                    this.mediaRecorder.setAudioEncoder(1);
                    this.mediaRecorder.setOutputFile(this.voiceFile.getAbsolutePath());
                    this.mediaRecorder.prepare();
                    this.startTime = System.currentTimeMillis();
                    this.mediaRecorder.start();
                }
                catch (IOException localIOException)
                {
                    localIOException.printStackTrace();
                }
                //continue;
                if (this.isVoiceRecording) {
                    if (!AppTools.contains(arrayOfInt3, event))
                    {
                        this.recordingHint.setText(R.string.label_chat_unlashcancle);
                        findViewById(R.id.recordingBanner).setSelected(true);
                        this.throwButton.setSelected(false);
                    }
                    else
                    {
                        this.recordingHint.setText(R.string.label_chat_soundcancle);
                        findViewById(R.id.recordingBanner).setSelected(false);
                        this.throwButton.setSelected(true);
                        //continue;
                        this.endTime = System.currentTimeMillis();
                        boolean bool1 = bool2;
                        if (AppTools.contains(arrayOfInt3, event))
                        {
                            bool1 = bool2;
                            if ((this.endTime - this.startTime) / 1000L > 0L) {
                                bool1 = true;
                            }
                        }
                        recordCompleted(bool1);
                    }
                }
            }
        //}
        return super.dispatchTouchEvent(event);
    }
    
    private void recordCompleted(boolean isValid) {
        if(isVoiceRecording) {
            ( (AlarmManager)getSystemService(Context.ALARM_SERVICE)).cancel(recordPendingIntent);
            try {
                mediaRecorder.stop();
                mediaRecorder.release();
            } catch(Exception localException1) {
            }
            throwButton.setSelected(false);
            voiceRecordingPanel.setVisibility(View.GONE);
            throwButton.setText(R.string.label_chat_soundrecord_normal);
            if(isValid) {
                apiParams.put("content", voiceFile.getName());
                apiParams.put("type", Integer.valueOf(0x1));
                apiParams.put("length", Long.valueOf(((((endTime - startTime) - 0x3e8) / 0x3e8) + 0x1)));
                apiParams.remove("gid");
                throwBottle(BOTTLE_THROW_URL);
                FileUploader.asyncUpload("lvxin-files", voiceFile.getName(), voiceFile);
            } else {
                //org.apache.commons.io.FileUtils.deleteQuietly(voiceFile);
                try {
                    FileUtils.forceDelete(voiceFile);
                }catch (IOException io){
                    Log.e("record not existed","",io);
                }
            }
        }
        isVoiceRecording = false;
    }
    
    public void hideThrowView() {
        spoondrift.setVisibility(View.GONE);
        findViewById(R.id.view_bottle_table).setVisibility(View.VISIBLE);
        findViewById(R.id.view_throw_bottle).setVisibility(View.GONE);
        findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night);
    }
    
    public void initComponents() {
        ImageLoader.getInstance().clearMemoryCache();
        getActionBar().hide();
        user = Global.getCurrentUser();
        messageEditText = (EditText)findViewById(R.id.messageEditText);
        throwButton = (Button)findViewById(R.id.throwButton);
        emptyBottle = (ImageView)findViewById(R.id.emptyBottle);
        spoondrift = (ImageView)findViewById(R.id.spoondrift);
        voiceRecordingPanel = findViewById(R.id.recordingPanelView);
        recordTime = (TextView)findViewById(R.id.recordingTime);
        recordingHint = (TextView)findViewById(R.id.recordingHint);
        newBottleMsgCountLabel = (TextView)findViewById(R.id.badge_bottle_unread_count);
        findViewById(R.id.tab_bottle_record).setOnClickListener(this);
        findViewById(R.id.tab_throw_bottle).setOnClickListener(this);
        findViewById(R.id.tab_get_bottle).setOnClickListener(this);
        findViewById(R.id.voiceSwitchButton).setOnClickListener(this);
        findViewById(R.id.keyboardSwitchButton).setOnClickListener(this);
        findViewById(R.id.throwButton).setOnClickListener(this);
        findViewById(R.id.bottle_paper).setOnClickListener(this);
        findViewById(R.id.bottle_voice).setOnClickListener(this);
        requester = new HttpAPIRequester(this);
        recordBroadcastReceiver = new DriftBottleActivity.RecordBroadcastReceiver();
        registerReceiver(recordBroadcastReceiver, recordBroadcastReceiver.getIntentFilter());
        recordReceiverIntent = new Intent("ACTION_RECORDING");
        recordPendingIntent = PendingIntent.getBroadcast(this, 0x0, recordReceiverIntent, 0x0);
        bottleDialog = new BottlePreviewDialog(this,this);
    }
    
    public int getConentLayout() {
        return R.layout.activity_drift_bottle;
    }
    
    public int getActionBarTitle() {
        return R.string.label_function_bottle;
    }
    
    public void onSuccess(Object data, List list, Page page, String code, String url) {
        spoondrift.setVisibility(View.GONE);
        findViewById(R.id.bottle_starfish).setVisibility(View.GONE);
        findViewById(R.id.bottle_paper).setVisibility(View.GONE);
        findViewById(R.id.bottle_voice).setVisibility(View.GONE);
        if((CIMConstant.ReturnCode.CODE_200.equals(code)) && (url.equals(BOTTLE_GET_URL))) {
            bottle =(Bottle) data;
            if((FriendDBManager.getManager().isFriend(bottle.sender)) || (BottleDBManager.getManager().isExist(bottle.sender))) {
                apiParams.put("gid", bottle.gid);
                requester.execute(null, null, BOTTLE_DISCARD_URL);
            } else {
                com.farsunset.lvxin.bean.Message msg = new com.farsunset.lvxin.bean.Message();
                View view = null;
                if(bottle.type == 0) {
                    view = findViewById(R.id.bottle_paper);
                    msg.content = bottle.content;
                } else if(bottle.type == 0x1) {
                    view = findViewById(R.id.bottle_voice);
                    msg.content = String.valueOf(bottle.length);
                    msg.file = bottle.content;
                    OSSFileLoader.getFileLoader(this).loadVoiceFile("lvxin-files", bottle.content, null);
                } else {
                    view.setVisibility(View.VISIBLE);
                    AnimationTools.start(R.anim.appear, view, new AnimationTools.OnAnimationListener() {

                        public void onAnimationEnd(View view) {
                        }
                        
                        public void onAnimationStart(View view) {
                        }
                    });
                    BottleDBManager.getManager().save(bottle);
                    msg.fileType = "2";
                    msg.gid = StringUtils.getUUID();
                    msg.receiver = user.getAccount();
                    msg.sender = bottle.sender;
                    msg.type = "700";
                    msg.createTime = bottle.timestamp;
                    msg.status = "1";
                    MessageDBManager.getManager().saveMessage(msg);
                }
            }
        }
        if((CIMConstant.ReturnCode.CODE_404.equals(code)) && (url.equals(BOTTLE_GET_URL))) {
            stopSeek();
        }
    }
    
    private void stopSeek() {
        spoondrift.setVisibility(View.GONE);
        findViewById(R.id.bottle_starfish).setVisibility(View.VISIBLE);
        AnimationTools.start(R.anim.long_appear, findViewById(R.id.bottle_starfish), new AnimationTools.OnAnimationListener() {

            public void onAnimationEnd(View view) {
                view.setVisibility(View.GONE);
                findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night);
            }
            
            public void onAnimationStart(View view) {
            }
        });
    }
    
    public void onFailed(Exception e) {
        stopSeek();
    }
    
    public Map getRequestParams(String code) {
        apiParams.put("sender", user.getAccount());
        apiParams.put("account", user.getAccount());
        apiParams.put("region", ClientConfig.getCurrentRegion());
        return apiParams;
    }
    
    public void onRequest() {
    }
    
    public void onDestroy() {
        unregisterReceiver(recordBroadcastReceiver);
        super.onDestroy();
    }
    
    public void onDiscard() {
        apiParams.put("gid", bottle.gid);
        throwBottle(BOTTLE_DISCARD_URL);
        BottleDBManager.getManager().deleteById(bottle.gid);
        MessageDBManager.getManager().deleteBySender(bottle.sender);
    }
    
    public void onReply() {
        spoondrift.setVisibility(View.GONE);
        findViewById(R.id.bottle_starfish).setVisibility(View.GONE);
        findViewById(R.id.bottle_paper).setVisibility(View.GONE);
        findViewById(R.id.bottle_voice).setVisibility(View.GONE);
        findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night);
        bottleDialog.dismiss();
        Intent intent = new Intent(this, BottleChatActivity.class);
        intent.putExtra(Bottle.class.getSimpleName(), bottle);
        startActivity(intent);
    }
    
    public void onMessageReceived(com.farsunset.cim.client.model.Message msg) {
        if("700".equals(msg.getType())) {
            showNewMsgLabe();
        }
    }
    
    private void showNewMsgLabe() {
        long count = MessageDBManager.getManager().countNewByType("700");
        if(count > 0x0) {
            newBottleMsgCountLabel.setVisibility(View.VISIBLE);
            newBottleMsgCountLabel.setText(String.valueOf(count));
            return;
        }
        newBottleMsgCountLabel.setText("");
        newBottleMsgCountLabel.setVisibility(View.GONE);
    }
    
    public void onResume() {
        super.onResume();
        showNewMsgLabe();
    }

    public class RecordBroadcastReceiver
            extends BroadcastReceiver
    {
        public static final String ACTION_RECORDING = "ACTION_RECORDING";

        public RecordBroadcastReceiver() {}

        public IntentFilter getIntentFilter()
        {
            IntentFilter localIntentFilter = new IntentFilter();
            localIntentFilter.addAction("ACTION_RECORDING");
            return localIntentFilter;
        }

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            long l = System.currentTimeMillis();
            int i = (int)((l - DriftBottleActivity.this.startTime) / 1000L);
            if (i >= 60)
            {
                DriftBottleActivity.this.endTime = l;
                DriftBottleActivity.this.recordCompleted(true);
                return;
            }
            if (i < 10)
            {
                DriftBottleActivity.this.recordTime.setText("00:0" + i);
                return;
            }
            DriftBottleActivity.this.recordTime.setText("00:" + i);
        }
    }
}
*/
package cn.cloudartisan.crius.ui.trend;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.TypeReference;
import com.nostra13.universalimageloader.core.ImageLoader;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.ClientConfig;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.GlobalMediaPlayer;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Bottle;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.db.BottleDBManager;
import cn.cloudartisan.crius.db.FriendDBManager;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.network.FileUploader;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.network.OSSFileLoader;
import cn.cloudartisan.crius.ui.base.CIMMonitorActivity;
import cn.cloudartisan.crius.util.AnimationTools;
import cn.cloudartisan.crius.util.AppTools;
import cn.cloudartisan.crius.util.StringUtils;
import cn.cloudartisan.crius.widget.BottlePreviewDialog;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class DriftBottleActivity extends CIMMonitorActivity implements HttpAPIResponser, BottlePreviewDialog.OnHandleListener {
    Bottle bottle;
    BottlePreviewDialog bottleDialog;
    ImageView emptyBottle;
    public long endTime;
    boolean isVoiceRecording = false;
    private MediaRecorder mediaRecorder;
    private EditText messageEditText;
    private TextView newBottleMsgCountLabel;
    Handler recordHandler = new Handler() {
        public void handleMessage(Message var1) {
            long var3 = System.currentTimeMillis();
            int var2 = (int)((var3 - DriftBottleActivity.this.startTime) / 1000L);
            if(var2 >= 60) {
                DriftBottleActivity.this.endTime = var3;
                DriftBottleActivity.this.recordCompleted(true);
            } else if(var2 < 10) {
                DriftBottleActivity.this.recordTime.setText("00:0" + var2);
            } else {
                DriftBottleActivity.this.recordTime.setText("00:" + var2);
            }

            if(DriftBottleActivity.this.isVoiceRecording) {
                DriftBottleActivity.this.recordHandler.sendMessageDelayed(DriftBottleActivity.this.recordHandler.obtainMessage(), 1000L);
            }

        }
    };
    TextView recordTime;
    TextView recordingHint;
    HttpAPIRequester requester;
    ImageView spoondrift;
    public long startTime;
    AnimationSet throwAnimation;
    Button throwButton;
    User user;
    private File voiceFile;
    View voiceRecordingPanel;

    private boolean isReady() {
        return this.spoondrift.getVisibility() != View.VISIBLE
                && this.findViewById(R.id.bottle_starfish).getVisibility() != View.VISIBLE
                && this.findViewById(R.id.bottle_paper).getVisibility() != View.VISIBLE &&
                this.findViewById(R.id.bottle_voice).getVisibility() !=View.VISIBLE;
    }

    private void recordCompleted(boolean var1) {
        if(this.isVoiceRecording) {
            try {
                this.mediaRecorder.stop();
                this.mediaRecorder.release();
            } catch (Exception var3) {
                ;
            }

            this.throwButton.setSelected(false);
            this.voiceRecordingPanel.setVisibility(View.GONE);
            this.throwButton.setText(R.string.label_chat_soundrecord_normal);
            this.recordTime.setText("00:00");
            if(var1) {
                this.apiParams.put("content", this.voiceFile.getName());
                this.apiParams.put("type", Integer.valueOf(1));
                this.apiParams.put("length", Long.valueOf((this.endTime - this.startTime - 1000L) / 1000L + 1L));
                this.apiParams.remove("gid");
                this.throwBottle(URLConstant.BOTTLE_THROW_URL);
                FileUploader.asyncUpload("lvxin-files", this.voiceFile.getName(), this.voiceFile);
            } else {
               // FileUtils.deleteQuietly(this.voiceFile);
                try {
                    FileUtils.forceDelete(this.voiceFile);
                }catch (Exception ex){

                }
            }
        }

        this.isVoiceRecording = false;
    }

    private void showNewMsgLabe() {
        long var1 = MessageDBManager.getManager().countNewByType("700");
        if(var1 > 0L) {
            this.newBottleMsgCountLabel.setVisibility(View.VISIBLE);
            this.newBottleMsgCountLabel.setText(String.valueOf(var1));
        } else {
            this.newBottleMsgCountLabel.setText("");
            this.newBottleMsgCountLabel.setVisibility(View.GONE);
        }
    }

    private void stopSeek() {
        this.hideSpoondrift();
        this.findViewById(R.id.bottle_starfish).setVisibility(View.VISIBLE);
        AnimationTools.start(R.anim.long_appear, this.findViewById(R.id.bottle_starfish), new AnimationTools.OnAnimationListener() {
            public void onAnimationEnd(View var1) {
                var1.setVisibility(View.GONE);
                DriftBottleActivity.this.findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night);
            }

            public void onAnimationStart(View var1) {
            }
        });
    }

    private void throwBottle(final String var1) {
        this.findViewById(R.id.emptyBottle).setVisibility(View.VISIBLE);
        this.findViewById(R.id.letterPaper).setVisibility(View.VISIBLE);
        this.findViewById(R.id.bottleBox).setVisibility(View.VISIBLE);
        this.findViewById(R.id.view_throw_bottle).setBackgroundResource(R.color.transparent);
        this.findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night_light);
        this.findViewById(R.id.bottomThrowView).setVisibility(View.GONE);
        this.messageEditText.setVisibility(View.GONE);
        this.throwAnimation = (AnimationSet)AnimationUtils.loadAnimation(this, R.anim.bottile_paper);
        int[] var2 = new int[4];
        int[] varT=new int[2];
        this.throwButton.getLocationInWindow(varT);
        var2[0]=varT[0];
        var2[1]=varT[1];
     //   this.findViewById(R.id.bottleBox).getLocationInWindow(var2);
        var2[2] = this.getResources().getDisplayMetrics().widthPixels / 3;
        var2[3] = this.getResources().getDisplayMetrics().heightPixels / 4;
        TranslateAnimation var4 = new TranslateAnimation(0.0F, (float)(-var2[2]), 0.0F, (float)(-var2[3]));
        this.throwAnimation.addAnimation(var4);
        AnimationTools.start(this.throwAnimation, this.findViewById(R.id.bottleBox), new AnimationTools.OnAnimationListener() {
            public void onAnimationEnd(View var1x) {
                DriftBottleActivity.this.findViewById(R.id.bottleBox).setVisibility(View.GONE);
            }

            public void onAnimationStart(View var1x) {
                DriftBottleActivity.this.requester.execute((Type)null, (Type)null, var1);
            }
        });
        final AlphaAnimation var3 = new AlphaAnimation(1.0F, 1.0F);
        var3.setDuration(1500L);
        AnimationTools.start(var3, this.findViewById(R.id.rootView), new AnimationTools.OnAnimationListener() {
            public void onAnimationEnd(View var1) {
                DriftBottleActivity.this.showSpoondrift();
                AnimationTools.start(var3, DriftBottleActivity.this.findViewById(R.id.rootView), new AnimationTools.OnAnimationListener() {
                    public void onAnimationEnd(View var1) {
                        DriftBottleActivity.this.hideThrowView();
                    }

                    public void onAnimationStart(View var1) {
                    }
                });
            }

            public void onAnimationStart(View var1) {
            }
        });
    }

    public boolean dispatchTouchEvent(MotionEvent var1) {
        boolean var2 = true;
        if(this.findViewById(R.id.view_throw_bottle).getVisibility() != View.VISIBLE) {
            return super.dispatchTouchEvent(var1);
        } else {
            int[] var3 = new int[4];
            int[] varT=new int[2];
            this.throwButton.getLocationInWindow(varT);
            var3[0]=varT[0];
            var3[1]=varT[1];
           // this.messageEditText.getLocationInWindow(var3);
            var3[2] = var3[0] + this.messageEditText.getWidth();
            var3[3] = var3[1] + this.messageEditText.getHeight();
            int[] var4 = new int[4];
            int[] varT1=new int[2];
            this.throwButton.getLocationInWindow(varT1);
            var4[0]=varT1[0];
            var4[1]=varT1[1];
            //this.findViewById(R.id.bottomThrowView).getLocationInWindow(var4);
            var4[2] = var4[0] + this.findViewById(R.id.bottomThrowView).getWidth();
            var4[3] = var4[1] + this.findViewById(R.id.bottomThrowView).getHeight();
            int[] var5 = new int[4];
            int[] varT2=new int[2];
            this.throwButton.getLocationInWindow(varT2);
            var5[0]=varT2[0];
            var5[1]=varT2[1];
            var5[2] = var4[0] + this.throwButton.getWidth();
            var5[3] = var4[1] + this.throwButton.getHeight();
            switch(var1.getAction()) {
                case 0:
                    if(!AppTools.contains(var3, var1) && !AppTools.contains(var4, var1)) {
                        this.hideThrowView();
                        return super.dispatchTouchEvent(var1);
                    }

                    if(AppTools.contains(var5, var1) && this.messageEditText.getVisibility() == View.GONE) {
                        GlobalMediaPlayer.getPlayer().start(R.raw.startrecord);
                        this.isVoiceRecording = true;
                        this.recordHandler.sendMessage(this.recordHandler.obtainMessage());
                        this.throwButton.setSelected(true);
                        this.throwButton.setText(R.string.label_chat_soundrecord_pressed);
                        this.recordingHint.setText(R.string.label_chat_soundcancle);

                        try {
                            this.voiceRecordingPanel.setVisibility(View.VISIBLE);
                            this.mediaRecorder = new MediaRecorder();
                            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) +  StringUtils.getUUID());
                            this.voiceFile = folder;//new File(Constant.CACHE_DIR_VOICE + "/" + StringUtils.getUUID());
                          /*  if (!this.voiceFile.exists()) {
                                this.voiceFile.mkdir();
                                        }*/

                            this.voiceFile.createNewFile();
                            this.mediaRecorder.setAudioSource(1);
                            this.mediaRecorder.setOutputFormat(3);
                            this.mediaRecorder.setAudioEncoder(1);
                            this.mediaRecorder.setOutputFile(this.voiceFile.getAbsolutePath());
                            this.mediaRecorder.prepare();
                            this.startTime = System.currentTimeMillis();
                            this.mediaRecorder.start();
                        } catch (IOException var6) {
                            var6.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    this.endTime = System.currentTimeMillis();
                    if(!AppTools.contains(var5, var1) || (this.endTime - this.startTime) / 1000L <= 0L) {
                        var2 = false;
                    }

                    this.recordCompleted(var2);
                    break;
                case 2:
                    if(this.isVoiceRecording) {
                        if(!AppTools.contains(var5, var1)) {
                            this.recordingHint.setText(R.string.label_chat_unlashcancle);
                            this.findViewById(R.id.recordingBanner).setSelected(true);
                            this.throwButton.setSelected(false);
                        } else {
                            this.recordingHint.setText(R.string.label_chat_soundcancle);
                            this.findViewById(R.id.recordingBanner).setSelected(false);
                            this.throwButton.setSelected(true);
                        }
                    }
            }

            return super.dispatchTouchEvent(var1);
        }
    }

    public int getActionBarTitle() {
        return R.string.label_function_bottle;
    }

    public int getConentLayout() {
        return R.layout.activity_drift_bottle;
    }

    public Map<String, Object> getRequestParams(String code) {
        this.apiParams.put("sender", this.user.getAccount());
        this.apiParams.put("account", this.user.getAccount());
        this.apiParams.put("region", ClientConfig.getCurrentRegion());
        return this.apiParams;
    }

    public void hideSpoondrift() {
        ((AnimationDrawable)this.spoondrift.getDrawable()).stop();
        this.spoondrift.setVisibility(View.GONE);
    }

    public void hideThrowView() {
        this.hideSpoondrift();
        this.findViewById(R.id.view_bottle_table).setVisibility(View.VISIBLE);
        this.findViewById(R.id.view_throw_bottle).setVisibility(View.GONE);
        this.findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night);
    }

    public void initComponents() {
        ImageLoader.getInstance().clearMemoryCache();
        this.getActionBar().hide();
        this.user = Global.getCurrentUser();
        this.messageEditText = (EditText)this.findViewById(R.id.messageEditText);
        this.throwButton = (Button)this.findViewById(R.id.throwButton);
        this.emptyBottle = (ImageView)this.findViewById(R.id.emptyBottle);
        this.spoondrift = (ImageView)this.findViewById(R.id.spoondrift);
        this.voiceRecordingPanel = this.findViewById(R.id.recordingPanelView);
        this.recordTime = (TextView)this.findViewById(R.id.recordingTime);
        this.recordingHint = (TextView)this.findViewById(R.id.recordingHint);
        this.newBottleMsgCountLabel = (TextView)this.findViewById(R.id.badge_bottle_unread_count);
        this.findViewById(R.id.tab_bottle_record).setOnClickListener(this);
        this.findViewById(R.id.tab_throw_bottle).setOnClickListener(this);
        this.findViewById(R.id.tab_get_bottle).setOnClickListener(this);
        this.findViewById(R.id.voiceSwitchButton).setOnClickListener(this);
        this.findViewById(R.id.keyboardSwitchButton).setOnClickListener(this);
        this.findViewById(R.id.throwButton).setOnClickListener(this);
        this.findViewById(R.id.bottle_paper).setOnClickListener(this);
        this.findViewById(R.id.bottle_voice).setOnClickListener(this);
        this.requester = new HttpAPIRequester(this);
        this.bottleDialog = new BottlePreviewDialog(this, this);
    }

    public void onClick(View var1) {
        if(var1.getId() == R.id.tab_throw_bottle && this.isReady()) {
            this.findViewById(R.id.view_throw_bottle).setVisibility(View.VISIBLE);
            this.findViewById(R.id.emptyBottle).setVisibility(View.GONE);
            this.findViewById(R.id.view_bottle_table).setVisibility(View.GONE);
            this.messageEditText.setVisibility(View.GONE);
            this.findViewById(R.id.bottomThrowView).setVisibility(View.VISIBLE);
            this.findViewById(R.id.keyboardSwitchButton).setVisibility(View.VISIBLE);
            this.findViewById(R.id.voiceSwitchButton).setVisibility(View.GONE);
            this.throwButton.setText(R.string.text_bottle_press_record);
        }

        if(var1.getId() == R.id.tab_get_bottle && this.isReady()) {
            this.findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night_light);
            this.showSpoondrift();
            this.requester.execute((new TypeReference<DriftBottleActivity
                    >() {
            }).getType(), (Type)null, URLConstant.BOTTLE_GET_URL);
        }

        if(var1.getId() == R.id.tab_bottle_record && this.isReady()) {
            this.startActivity(new Intent(this, BottleListActivity.class));
        }

        if(var1.getId() == R.id.voiceSwitchButton) {
            var1.setVisibility(View.GONE);
            this.findViewById(R.id.keyboardSwitchButton).setVisibility(View.VISIBLE);
            this.throwButton.setText(R.string.text_bottle_press_record);
            this.messageEditText.setVisibility(View.GONE);
            ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.messageEditText.getWindowToken(), 0);
        }

        if(var1.getId() == R.id.keyboardSwitchButton) {
            var1.setVisibility(View.GONE);
            this.throwButton.setText(R.string.text_bottle_throw);
            this.findViewById(R.id.voiceSwitchButton).setVisibility(View.VISIBLE);
            this.messageEditText.setVisibility(View.VISIBLE);
        }

        if(var1.getId() == R.id.throwButton && !TextUtils.isEmpty(this.messageEditText.getText().toString())) {
            ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.messageEditText.getWindowToken(), 0);
            this.apiParams.put("content", this.messageEditText.getText().toString());
            this.apiParams.put("type", Integer.valueOf(0));
            this.apiParams.remove("gid");
            this.throwBottle(URLConstant.BOTTLE_THROW_URL);
        }

        if(var1.getId() == R.id.bottle_paper || var1.getId() == R.id.bottle_voice) {
            this.bottleDialog.showBottle(this.bottle);
            this.bottleDialog.show();
            this.hideSpoondrift();
            this.findViewById(R.id.bottle_starfish).setVisibility(View.GONE);
            this.findViewById(R.id.bottle_paper).setVisibility(View.GONE);
            this.findViewById(R.id.bottle_voice).setVisibility(View.GONE);
        }

    }

    public void onDiscard() {
        this.apiParams.put("gid", this.bottle.gid);
        this.throwBottle(URLConstant.BOTTLE_DISCARD_URL);
        BottleDBManager.getManager().deleteById(this.bottle.gid);
        MessageDBManager.getManager().deleteBySender(this.bottle.sender);
    }

    public void onFailed(Exception var1) {
        this.stopSeek();
    }

    public void onMessageReceived(cn.cloudartisan.crius.client.model.Message var1) {
        if("700".equals(var1.getType())) {
            this.showNewMsgLabe();
        }

    }

    public void onReply() {
        this.hideSpoondrift();
        this.findViewById(R.id.bottle_starfish).setVisibility(View.GONE);
        this.findViewById(R.id.bottle_paper).setVisibility(View.GONE);
        this.findViewById(R.id.bottle_voice).setVisibility(View.GONE);
        this.findViewById(R.id.rootView).setBackgroundResource(R.drawable.sea_night);
        this.bottleDialog.dismiss();
        Intent var1 = new Intent(this, BottleChatActivity.class);
        var1.putExtra(Bottle.class.getSimpleName(), this.bottle);
        this.startActivity(var1);
    }

    public void onRequest() {
    }

    public void onResume() {
        super.onResume();
        this.showNewMsgLabe();
    }

    public void onSuccess(Object var1, List var2, Page var3, String var4, String var5) {
        this.hideSpoondrift();
        this.findViewById(R.id.bottle_starfish).setVisibility(View.GONE);
        this.findViewById(R.id.bottle_paper).setVisibility(View.GONE);
        this.findViewById(R.id.bottle_voice).setVisibility(View.GONE);
        String var7 = var4;
        if(CIMConstant.ReturnCode.CODE_200.equals(var4)) {
            var7 = var4;
            if(var5.equals(URLConstant.BOTTLE_GET_URL)) {
                this.bottle = (Bottle)var1;
                if(!FriendDBManager.getManager().isFriend(this.bottle.sender) && !BottleDBManager.getManager().isExist(this.bottle.sender)) {
                    cn.cloudartisan.crius.bean.Message var9 = new cn.cloudartisan.crius.bean.Message();
                    View var6 = null;
                    if(this.bottle.type == 0) {
                        var6 = this.findViewById(R.id.bottle_paper);
                        var9.content = this.bottle.content;
                    }

                    if(this.bottle.type == 1) {
                        var6 = this.findViewById(R.id.bottle_voice);
                        var9.content = String.valueOf(this.bottle.length);
                        var9.file = this.bottle.content;
                        OSSFileLoader.getFileLoader(this).loadVoiceFile("lvxin-files", this.bottle.content, (OSSFileLoader.FileLoadedCallback)null);
                    }

                    var6.setVisibility(View.VISIBLE);
                    AnimationTools.start(R.anim.appear, var6, new AnimationTools.OnAnimationListener() {
                        public void onAnimationEnd(View var1) {
                        }

                        public void onAnimationStart(View var1) {
                        }
                    });
                    BottleDBManager.getManager().save(this.bottle);
                    String var8;
                    if(this.bottle.type == 0) {
                        var8 = "0";
                    } else {
                        var8 = "2";
                    }

                    var9.fileType = var8;
                    var9.gid = StringUtils.getUUID();
                    var9.receiver = this.user.getAccount();
                    var9.sender = this.bottle.sender;
                    var9.type = "700";
                    var9.createTime = this.bottle.timestamp;
                    var9.status = "1";
                    MessageDBManager.getManager().saveMessage(var9);
                    var7 = var4;
                } else {
                    this.apiParams.put("gid", this.bottle.gid);
                    this.requester.execute((Type)null, (Type)null, URLConstant.BOTTLE_DISCARD_URL);
                    var7 = CIMConstant.ReturnCode.CODE_404;
                }
            }
        }

        if(CIMConstant.ReturnCode.CODE_404.equals(var7) && var5.equals(URLConstant.BOTTLE_GET_URL)) {
            this.stopSeek();
        }

    }

    public void showSpoondrift() {
        ((AnimationDrawable)this.spoondrift.getDrawable()).start();
        this.spoondrift.setVisibility(View.VISIBLE);
    }
}
