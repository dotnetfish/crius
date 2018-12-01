package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.io.Serializable;

@DatabaseTable(tableName="t_ichat_friend")
public class Friend
  extends MessageItemSource
  implements Serializable
{
  public static String[] messageType = { "0" };
  private static final long serialVersionUID = 1L;
  @DatabaseField(id=true)
  public String account;
  public String fristChar;
  @DatabaseField
  public String gender;
  @DatabaseField
  public String latitude;
  @DatabaseField
  public String location;
  @DatabaseField
  public String longitude;
  @DatabaseField
  public String motto;
  @DatabaseField
  public String name;
  @DatabaseField
  public String online = "0";
  
  public Friend() {}
  
  public Friend(String paramString)
  {
    this.account = paramString;
  }
  
  public int getDefaultIconRID()
  {
    return R.drawable.icon_head_default;
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
    return "0";
  }
  
  public String getTitle()
  {
    return this.name;
  }
  
  public String getWebIcon()
  {
    return FileURLBuilder.getUserIconUrl(this.account);
  }
}
