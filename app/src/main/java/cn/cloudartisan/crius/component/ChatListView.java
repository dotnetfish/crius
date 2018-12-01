package cn.cloudartisan.crius.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.GlobalMediaPlayer;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.listener.OnUploadProgressListener;
import cn.cloudartisan.crius.util.AppTools;

public class ChatListView
        extends ListView
{
    public static final String ACTION_UPLOAD_PROGRESS = "com.farsunset.lvxin.UPLOAD_PROGRESS";
    private static final int DONE = 3;
    private static final int LOADING = 4;
    private static final int PULL_To_REFRESH = 1;
    private static final int RATIO = 3;
    private static final int REFRESHING = 2;
    private static final int RELEASE_To_REFRESH = 0;
    Context context;
    private int firstItemIndex;
    private View header;
    private int headerHeight;
    private boolean isBack;
    private boolean isRecored;
    public MessageSendReceiver messageSendReceiver;
    OnTouchDownListenter onTouchDownListenter;
    private OnPreviouListener previouListener;
    private int startY;
    private int state;

    public ChatListView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }

    private void changeHeaderViewByState()
    {
        switch (this.state)
        {
            case 0:
            default:
            case 1:

                if(!this.isBack){
                    this.isBack=false;
                }
                if(this.headerHeight==0){
                    this.header.setVisibility(GONE);
                }else {
                    this.header.setPadding(0, this.headerHeight * -1, 0, 0);
                }
                return;
            case 2:
                this.header.setPadding(0, 0, 0, 0);
                return;
        }

    }

    private void onPrevious()
    {
        if (this.previouListener != null) {
            this.previouListener.onPreviou();
        }
    }

    public void doPrevious()
    {
        this.state = 2;
        changeHeaderViewByState();
        onPrevious();
    }

    public void init(Context paramContext)
    {
        this.context = paramContext;
        this.header = LayoutInflater.from(paramContext).inflate(R.layout.list_chat_header, null);
        AppTools.measureView(this.header);
        this.headerHeight = this.header.getMeasuredHeight();
        addHeaderView(this.header, null, false);
        addFooterView(LayoutInflater.from(paramContext).inflate(R.layout.layout_chat_listview_footer, null));
        this.header.setPadding(0, this.headerHeight * -1, 0, 0);
        this.messageSendReceiver = new MessageSendReceiver();
        paramContext.registerReceiver(this.messageSendReceiver, this.messageSendReceiver.getIntentFilter());
    }

    public boolean onTouchEvent(MotionEvent var1)
    {
        if(var1.getAction() ==0 && this.onTouchDownListenter != null) {
            this.onTouchDownListenter.onTouchDown();
        }

        if(this.getFirstVisiblePosition() != 0 && this.getFirstVisiblePosition() != 1) {
            return super.onTouchEvent(var1);
        } else {
            switch(var1.getAction()) {
                case 0:
                    if(this.firstItemIndex == 0 && !this.isRecored) {
                        this.isRecored = true;
                        this.startY = (int)var1.getY();
                    }
                    break;
                case 1:
                    if(this.state != 2 && this.state != 4) {
                        if(this.state == 1) {
                            this.state = 3;
                            this.changeHeaderViewByState();
                        }

                        if(this.state == 0) {
                            this.state = 2;
                            this.changeHeaderViewByState();
                            this.onPrevious();
                        }
                    }

                    this.isRecored = false;
                    this.isBack = false;
                    break;
                case 2:
                    int var2 = (int)var1.getY();
                    if(this.firstItemIndex == 0 && !this.isRecored) {
                        this.isRecored = true;
                        this.startY = var2;
                    }

                    if(this.state != 2 && this.isRecored && this.state != 4) {
                        if(this.state == 0) {
                            this.setSelection(0);
                            if((var2 - this.startY) / 3 < this.headerHeight && var2 - this.startY > 0) {
                                this.state = 1;
                                this.changeHeaderViewByState();
                            } else if(var2 - this.startY <= 0) {
                                this.state = 3;
                                this.changeHeaderViewByState();
                            }
                        }

                        if(this.state == 1) {
                            this.setSelection(0);
                            if((var2 - this.startY) / 3 >= this.headerHeight) {
                                this.state = 0;
                                this.isBack = true;
                                this.changeHeaderViewByState();
                            } else if(var2 - this.startY <= 0) {
                                this.state = 3;
                                this.changeHeaderViewByState();
                            }
                        }

                        if(this.state == 3 && var2 - this.startY > 0) {
                            this.state = 1;
                            this.changeHeaderViewByState();
                        }

                        if(this.state == 1) {
                            this.header.setPadding(0, this.headerHeight * -1 + (var2 - this.startY) / 3, 0, 0);
                        }

                        if(this.state == 0) {
                            this.header.setPadding(0, (var2 - this.startY) / 3 - this.headerHeight, 0, 0);
                        }
                    }
            }

            return super.onTouchEvent(var1);
        }

    }

    public void previousComplete()
    {
        this.state = 3;
        changeHeaderViewByState();
    }

    public void setOnPreviouListener(OnPreviouListener paramOnPreviouListener)
    {
        this.previouListener = paramOnPreviouListener;
    }

    public void setOnTouchDownListenter(OnTouchDownListenter paramOnTouchDownListenter)
    {
        this.onTouchDownListenter = paramOnTouchDownListenter;
    }

    public void setSelection(int paramInt)
    {
        setTranscriptMode(1);
        super.setSelection(paramInt);
    }

    public class MessageSendReceiver
            extends BroadcastReceiver
    {
        public MessageSendReceiver() {}

        public IntentFilter getIntentFilter()
        {
            IntentFilter localIntentFilter = new IntentFilter();
            localIntentFilter.addAction("com.farsunset.cim.SEND_STATUS_CHANGED");
            localIntentFilter.addAction("com.farsunset.lvxin.UPLOAD_PROGRESS");
            return localIntentFilter;
        }

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            if (paramIntent.getAction().equals("com.farsunset.cim.SEND_STATUS_CHANGED"))
            {
                Message message = (Message)paramIntent.getSerializableExtra(Message.NAME);
                if ("1".equals(message.status)) {
                    GlobalMediaPlayer.getPlayer().start(R.raw.sent_message);
                }
                ToMessageView localToMessageView = (ToMessageView)ChatListView.this.findViewWithTag(paramContext);
                if (localToMessageView != null) {
                    localToMessageView.onMessageSend(message);
                }
            }
            if (paramIntent.getAction().equals("com.farsunset.lvxin.UPLOAD_PROGRESS"))
            {
                String str = paramIntent.getStringExtra("objectKey");
                View view = ChatListView.this.findViewWithTag(paramContext);
                if ((view instanceof OnUploadProgressListener)) {
                    ((OnUploadProgressListener)paramContext).onProgress(paramIntent.getFloatExtra("progress", 0.0F));
                }
            }
        }
    }

    public  interface OnMessageDeleteListenter
    {
        void onDelete(Message paramMessage);
    }

    public  interface OnPreviouListener
    {
         void onPreviou();
    }

    public  interface OnTouchDownListenter
    {
         void onTouchDown();
    }
}
