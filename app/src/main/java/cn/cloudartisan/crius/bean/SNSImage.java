package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class SNSImage
  extends OSSImage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int oh;
  public int ow;
  public int th;
  public int tw;
}