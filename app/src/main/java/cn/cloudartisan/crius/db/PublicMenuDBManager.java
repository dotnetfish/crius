package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.PublicMenu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublicMenuDBManager
  extends BaseDBManager<PublicMenu, String>
{
  static PublicMenuDBManager manager;
  
  private PublicMenuDBManager()
  {
    super(PublicMenu.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static PublicMenuDBManager getManager()
  {
    if (manager == null) {
      manager = new PublicMenuDBManager();
    }
    return manager;
  }
  
  public void deleteByAccount(String paramString)
  {
    executeSQL("delete from t_ichat_publicmenu where account = ?", new String[] { paramString });
  }
  
  public PublicMenu queryPublicMenu(String paramString)
  {
    return (PublicMenu)queryById(paramString);
  }
  
  public List<PublicMenu> queryPublicMenuList(String paramString)
  {
    try
    {

      return this.databaseDao.queryBuilder().where().eq("account", paramString).query();
    }
    catch (SQLException ex)
    {
      ex.printStackTrace();
    }
    return new ArrayList();
  }
  
  public void savePublicMenus(List<PublicMenu> paramList)
  {
    super.clearAll();
    save(paramList);
  }
}