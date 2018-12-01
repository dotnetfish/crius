/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.network;

import android.content.Intent;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.LvxinApplication;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.PubMenuEvent;
import cn.cloudartisan.crius.bean.PublicAccount;
import cn.cloudartisan.crius.bean.PublicMenu;
import cn.cloudartisan.crius.util.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PublicAccountMenuHander {
    public HashMap<String, Object> apiParams;
    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue();
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(0x3, 0x5, 0x14, TimeUnit.SECONDS, queue);
    
    public static void execute(PublicAccount account, Message message) {
        PubMenuEvent event = new PubMenuEvent();
        event.eventType = "text";
        event.text = message.content;
        event.account = Global.getCurrentUser().getAccount();
        dispatcherEvent(account, event, message);
    }
    
    public static void execute(PublicAccount account, PublicMenu menu) {
        PubMenuEvent event = new PubMenuEvent();
        event.eventType = "menu";
        event.account = Global.getCurrentUser().getAccount();
        event.menuCode = menu.code;
        dispatcherEvent(account, event, menu);
    }
    
    public static void dispatcherEvent(final PublicAccount account,final PubMenuEvent event,final Serializable target) {
        executor.execute(new Runnable() {
            

            public void run() {
                cn.cloudartisan.crius.client.model.Message message = new cn.cloudartisan.crius.client.model.Message();
                message.setMid(StringUtils.getUUID());
                message.setReceiver(event.account);
                message.setSender(account.account);
                message.setType("201");
                message.setTimestamp(System.currentTimeMillis());
                Message msg = (Message)target;
                Intent statusintent = new Intent("com.farsunset.cim.SEND_STATUS_CHANGED");
                statusintent.putExtra(Message.NAME, target);
                try {
                    String text = httpPost(account.apiUrl, event);
                    message.setContent(text);
                    JSONObject json = JSON.parseObject(text);
                    if((json.containsKey("contentType")) && ("0".equals(json.getString("contentType")))) {
                        message.setFileType("0");
                    }
                    if((json.containsKey("contentType")) && ("5".equals(json.getString("contentType")))) {
                        message.setFileType("5");
                    }
                    if((json.containsKey("contentType")) && ("6".equals(json.getString("contentType")))) {
                        message.setFileType("6");
                    }
                    Intent intent = new Intent("com.farsunset.cim.MESSAGE_RECEIVED");
                    intent.putExtra("message", message);
                    LvxinApplication.getInstance().sendBroadcast(intent);
                    msg.status = "1";
                } catch(Exception e) {
                    msg.status = "-3";
                }
                LvxinApplication.getInstance().sendBroadcast(statusintent);
            }
        });
    }
    
    public static String httpPost(String url, PubMenuEvent event) throws Exception {
        HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
        connection.setConnectTimeout(0x1388);
        connection.setRequestMethod("POST");
        connection.setReadTimeout(0x1388);
        OutputStream output = connection.getOutputStream();
        output.write(JSON.toJSONString(event).getBytes("UTF-8"));
        output.flush();
        output.close();
        Log.i("Pub_SENTBODY", JSON.toJSONString(event));
        if(0xc8 == connection.getResponseCode()) {
            String json = IOUtils.toString(connection.getInputStream(), "UTF-8");
            Log.i("Pub_REPLAY", json);
            connection.disconnect();
            return json;
        }
        return null;
    }
}
