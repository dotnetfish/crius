package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDBManager
  extends BaseDBManager<Group, String>
{
  static GroupDBManager manager;
  
  private GroupDBManager()
  {
    super(Group.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static GroupDBManager getManager()
  {
    if (manager == null) {
      manager = new GroupDBManager();
    }
    return manager;
  }
  
  public void deleteGroup(String paramString)
  {
    deleteById(paramString);
  }
  
  public Group queryGroup(String paramString)
  {
    Group localGroup =queryById(paramString);
    if (localGroup == null) {
      return null;
    }
    localGroup.memberList = GroupMemberDBManager.getManager().queryMemberList(paramString);
    return localGroup;
  }
  
  public List<Group> queryGroupList()
  {
    return super.queryAll();
  }
  
  public List<Group> queryJoinList(String paramString)
  {
    try
    {
        List<Group>  list = this.databaseDao.queryBuilder().where().ne("founder", paramString).query();
      return list;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return new ArrayList();
  }
  
  public List<Group> queryMyCreatedGroupList(String paramString)
  {
    try
    {
        //this.databaseDao.query(bu)
        List<Group> list = this.databaseDao.queryBuilder()
                .where()
                .eq("founder", paramString).query();
      return list;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return new ArrayList();
  }
  
  public void saveGroup(Group paramGroup)
  {
    save(paramGroup);
  }
  
  public void saveGroups(List<Group> paramList)
  {
    clearAll();
    save(paramList);

    for (Group group:paramList)
    {

          GroupMemberDBManager.getManager().saveMembers(group.memberList);



         }
  }
}
