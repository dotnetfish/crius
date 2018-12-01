package cn.cloudartisan.crius.db;

import android.content.Context;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.LvxinApplication;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDBManager<T, ID>
{
  Context _context = LvxinApplication.getInstance();
  Dao<T, ID> databaseDao;
  GlobalDatabaseHelper mDatabaseHelper = GlobalDatabaseHelper.getHelper(this._context, getDatabaseName());
  
  public BaseDBManager(Class<T> paramClass)
  {
    try
    {
      this.databaseDao = this.mDatabaseHelper.getDao(paramClass);

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void clearAll()
  {
    try
    {
      this.databaseDao.deleteBuilder().delete();

    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
  }
  
  public void close()
  {
    this._context = null;
    this.mDatabaseHelper.close();
    this.mDatabaseHelper = null;
    this.databaseDao.clearObjectCache();
    this.mDatabaseHelper = null;
  }
  
  public void createOrUpdate(T paramT)
  {
    try
    {
      this.databaseDao.createOrUpdate(paramT);
      return;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void delete(T paramT)
  {
    try
    {
      this.databaseDao.delete(paramT);
      return;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void deleteById(ID paramID)
  {
    try
    {
      this.databaseDao.deleteById(paramID);
      return;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void executeSQL(String paramString)
  {
    try
    {
      this.databaseDao.executeRawNoArgs(paramString);

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void executeSQL(String paramString, String[] paramArrayOfString)
  {
    try
    {
      this.databaseDao.executeRaw(paramString, paramArrayOfString);

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public String getDatabaseName()
  {
    return Global.getCurrentUser().getAccount() + ".db";
  }
  
  public List<T> queryAll()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      List localList = this.databaseDao.queryForAll();
      return localList;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    return localArrayList;
  }
  
  public T queryById(ID paramID)
  {
    try
    {
     T obj = this.databaseDao.queryForId(paramID);
      return obj;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public void save(T paramT)
  {
    try
    {
      this.databaseDao.create(paramT);

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void save(List<T> paramList)
  {
    try
    {
for(T obj:paramList){
  this.databaseDao.create(obj);
}


    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
  
  public void update(T paramT)
  {
    try
    {
      this.databaseDao.update(paramT);

    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
  }
}
