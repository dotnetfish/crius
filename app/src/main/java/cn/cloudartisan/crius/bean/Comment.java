package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class Comment
  implements Serializable
{
  public static final String TYPE_0 = "0";
  public static final String TYPE_1 = "1";
  private static final long serialVersionUID = 1L;
  public String account;
  public String articleId;
  public String content;
  public String gid;
  public String timestamp;
  public String type = "0";
}
