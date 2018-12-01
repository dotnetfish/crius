package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.Friend;
import java.util.List;

public class FriendDBManager
  extends BaseDBManager<Friend, String>
{
  static FriendDBManager manager;
  
  private FriendDBManager()
  {
    super(Friend.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static FriendDBManager getManager()
  {
    if (manager == null) {
      manager = new FriendDBManager();
    }
    return manager;
  }
  
  public void deleteFriend(String paramString)
  {
    deleteById(paramString);
  }
  
  public boolean isFriend(String paramString)
  {
    return queryById(paramString) != null;
  }
  
  public void modifyNameAndMotto(String paramString1, String paramString2, String paramString3)
  {
    executeSQL("update t_ichat_friend set name=?,motto = ?  where account=?", new String[] { paramString2, paramString3, paramString1 });
  }
  
  public void modifyOnlineStatus(String paramString1, String paramString2)
  {
    executeSQL("update t_ichat_friend set online=? where account=?", new String[] { paramString2, paramString1 });
  }
  
  public Friend queryFriend(String paramString)
  {
    return (Friend)queryById(paramString);
  }
  
  public List<Friend> queryFriendList()
  {
    return queryAll();
  }
  
  public void saveFriend(Friend paramFriend)
  {
    save(paramFriend);
  }
  
  public void saveFriends(List<Friend> paramList)
  {
    clearAll();
    save(paramList);
  }
}


/* Location:              F:\download\classes-dex2jar.jar!\com\farsunset\lvxin\db\FriendDBManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */