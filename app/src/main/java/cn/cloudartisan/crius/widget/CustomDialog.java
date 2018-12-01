/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.widget;

import android.app.Dialog;
import android.view.View;
import android.content.Context;
import android.widget.TextView;
import android.view.animation.AnimationUtils;
import cn.cloudartisan.crius.R;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private View mDialogView;
    CustomDialog.OnOperationListener operationListener;
    
    public CustomDialog(Context context) {
        super(context, R.style.custom_dialog);
        setContentView(R.layout.custom_dialog);
      //  mDialogView = getWindow().getDecorView().findViewById(0x1020002);
        mDialogView=getWindow().getDecorView().findViewById(R.id.dialogRoot);
        findViewById(R.id.dialogLeftBtn).setOnClickListener(this);
        findViewById(R.id.dialogRightBtn).setOnClickListener(this);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }
    
    public void setOperationListener(CustomDialog.OnOperationListener operationListener) {
       this.operationListener = operationListener;
    }
    
    public void setTitle(CharSequence title) {
        ((TextView)findViewById(R.id.dialogTitle)).setText(title);
    }
    
    public void setTag(Object tag) {
        findViewById(R.id.dialogTitle).setTag(tag);
    }
    
    public Object getTag() {
        return findViewById(R.id.dialogTitle).getTag();
    }
    
    public void setMessage(CharSequence message) {
        ((TextView)findViewById(R.id.dialogText)).setText(message);
    }
    
    public void setButtonsText(CharSequence left, CharSequence right) {
        ((TextView)findViewById(R.id.dialogLeftBtn)).setText(left);
        ((TextView)findViewById(R.id.dialogRightBtn)).setText(right);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.dialogLeftBtn:
            {
                operationListener.onLeftClick();
                return;
            }
            case R.id.dialogRightBtn:
            {
                operationListener.onRightClick();
                break;
            }
        }
    }
    
    public void show() {
        super.show();
        mDialogView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_in));
    }

    public  interface OnOperationListener
    {
         void onLeftClick();

       void onRightClick();
    }
}
