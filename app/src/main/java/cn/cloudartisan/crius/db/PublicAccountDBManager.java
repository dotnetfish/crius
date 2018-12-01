package cn.cloudartisan.crius.db;

import cn.cloudartisan.crius.bean.PublicAccount;

import java.util.List;

public class PublicAccountDBManager
  extends BaseDBManager<PublicAccount, String>
{
  static PublicAccountDBManager manager;
  
  private PublicAccountDBManager()
  {
    super(PublicAccount.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static PublicAccountDBManager getManager()
  {
    if (manager == null) {
      manager = new PublicAccountDBManager();
    }
    return manager;
  }
  
  public void deletePublicAccount(String paramString)
  {
    deleteById(paramString);
  }
  
  public PublicAccount queryPublicAccount(String paramString)
  {
    return (PublicAccount)queryById(paramString);
  }
  
  public List<PublicAccount> queryPublicAccountList()
  {
    return queryAll();
  }
  
  public void savePublicAccount(PublicAccount paramPublicAccount)
  {
    save(paramPublicAccount);
  }
  
  public void savePublicAccounts(List<PublicAccount> paramList) {
    clearAll();
    save(paramList);

    for (PublicAccount publicAccount : paramList) {

      PublicMenuDBManager.getManager().savePublicMenus(publicAccount.menuList);
    }
  }


}

