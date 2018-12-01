package cn.cloudartisan.crius.service.adapter;

import android.util.JsonReader;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.cloudartisan.crius.bean.User;

public class CMSUserAdapter implements Adapter<User> {
    @Override
    public User fromJson(JSONObject json) throws JSONException {
        //{"id":1,"site_id":1,"group_id":1,
        // "user_name":"test","salt":"4R04B6","password":"",
        // "mobile":"13695245546",
        // "email":"test@qq.com","avatar":"","nick_name":"测试用户","sex":"保密",
        // "birthday":null,"telphone":"","area":"","address":"",
        // "qq":"","msn":"","amount":0.00,"point":30,
        // "exp":30,"status":0,
        // "reg_time":"\/Date(1492540330127)\/",
        // "reg_ip":"127.0.0.1",
        // "Token":"26XXV06J28XN44TVL864"}
        User user=new User();
        //user.setEmail(email)
        //user.setMotto(json.getString("token"));

        user.setUserToken(json.getString("token"));

        user.setAccount(json.getString("user_name"));
        user.setName(json.getString("nick_name"));
        user.setGender(json.getString("sex"));
        return  user;
    }
}
