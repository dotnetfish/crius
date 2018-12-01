package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.Config;

import java.util.HashMap;
import java.util.List;

public class ConfigDBManager
  extends BaseDBManager<Config, String>
{
  static ConfigDBManager manager;
  
  private ConfigDBManager()
  {
    super(Config.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static ConfigDBManager getManager()
  {
    if (manager == null) {
      manager = new ConfigDBManager();
    }
    return manager;
  }
  
  public HashMap<String, String> queryConfigs()
  {
    List<Config> localObject = queryAll();
    HashMap localHashMap = new HashMap();
    for(Config config:localObject){

      localHashMap.put(config.key, config.value);
    }
   return  localHashMap;
  }
  
  public String queryValue(String paramString)
  {
    Config str = queryById(paramString);
    if (str == null) {
      return null;
    }
    return str.value;
  }
  
  public void saveConfig(String paramString1, String paramString2)
  {
    Config localConfig = new Config();
    localConfig.key = paramString1;
    localConfig.value = paramString2;
    createOrUpdate(localConfig);
  }
}
