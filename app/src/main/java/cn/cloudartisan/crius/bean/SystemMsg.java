package cn.cloudartisan.crius.bean;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.CriusApplication;

import java.io.Serializable;

public class SystemMsg
  extends MessageItemSource
  implements Serializable
{
  public static String HANDLE_RESULT;
  public static String RESULT_AGREE;
  public static String RESULT_IGNORE = "3";
  public static String RESULT_REFUSE;
  public static String id = "system";
  public static String[] messageType = { "2" };
  public static final long serialVersionUID = 4733464888738356502L;
  public String name;
  String type;
  
  static
  {
    HANDLE_RESULT = "handleResult";
    RESULT_AGREE = "1";
    RESULT_REFUSE = "2";
  }
  
  public SystemMsg(String paramString)
  {
    this.type = paramString;
    this.name = getTypeText(paramString);
  }
  
  public static String getTypeText(String paramString)
  {
    if ("2".equals(paramString)) {
      return CriusApplication.getInstance().getString(R.string.common_sysmessage);
    }
    if ("100".equals(paramString)) {
      return CriusApplication.getInstance().getString(R.string.tip_title_friendmessage);
    }
    if (("102".equals(paramString)) || ("105".equals(paramString))) {
      return CriusApplication.getInstance().getString(R.string.tip_title_groupmessage);
    }
    return CriusApplication.getInstance().getString(R.string.common_sysmessage);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if ((paramObject instanceof MessageItemSource))
    {
      paramObject = (MessageItemSource)paramObject;
      bool1 = bool2;
      if (getSourceType().equals(((MessageItemSource)paramObject).getSourceType()))
      {
        bool1 = bool2;
        if (getId().equals(((MessageItemSource)paramObject).getId())) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public int getDefaultIconRID()
  {
    return R.drawable.icon_system_message;
  }
  
  public String getId()
  {
    return id;
  }
  
  public String[] getMessageType()
  {
    return messageType;
  }
  
  public String getName()
  {
    return CriusApplication.getInstance().getString(R.string.common_system);
  }
  
  public String getSourceType()
  {
    return "2";
  }
  
  public String getTitle()
  {
    return this.name;
  }
  
  public String getWebIcon()
  {
    return null;
  }
}
