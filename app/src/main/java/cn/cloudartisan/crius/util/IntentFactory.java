package cn.cloudartisan.crius.util;

import cn.cloudartisan.crius.app.Module;
import cn.cloudartisan.crius.component.BaseWebActivity;

/**
 * Created by kenqu on 2016/7/31.
 */
public class IntentFactory {
    public static Class getIntentClass(Module module) throws Exception{
        switch (module.getShowType()){
            case "intent":
                return  Class.forName(module.getLink());
            default:
            case "":
               return BaseWebActivity.class;
        }
    }
}
