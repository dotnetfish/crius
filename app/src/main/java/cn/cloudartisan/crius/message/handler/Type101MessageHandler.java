
package cn.cloudartisan.crius.message.handler;

import android.content.Context;
import android.content.Intent;
import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.ChatItem;
import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.db.FriendDBManager;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.message.parser.MessageParser;
import cn.cloudartisan.crius.message.parser.MessageParserFactory;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Type101MessageHandler implements CustomMessageHandler, HttpAPIResponser {
    public HashMap<String, Object> apiParams;
    HttpAPIRequester detailedRequester;
    
    public boolean handle(Context context, Message message) {
        MessageParser messageParser = MessageParserFactory.getFactory().getMessageParser(message.type);
        Friend friend = (Friend)messageParser.decodeContent(message);
        FriendDBManager.getManager().saveFriend(friend);
        apiParams.put("account", friend.account);
        detailedRequester.execute(new TypeReference<Type101MessageHandler>(){}.getType(), null, URLConstant.USER_DETAILED_URL);
        Message msg = new Message();
        msg.createTime = String.valueOf(System.currentTimeMillis());
        msg.gid = String.valueOf(System.currentTimeMillis());
        msg.type = "0";
        msg.sender = friend.account;
        msg.receiver = message.receiver;
        msg.status = "0";
        msg.content = context.getString(R.string.tip_friend_hellow_message);
        MessageDBManager.getManager().saveMessage(msg);
        ChatItem chatItem = new ChatItem();
        chatItem.source = friend;
        chatItem.message = msg;
        Intent intent = new Intent();
        intent.putExtra("data", chatItem);
        intent.setAction("com.farsunset.cim.MESSAGE_APPEND");
        context.sendBroadcast(intent);
        return true;
    }
    
    public void onSuccess(Object data, List list, Page page, String code, String url) {
        if(CIMConstant.ReturnCode.CODE_200.equals(data) && data instanceof Friend) {
            Friend var6 = (Friend)data;
            FriendDBManager.getManager().deleteFriend(var6.account);
            FriendDBManager.getManager().saveFriend(var6);
        }
    }
    
    public void onFailed(Exception e) {
    }
    
    public Map getRequestParams(String code) {
        return apiParams;
    }
    
    public void onRequest() {
    }
}
