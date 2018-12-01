package cn.cloudartisan.crius.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import cn.cloudartisan.crius.bean.Article;
import cn.cloudartisan.crius.bean.Comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleDBManager
  extends BaseDBManager<Article, String>
{
  static ArticleDBManager manager;
  
  private ArticleDBManager()
  {
    super(Article.class);
  }
  
  public static void destory()
  {
    if (manager == null) {
      return;
    }
    manager.close();
    manager = null;
  }
  
  public static ArticleDBManager getManager()
  {
    if (manager == null) {
      manager = new ArticleDBManager();
    }
    return manager;
  }
  
  public void addComent(Comment paramComment)
  {
    Article localArticle = super.queryById(paramComment.articleId);
    if (localArticle == null) {
      return;
    }
    localArticle.setCommentList((List)JSON.parseObject(localArticle.comment, new TypeReference<Comment>() {}.getType(), new Feature[0]));
    localArticle.addComment(paramComment);
    super.executeSQL("update t_ichat_article set comment = ? where gid = ?", new String[] { JSON.toJSONString(localArticle.getCommentList()), paramComment.articleId });
  }
  
  public void deleteById(String paramString)
  {
    super.deleteById(paramString);
  }
  
  public List<Article> queryArticle(int paramInt)
  {
    long l = paramInt - 1;

    try
    {
        List<Article>  localList = this.databaseDao.queryBuilder().offset(Long.valueOf(l * 20L)).limit(Long.valueOf(20L)).orderBy("timestamp", false).query();


      for ( Article article:localList)
      {

          article.setCommentList((List)JSON.parseObject(article.comment, new TypeReference<Comment>() {}.getType(), new Feature[0]));
      }
      return localList;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
      return null;
  }
  
  public List<Article> queryArticles(String paramString, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    long l = paramInt - 1;
    try
    {
        List<Article> list = this.databaseDao.queryBuilder()
              .offset(l*20L)
               // .(l*20)
                .limit(Long.valueOf(20L))
                .orderBy("timestamp", false)
                .where().eq("account", paramString).query();
      return list;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return localArrayList;
  }
  
  public Article queryById(String paramString)
  {
    Article article = (Article)super.queryById(paramString);
    if (article == null) {
      return article;
    }
    article.setCommentList((List)JSON.parseObject(article.comment, new TypeReference<Comment>() {}.getType(), new Feature[0]));
    return article;
  }
  
  public void save(Article paramArticle)
  {
    paramArticle.comment = JSON.toJSONString(paramArticle.getCommentList());
    createOrUpdate(paramArticle);
  }
  
  public void save(List<Article> paramList)
  {
    clearAll();
    for(Article article:paramList){
      article.comment = JSON.toJSONString(article.getCommentList());

    }
    super.save(paramList);

  }
}

