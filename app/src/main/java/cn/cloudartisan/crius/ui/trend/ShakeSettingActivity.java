/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.trend;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.ClientConfig;
import cn.cloudartisan.crius.ui.base.BaseActivity;

public class ShakeSettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        if(ClientConfig.shakeSoundIsEnable()) {
            ((CheckBox)findViewById(R.id.soundSwitch)).setChecked(true);
        } else {
            ( (CheckBox)findViewById(R.id.soundSwitch)).setChecked(false);
        }
        ((CheckBox)findViewById(R.id.soundSwitch)).setOnCheckedChangeListener(this);
        findViewById(R.id.item_records).setOnClickListener(this);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.item_records:
            {
                startActivity(new Intent(this, ShakeRecordListActivity.class));
                break;
            }
        }
    }
    
    public void onCheckedChanged(CompoundButton arg0, boolean flag) {
        if(flag) {
            ClientConfig.shakeSoundEnable();
            return;
        }
        ClientConfig.shakeSoundDisEnable();
    }
    
    public int getConentLayout() {
        return R.layout.activity_shake_setting;
    }
    
    public int getActionBarTitle() {
        return R.string.common_setting;
    }
}
