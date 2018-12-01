package cn.cloudartisan.crius.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.cloudartisan.crius.bean.Article;
import cn.cloudartisan.crius.bean.Bottle;
import cn.cloudartisan.crius.bean.NewsInfo;

public class ProductDBManager extends BaseDBManager<NewsInfo, String> {
    private ProductDBManager() {
        super(NewsInfo.class);
    }
    static ProductDBManager manager;



    public static void destory()
    {
        if (manager == null) {
            return;
        }
        manager.close();
        manager = null;
    }

    public static ProductDBManager getManager()
    {
        if (manager == null) {
            manager = new ProductDBManager();
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

    public  List<NewsInfo> queryProductList(){
        ArrayList localArrayList = new ArrayList();
        try
        {
            List localList = this.databaseDao.queryBuilder()
                    .orderBy("publishTime", false).query();
            return localList;
        }
        catch (SQLException localSQLException)
        {
            localSQLException.printStackTrace();
        }
        return localArrayList;
    }

}
