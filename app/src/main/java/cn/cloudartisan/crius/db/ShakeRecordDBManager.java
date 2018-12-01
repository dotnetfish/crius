package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.ShakeRecord;
import java.util.List;

public class ShakeRecordDBManager
  extends BaseDBManager<ShakeRecord, String>
{
  static ShakeRecordDBManager manager;
  
  private ShakeRecordDBManager()
  {
    super(ShakeRecord.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static ShakeRecordDBManager getManager()
  {
    if (manager == null) {
      manager = new ShakeRecordDBManager();
    }
    return manager;
  }
  
  public void deleteShakeRecord(String paramString)
  {
    deleteById(paramString);
  }
  
  public List<ShakeRecord> queryRecordList()
  {
    return super.queryAll();
  }
  
  public ShakeRecord queryShakeRecord(String paramString)
  {
    return (ShakeRecord)queryById(paramString);
  }
  
  public void saveShakeRecord(ShakeRecord paramShakeRecord)
  {
    save(paramShakeRecord);
  }
}
