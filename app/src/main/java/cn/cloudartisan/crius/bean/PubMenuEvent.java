package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class PubMenuEvent
  implements Serializable
{
  public static final String EVENT_TYPE_MENU = "menu";
  public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
  public static final String EVENT_TYPE_TEXT = "text";
  public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
  public static final long serialVersionUID = 1L;
  public String account;
  public String eventType;
  public String menuCode;
  public String text;
}
