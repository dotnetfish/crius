package cn.cloudartisan.crius.service.adapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;


import cn.cloudartisan.crius.app.Module;

public class CMSModuleAdapter implements Adapter<Module> {
    @Override
    public Module fromJson(JSONObject json) throws JSONException {
        Module module=new Module();
        //{"id":139,"parent_id":138,
        // "channel_id":0,
        // "nav_type":"System",
        // "name":"scan",
        // "title":"扫一扫","sub_title":"","icon_url":"/upload/201812/01/201812011438014886.png",
        // "link_url":"","sort_id":99,"is_lock":0,"remark":"",
        // "action_type":"Show,View,Instal","is_sys":0}
        module.setCode(json.getString("name"));
        module.setDisplayName(json.getString("title"));
        module.setIcon(json.getString("icon_url"));
        module.setShowType(json.getString("nav_type"));
        module.setLink(json.getString("link_url"));

        return module;
    }


    public List<Module> fromJson(JSONArray jsonArray) throws JSONException {
        List<Module> result=new ArrayList<>();
        for (Object obj: jsonArray
             ) {
            result.add(fromJson((JSONObject) obj));
        }
        return result;
    }
}
