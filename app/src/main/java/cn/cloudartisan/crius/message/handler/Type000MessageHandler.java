
package cn.cloudartisan.crius.message.handler;

import android.content.Context;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.network.OSSFileLoader;

public class Type000MessageHandler implements CustomMessageHandler {
    
    public boolean handle(Context context, Message message) {
        if(message.fileType.equals("2")) {
            OSSFileLoader.getFileLoader(context).loadVoiceFile("lvxin-files", message.file, null);
        }
        return true;
    }
}
