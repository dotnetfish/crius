package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.GroupMember;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupMemberDBManager
  extends BaseDBManager<GroupMember, String>
{
  static GroupMemberDBManager manager;
  
  private GroupMemberDBManager()
  {
    super(GroupMember.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static GroupMemberDBManager getManager()
  {
    if (manager == null) {
      manager = new GroupMemberDBManager();
    }
    return manager;
  }
  
  public void delete(String paramString1, String paramString2)
  {
    executeSQL("delete from t_ichat_groupMember where groupId=? and account=?", new String[] { paramString1, paramString2 });
  }
  
  public void deleteByGID(String paramString)
  {
    executeSQL("delete from t_ichat_groupMember where groupId=?", new String[] { paramString });
  }
  
  public GroupMember queryMember(String paramString1, String paramString2)
  {
    try
    {
      return this.databaseDao.queryBuilder().offset(Long.valueOf(0L)).limit(Long.valueOf(1L)).where().eq("account", paramString2).and().eq("groupId", paramString1).queryForFirst();

    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    return null;
  }
  
  public List<String> queryMemberAccountList(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      localArrayList = (ArrayList) Arrays.asList(this.databaseDao.queryBuilder().selectColumns(new String[] { "account" }).where().eq("groupId", paramString).queryRawFirst());

    }
    catch (SQLException ex) {}
    return localArrayList;
  }
  
  public List<GroupMember> queryMemberList(String paramString)
  {
    try
    {
      return this.databaseDao.queryBuilder().where().eq("groupId", paramString).query();

    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    return new ArrayList();
  }
  
  public void saveMember(GroupMember paramGroupMember)
  {
    createOrUpdate(paramGroupMember);
  }
  
  public void saveMembers(List<GroupMember> paramList)
  {
    if ((paramList == null) || (paramList.isEmpty())) {
      return;
    }
    deleteByGID((paramList.get(0)).groupId);
    save(paramList);
  }
}
