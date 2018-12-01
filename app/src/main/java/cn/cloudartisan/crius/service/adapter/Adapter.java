package cn.cloudartisan.crius.service.adapter;



import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.cloudartisan.crius.bean.User;

public interface Adapter<T> {
    T fromJson(JSONObject jsonUser) throws JSONException;
}
