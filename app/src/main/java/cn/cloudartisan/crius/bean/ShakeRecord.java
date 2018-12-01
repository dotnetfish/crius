package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.io.Serializable;

@DatabaseTable(tableName="t_ichat_shakerecord")
public class ShakeRecord
  extends MessageItemSource
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  @DatabaseField(id=true)
  public String account;
  @DatabaseField
  public String gender;
  @DatabaseField
  public String latitude;
  @DatabaseField
  public String longitude;
  @DatabaseField
  public String name;
  
  public static ShakeRecord toShakeRecord(Friend paramFriend)
  {
    ShakeRecord localShakeRecord = new ShakeRecord();
    localShakeRecord.account = paramFriend.account;
    localShakeRecord.name = paramFriend.name;
    localShakeRecord.gender = paramFriend.gender;
    localShakeRecord.latitude = paramFriend.latitude;
    localShakeRecord.longitude = paramFriend.longitude;
    return localShakeRecord;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof ShakeRecord)) {
      return ((ShakeRecord)paramObject).account.equals(this.account);
    }
    return false;
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
    return new String[0];
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
  
  public int hasCode()
  {
    return getClass().getName().hashCode() + this.account.hashCode();
  }
  
  public Friend toFriend()
  {
    Friend localFriend = new Friend();
    localFriend.account = this.account;
    localFriend.name = this.name;
    localFriend.gender = this.gender;
    localFriend.latitude = this.latitude;
    localFriend.longitude = this.longitude;
    return localFriend;
  }
}
