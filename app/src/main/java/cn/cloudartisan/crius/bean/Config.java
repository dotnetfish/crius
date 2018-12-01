package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

@DatabaseTable(tableName="t_ichat_config")
public class Config
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String description;
  public String domain;
  public String gid;
  @DatabaseField(id=true)
  public String key;
  @DatabaseField
  public String value;
}
