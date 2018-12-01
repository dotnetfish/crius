package cn.cloudartisan.crius.bean;

import java.io.File;
import java.io.Serializable;

public class AlbumItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public File file;
  public String imageId;
  public String thumbnail;
  public long time;
}
