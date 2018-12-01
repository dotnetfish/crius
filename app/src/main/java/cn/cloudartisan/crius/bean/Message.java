package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Arrays;

@DatabaseTable(tableName="t_ichat_message")
public class Message
  implements Serializable
{
  public static final String NAME = Message.class.getSimpleName();
  public static final String STATUS_NOT_READ = "0";
  public static final String STATUS_READ = "1";
  public static final String STATUS_READ_OF_VOICE = "2";
  public static final long serialVersionUID = 1845362556725768545L;
  @DatabaseField
  public String content;
  @DatabaseField
  public String createTime;
  @DatabaseField
  public String file;
  @DatabaseField
  public String fileType;
  @DatabaseField(id=true)
  public String gid;
  String[] isNeedSoundTypes = { "0", "2", "3", "100", "101", "102", "103", "104", "105", "106", "107" };
  @DatabaseField
  public String receiver;
  @DatabaseField
  public String sender;
  @DatabaseField
  public String status;
  @DatabaseField
  public String title;
  @DatabaseField
  public String type;
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Message)) {
      return ((Message)paramObject).gid.equals(this.gid);
    }
    return false;
  }
  
  public int hasCode()
  {
    return this.gid.hashCode();
  }
  
  public boolean isActionMessage()
  {
    return this.type.startsWith("9");
  }
  
  public boolean isNeedShowReadStatus()
  {
    return (this.type.equals("0")) || (this.type.equals("700"));
  }
  
  public boolean isNeedSound()
  {
    return Arrays.asList(this.isNeedSoundTypes).contains(this.type);
  }
  
  public boolean isNoNeedShow()
  {
    return (this.type.startsWith("9")) || (this.type.startsWith("8"));
  }
}
