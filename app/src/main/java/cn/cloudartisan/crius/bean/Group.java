package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName="t_ichat_group")
public class Group
  extends MessageItemSource
  implements Serializable
{
  public static String[] messageType = { "1", "3" };
  public static final long serialVersionUID = 4733464888738356502L;
  @DatabaseField
  public String category;
  @DatabaseField
  public String founder;
  @DatabaseField(id=true)
  public String groupId;
  public List<GroupMember> memberList;
  @DatabaseField
  public String name;
  @DatabaseField
  public String summary;
  
  public Group() {}
  
  public Group(String paramString)
  {
    this.groupId = paramString;
  }
  
  public void addMember(GroupMember paramGroupMember)
  {
    if (this.memberList == null) {
      this.memberList = new ArrayList();
    }
    this.memberList.add(paramGroupMember);
  }
  
  public int getDefaultIconRID()
  {
    return R.drawable.grouphead_normal;
  }
  
  public String getId()
  {
    return this.groupId;
  }
  
  public int getMemberCount()
  {
    if (this.memberList == null) {
      return 0;
    }
    return this.memberList.size();
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
    return "1";
  }
  
  public String getTitle()
  {
    return this.name;
  }
  
  public String getWebIcon()
  {
    return FileURLBuilder.getGroupIconUrl(this.groupId);
  }
}
