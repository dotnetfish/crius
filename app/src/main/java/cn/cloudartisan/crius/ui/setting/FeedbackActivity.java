/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.setting;

import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.ui.base.BaseActivity;

import java.util.HashMap;

public class FeedbackActivity extends BaseActivity {
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
            {
                String text = ((EditText)findViewById(R.id.text)).getText().toString();
                if(!TextUtils.isEmpty(text)) {
                    User source = Global.getCurrentUser();
                    HashMap<String, Object> inputData = new HashMap<String, Object>();
                    inputData.put("account", source.getAccount());
                    inputData.put("content", text);
                    HttpAPIRequester.execute(inputData, URLConstant. FEEDBACK_PUBLISH_URL);
                    showToast(R.string.tip_feedback_completed);
                    finish();
                    break;
                }
            }
        }
    }
    
    public int getConentLayout() {
        return R.layout.activity_feedback;
    }
    
    public int getActionBarTitle() {
        return R.string.common_feedback;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_button, menu);
        Button button = (Button)menu.findItem(R.id.menu_button).getActionView().findViewById(R.id.button);
        button.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }
}
