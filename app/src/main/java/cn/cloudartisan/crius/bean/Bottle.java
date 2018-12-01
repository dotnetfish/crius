package cn.cloudartisan.crius.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.CriusApplication;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.io.Serializable;

@DatabaseTable(tableName="t_ichat_bottle")
public class Bottle
  extends MessageItemSource
  implements Serializable
{
  public static String[] messageType = { "700", "0" };
  private static final long serialVersionUID = 1L;
  @DatabaseField
  public String content;
  @DatabaseField(id=true)
  public String gid;
  @DatabaseField
  public String length;
  @DatabaseField
  public String receiver;
  @DatabaseField
  public String region;
  @DatabaseField
  public String sender;
  @DatabaseField
  public int status;
  @DatabaseField
  public String timestamp;
  @DatabaseField
  public int type;
  
  @JSONField(serialize=false)
  public int getDefaultIconRID()
  {
    return 0;
  }
  
  @JSONField(serialize=false)
  public String getId()
  {
    if (Global.getCurrentUser().getAccount().equals(this.sender)) {
      return this.receiver;
    }
    return this.sender;
  }
  
  public String[] getMessageType()
  {
    return messageType;
  }
  
  @JSONField(serialize=false)
  public String getName()
  {
    if (this.region == null) {
      return "未知区域";
    }
    return this.region;
  }
  
  @JSONField(serialize=false)
  public String getSourceType()
  {
    return "7";
  }
  
  @JSONField(serialize=false)
  public String getTitle()
  {
    return CriusApplication.getInstance().getString(R.string.label_function_bottle);
  }
  
  @JSONField(serialize=false)
  public String getWebIcon()
  {
    if (Global.getCurrentUser().getAccount().equals(this.sender)) {
      return FileURLBuilder.getUserIconUrl(this.receiver);
    }
    return FileURLBuilder.getUserIconUrl(this.sender);
  }
}
