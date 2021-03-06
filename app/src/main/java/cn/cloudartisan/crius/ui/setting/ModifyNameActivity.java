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
import cn.cloudartisan.crius.client.android.CIMPushManager;
import cn.cloudartisan.crius.client.model.SentBody;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.ui.base.BaseActivity;

import java.util.HashMap;

public class ModifyNameActivity extends BaseActivity {
    User user;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        user = Global.getCurrentUser();
        ((EditText)findViewById(R.id.name)).setText(user.getName());
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
            {
                save();
                break;
            }
        }
    }
    
    public void save() {
        if(!TextUtils.isEmpty(((EditText)findViewById(R.id.name)).getText().toString())) {
            HttpAPIRequester.execute(getRequestParams(""), URLConstant.USER_MODIFY_URL);
            user.setName(apiParams.get("name").toString());
            Global.setCurrentUser(user);
            SentBody sent = new SentBody();
            sent.setKey("client_modify_profile");
            sent.put("account", user.getAccount());
            sent.put("name", user.getName());
            sent.put("motto", user.getMotto());
            CIMPushManager.sendRequest(this, sent);
            showToast(R.string.tip_save_complete);
            finish();
        }
    }
    
    public HashMap getRequestParams(String code) {
        apiParams.put("account", user.getAccount());
        apiParams.put("motto", user.getMotto());
        apiParams.put("name", ((EditText)findViewById(R.id.name)).getText().toString());
        return apiParams;
    }
    
    public int getConentLayout() {
        return R.layout.activity_modify_name;
    }
    
    public int getActionBarTitle() {
        return R.string.label_setting_modify_name;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_button, menu);
        Button button = (Button)menu.findItem(R.id.menu_button).getActionView().findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setText(R.string.common_save);
        return super.onCreateOptionsMenu(menu);
    }
}
