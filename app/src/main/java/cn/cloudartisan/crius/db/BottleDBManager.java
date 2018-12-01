package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.Bottle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BottleDBManager
  extends BaseDBManager<Bottle, String>
{
  static BottleDBManager manager;
  
  private BottleDBManager()
  {
    super(Bottle.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static BottleDBManager getManager()
  {
    if (manager == null) {
      manager = new BottleDBManager();
    }
    return manager;
  }
  
  public void clear()
  {
    clearAll();
  }
  
  public void deleteById(String paramString)
  {
    super.deleteById(paramString);
  }
  
  public boolean isExist(String paramString)
  {
    boolean bool = false;
    try
    {
      long l = this.databaseDao.queryBuilder().where().eq("sender", paramString).countOf();
      if (l > 0L) {
        bool = true;
      }
      return bool;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public List<Bottle> queryBottleList()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      List localList = this.databaseDao.queryBuilder()
              .orderBy("timestamp", false).query();
      return localList;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    return localArrayList;
  }
  
  public Bottle queryById(String paramString)
  {
    return (Bottle)super.queryById(paramString);
  }
  
  public Bottle queryBySender(String paramString)
  {
    try
    {
        Bottle obj = (Bottle)this.databaseDao.queryBuilder()
                .offset(Long.valueOf(0L))
                .limit(Long.valueOf(1L))
                .where().eq("sender", paramString).or()
                .eq("receiver", paramString).queryForFirst();
      return obj;
    }
    catch (SQLException w)
    {
      w.printStackTrace();
    }
    return null;
  }
  
  public void save(Bottle paramBottle)
  {
    super.save(paramBottle);
  }
}