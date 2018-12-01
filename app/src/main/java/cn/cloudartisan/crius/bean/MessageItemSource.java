package cn.cloudartisan.crius.bean;

import cn.cloudartisan.crius.R;

import java.io.Serializable;

public abstract class MessageItemSource
  implements Serializable
{
  public static final String NAME = MessageItemSource.class.getSimpleName();
  public static final String SOURCE = "SOURCE";
  public static final String SOURCE_BOTTLE = "7";
  public static final String SOURCE_GROUP = "1";
  public static final String SOURCE_PUBACCOUNT = "5";
  public static final String SOURCE_SYSTEM = "2";
  public static final String SOURCE_USER = "0";
  private static final long serialVersionUID = 1L;
  
  public boolean equals(Object paramObject)
  {

    boolean bool1 = false;
    if ((paramObject instanceof MessageItemSource))
    {
      MessageItemSource messageItemSource = (MessageItemSource)paramObject;

      if (getSourceType().equals(messageItemSource.getSourceType()))
      {

        if (getId().equals(messageItemSource.getId())) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public abstract int getDefaultIconRID();
  
  public abstract String getId();
  
  public abstract String[] getMessageType();
  
  public abstract String getName();
  
  public abstract String getSourceType();
  
  public abstract String getTitle();
  
  public int getTitleColor()
  {
    return R.color.text_chat_color;
  }
  
  public abstract String getWebIcon();
}
