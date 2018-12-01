package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

@DatabaseTable(tableName="t_ichat_groupMember")
public class GroupMember
  implements Serializable
{
  public static final long serialVersionUID = 4733464888738356502L;
  @DatabaseField
  public String account;
  @DatabaseField(id=true)
  public String gid;
  @DatabaseField
  public String groupId;
  @DatabaseField
  public String host;
}
