/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.setting;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.BaseActivity;
import cn.cloudartisan.crius.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ModifyPasswordActivity extends BaseActivity implements HttpAPIResponser {
    HttpAPIRequester requester;
    User user;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        user = Global.getCurrentUser();
        requester = new HttpAPIRequester(this);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
            {
                if(StringUtils.isNotEmpty(((EditText)findViewById(R.id.oldPassword)).getText().toString().trim())) {
                    if(StringUtils.isNotEmpty(((EditText)findViewById(R.id.newPassword)).getText().toString().trim())) {
                        requester.execute(new TypeReference<ModifyPasswordActivity>() {}.getType(), null, URLConstant.USER_MODIFYPASSWORD_URL);
                        break;
                    }
                }
            }
        }
    }
    
    public void onSuccess(Object data, List list, Page page, String code, String url) {
        hideProgressDialog();
        if(CIMConstant.ReturnCode.CODE_200.equals(code)) {
            showToast(R.string.tip_save_complete);
            user.setPassword(((EditText)findViewById(R.id.newPassword)).getText().toString().trim());//password = ;
            Global.setCurrentUser(user);
            return;
        }
        showToast(R.string.tip_oldpassword_error);
    }
    
    public void onFailed(Exception e) {
        hideProgressDialog();
    }
    
    public Map getRequestParams(String code) {
        apiParams.put("oldPassword", ((EditText)findViewById(R.id.oldPassword)).getText().toString().trim());
        apiParams.put("newPassword", ((EditText)findViewById(R.id.newPassword)).getText().toString().trim());
        apiParams.put("account", user.getAccount());
        return apiParams;
    }
    
    public void onRequest() {
        showProgressDialog(getString(R.string.tip_loading, new Object[] {getString(R.string.common_save)}));
    }
    
    public int getConentLayout() {
        return R.layout.activity_modify_passord;
    }
    
    public int getActionBarTitle() {
        return R.string.label_setting_modify_password;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_button, menu);
        Button button = (Button)menu.findItem(R.id.menu_button).getActionView().findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setText(R.string.common_save);
        return super.onCreateOptionsMenu(menu);
    }
}
