package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

@DatabaseTable(tableName="t_ichat_publicmenu")
public class PublicMenu
  implements Serializable
{
  public static final String TYPE_API = "1";
  public static final String TYPE_ROOT = "0";
  public static final String TYPE_TEXT = "3";
  public static final String TYPE_WEB = "2";
  public static final long serialVersionUID = 1L;
  @DatabaseField
  public String account;
  @DatabaseField
  public String code;
  @DatabaseField
  public String content;
  @DatabaseField
  public String fid;
  @DatabaseField(id=true)
  public String gid;
  @DatabaseField
  public String link;
  @DatabaseField
  public String name;
  @DatabaseField
  public int sort;
  @DatabaseField
  public String type;
  
  public boolean hasSubMenu()
  {
    return "0".equals(this.type);
  }
  
  public int hashCode()
  {
    return this.sort;
  }
  
  public boolean isApiMenu()
  {
    return "1".equals(this.type);
  }
  
  public boolean isRootMenu()
  {
    return this.fid == null;
  }
  
  public boolean isTextMenu()
  {
    return "3".equals(this.type);
  }
  
  public boolean isWebMenu()
  {
    return "2".equals(this.type);
  }
}