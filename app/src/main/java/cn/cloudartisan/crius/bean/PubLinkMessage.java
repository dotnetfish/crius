package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class PubLinkMessage
  implements Serializable
{
  public static final String NAME = PubLinkMessage.class.getSimpleName();
  private static final long serialVersionUID = 1L;
  public String content;
  public String image;
  public String link;
  public String title;
}