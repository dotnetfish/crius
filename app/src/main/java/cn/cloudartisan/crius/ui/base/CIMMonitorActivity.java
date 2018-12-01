/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.base;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import cn.cloudartisan.crius.client.android.CIMEventListener;
import cn.cloudartisan.crius.client.android.CIMListenerManager;
import cn.cloudartisan.crius.client.model.Message;
import cn.cloudartisan.crius.client.model.ReplyBody;

public abstract class CIMMonitorActivity extends BaseActivity implements CIMEventListener {
    
    public void onCreate(Bundle savedInstanceState) {
        CIMListenerManager.registerMessageListener(this, this);
        super.onCreate(savedInstanceState);
    }
    
    public void onDestroy() {
        super.onDestroy();
        CIMListenerManager.removeMessageListener(this);
    }
    
    public void finish() {
        super.finish();
        CIMListenerManager.removeMessageListener(this);
    }
    
    public void onRestart() {
        super.onRestart();
        CIMListenerManager.registerMessageListener(this, this);
    }
    
    public void onConnectionStatus(boolean isConnected) {
    }
    
    public void onReplyReceived(ReplyBody reply) {
        Toast.makeText(CIMMonitorActivity.this, "ddd", Toast.LENGTH_SHORT).show();
    }
    
    public void onMessageReceived(Message arg0) {
        Log.e("monitor activity",arg0.getContent());
      Toast.makeText(CIMMonitorActivity.this, "ddd", Toast.LENGTH_SHORT).show();
    }
    
    public void onNetworkChanged(NetworkInfo info) {
    }
}
