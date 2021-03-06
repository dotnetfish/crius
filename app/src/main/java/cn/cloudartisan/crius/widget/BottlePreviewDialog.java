/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.widget;

import android.app.Dialog;
import android.view.View;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.GlobalVoicePlayer;
import cn.cloudartisan.crius.network.OSSFileLoader;
import java.io.File;
import android.content.Context;
import android.media.MediaPlayer;
import cn.cloudartisan.crius.app.GlobalMediaPlayer;
import android.widget.TextView;
import cn.cloudartisan.crius.bean.Bottle;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.util.FileURLBuilder;
import cn.cloudartisan.crius.app.Constant;

public class BottlePreviewDialog extends Dialog implements View.OnClickListener, GlobalVoicePlayer.OnPlayListener, OSSFileLoader.FileLoadedCallback {
    BottlePreviewDialog.OnHandleListener listener;
    File voiceFile;
    
    public BottlePreviewDialog(Context context, BottlePreviewDialog.OnHandleListener l) {
        super(context, R.style.custom_dialog);
        listener = l;
        setContentView(R.layout.bottle_preview_dialog);
        findViewById(R.id.dialogLeftBtn).setOnClickListener(this);
        findViewById(R.id.dialogRightBtn).setOnClickListener(this);
        findViewById(R.id.dialogVoice).setOnClickListener(this);
    }
    
    public void showBottle(Bottle bottle) {
        ((TextView)findViewById(R.id.dialogTitle)).setText(bottle.getName() + "\u7684\u74f6\u5b50");
        ((TextView)findViewById(R.id.dialogText)).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.lengthTextView)).setText("\"");
        findViewById(R.id.dialogVoice).setVisibility(View.GONE);
        ((WebImageView)findViewById(R.id.icon)).load(FileURLBuilder.getUserIconUrl(bottle.sender));
        if(bottle.type == 0) {
            ((TextView)findViewById(R.id.dialogText)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.dialogText)).setText(bottle.content);
        }
        if(bottle.type == 0x1) {
            findViewById(R.id.dialogVoice).setVisibility(View.VISIBLE);
            voiceFile = new File(Constant.CACHE_DIR_VOICE, bottle.content);
            if(!voiceFile.exists()) {
                voiceFile = null;
                OSSFileLoader.getFileLoader(getContext()).loadVoiceFile("lvxin-files", bottle.content, this);
            }
        }
    }
    
    public void setButtonsText(CharSequence left, CharSequence right) {
        ((TextView)findViewById(R.id.dialogLeftBtn)).setText(left);
        ((TextView)findViewById(R.id.dialogRightBtn)).setText(right);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.dialogLeftBtn:
            {
                dismiss();
                listener.onDiscard();
                return;
            }
            case R.id.dialogRightBtn:
            {
                listener.onReply();
                return;
            }
            case R.id.dialogVoice:
            {
                if(voiceFile != null) {
                    findViewById(R.id.waveformImage).setVisibility(View.GONE);
                    findViewById(R.id.waveformAnim).setVisibility(View.VISIBLE);
                    GlobalVoicePlayer.getPlayer().start(voiceFile, this);
                    break;
                }
            }
        }
    }
    
    public void fileLoaded(File file, String currentKey) {
    }
    
    public void onCompletion(MediaPlayer mediaplayer) {
        GlobalMediaPlayer.getPlayer().start(R.raw.play_completed);
        findViewById(R.id.waveformImage).setVisibility(View.VISIBLE);
        findViewById(R.id.waveformAnim).setVisibility(View.GONE);
    }
    
    public void onVoiceStop() {
        findViewById(R.id.waveformImage).setVisibility(View.VISIBLE);
        findViewById(R.id.waveformAnim).setVisibility(View.GONE);
    }
    public  interface OnHandleListener
    {
        void onDiscard();

        void onReply();
    }
}
