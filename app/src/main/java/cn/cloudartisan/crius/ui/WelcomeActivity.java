

package cn.cloudartisan.crius.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import cn.cloudartisan.crius.client.android.CIMPushManager;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.db.MessageDBManager;
import com.nostra13.universalimageloader.core.ImageLoader;

public class WelcomeActivity extends Activity implements View.OnClickListener {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);
        CIMPushManager.init(this, "192.168.1.105", 23456);
        if(Global.getCurrentUser() != null) {
            MessageDBManager.getManager().resetSendingStatus();
        }
        AlphaAnimation aAnimation = new AlphaAnimation(0.0f, 1.0f);
        aAnimation.setDuration(0x3e8);
        aAnimation.setAnimationListener(new Animation.AnimationListener() {


            public void onAnimationEnd(Animation animation) {
                if(Global.getCurrentUser() != null) {
                    if(Global.getCurrentUser().getPassword() == null) {
                        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    return;
                }
                findViewById(R.id.button_group).setVisibility(View.VISIBLE);
                findViewById(R.id.button_group).startAnimation(AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.slide_in_from_bottom));
            }
            
            public void onAnimationRepeat(Animation animation) {
            }
            
            public void onAnimationStart(Animation animation) {
            }
        });
        findViewById(R.id.rootView).startAnimation(aAnimation);
    }
    
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()) {
            case 2131493036:
            {
                intent.setClass(this, LoginActivity.class);
                intent.putExtra("source", "wel");
                break;
            }
            case 2131493037:
            {
                intent.setClass(this, RegisterActivity.class);
                break;
            }
        }
        startActivity(intent);
    }
    
    public void onBackPressed() {
        CIMPushManager.destory(this);
        if(ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().destroy();
        }
        super.onBackPressed();
        Process.killProcess(Process.myPid());
    }
}
