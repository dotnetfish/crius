package cn.cloudartisan.crius.services;

import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.contact.AllyRequestActivity;

/**
 * Created by kenqu on 2015/12/20.
 */
public class ContactService {
    HttpAPIRequester requester;
    public  void ContactService(HttpAPIResponser responser){
        requester=new HttpAPIRequester(responser);
    }

    public  void SendMessage(Message msg){
        requester.execute(new TypeReference<AllyRequestActivity>() {}.getType(),
                null,
                URLConstant.MESSAGE_SEND_URL);
    }
}
