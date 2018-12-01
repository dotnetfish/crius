package cn.cloudartisan.crius.bean;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.util.FileURLBuilder;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName="t_ichat_publicaccount")
public class PublicAccount
  extends MessageItemSource
  implements Serializable
{
  public static String[] messageType = { "200", "201" };
  public static final long serialVersionUID = 1L;
  @DatabaseField(id=true)
  public String account;
  @DatabaseField
  public String apiUrl;
  @DatabaseField
  public String description;
  @DatabaseField
  public String greet;
  @DatabaseField
  public String link;
  public List<PublicMenu> menuList;
  @DatabaseField
  public String name;
  @DatabaseField
  public String power;
  
  public int getDefaultIconRID()
  {
    return R.drawable.icon_contact_public;
  }
  
  public String getId()
  {
    return this.account;
  }
  
  public String[] getMessageType()
  {
    return messageType;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getSourceType()
  {
    return "5";
  }
  
  public String getTitle()
  {
    return this.name;
  }
  
  public int getTitleColor()
  {
    return R.color.text_blue;
  }
  
  public String getWebIcon()
  {
    return FileURLBuilder.getPubAccountLogoUrl(this.account);
  }
}
