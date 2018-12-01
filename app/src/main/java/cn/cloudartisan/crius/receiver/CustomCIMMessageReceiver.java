/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.receiver;

import android.content.Intent;
import android.net.NetworkInfo;
import android.util.Log;
import cn.cloudartisan.crius.client.android.CIMEventBroadcastReceiver;
import cn.cloudartisan.crius.client.android.CIMEventListener;
import cn.cloudartisan.crius.client.android.CIMListenerManager;
import cn.cloudartisan.crius.client.model.Message;
import cn.cloudartisan.crius.client.model.ReplyBody;
import cn.cloudartisan.crius.app.GlobalMediaPlayer;
import cn.cloudartisan.crius.app.LvxinApplication;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.message.handler.CustomMessageHandlerFactory;
import cn.cloudartisan.crius.network.MessageReceiptHandler;
import cn.cloudartisan.crius.service.MessageNotifyService;
import cn.cloudartisan.crius.ui.HomeActivity;
import cn.cloudartisan.crius.ui.chat.FriendChatActivity;
import cn.cloudartisan.crius.ui.chat.GroupChatActivity;
import cn.cloudartisan.crius.ui.trend.BottleChatActivity;
import cn.cloudartisan.crius.util.MessageUtil;

public final class CustomCIMMessageReceiver extends CIMEventBroadcastReceiver {
    
    public void onMessageReceived(Message message) {
        Log.e("cimReceiver",""+message);
        MessageReceiptHandler.handleReceipt(message.getMid(), message.getType());
        cn.cloudartisan.crius.bean.Message beanMessage = MessageUtil.transform(message);
        MessageDBManager.getManager().saveMessage(beanMessage);
        if(CustomMessageHandlerFactory.getFactory().handle(super.context, message)) {
            for(CIMEventListener listener:CIMListenerManager.getCIMListeners()){
                listener.onMessageReceived(message);
            }
           /* for(int var2 = 0; var2 < CIMListenerManager.getCIMListeners().size(); ++var2) {
                Log.i(this.getClass().getSimpleName(), "########################" + ((CIMEventListener)CIMListenerManager.getCIMListeners().get(var2)).getClass().getName() + ".onMessageReceived################");
                ((CIMEventListener)CIMListenerManager.getCIMListeners().get(var2)).onMessageReceived(message);
            }*/

            if(beanMessage.isNeedSound() && !LvxinApplication.getTopActivity().equals(HomeActivity.class.getName()) && !LvxinApplication.getTopActivity().equals(GroupChatActivity.class.getName()) && !LvxinApplication.getTopActivity().equals(FriendChatActivity.class.getName()) && !LvxinApplication.getTopActivity().equals(BottleChatActivity.class.getName())) {
                GlobalMediaPlayer.getPlayer().playMessageSound();
            }

            if(this.isInBackground(this.context)) {
                Intent var4 = new Intent(this.context, MessageNotifyService.class);
                var4.putExtra("message", beanMessage);
                this.context.startService(var4);
                return;
            }
        }

    }
    
    public void onNetworkChanged(NetworkInfo info) {
        for(CIMEventListener listener:CIMListenerManager.getCIMListeners()){
            listener.onNetworkChanged(info);
        }
           }
    
    public void onReplyReceived(ReplyBody body) {
        for(CIMEventListener listener:CIMListenerManager.getCIMListeners()){
            listener.onReplyReceived(body);
        }
           }
    
    public void onConnectionStatus(boolean arg0) {
        for(CIMEventListener listener:CIMListenerManager.getCIMListeners()){
            listener.onConnectionStatus(arg0);
        }

    }
}
