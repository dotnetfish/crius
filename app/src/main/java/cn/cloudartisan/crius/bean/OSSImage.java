package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class OSSImage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String bucket = "lvxin-files";
  public String image;
  public String thumbnail;
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof OSSImage)) {
      return ((OSSImage)paramObject).image.equals(this.image);
    }
    return false;
  }
  
  public int hasCode()
  {
    return this.image.hashCode();
  }
}
