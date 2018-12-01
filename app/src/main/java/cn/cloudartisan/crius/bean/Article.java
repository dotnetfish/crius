package cn.cloudartisan.crius.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName="t_ichat_article")
public class Article
  implements Serializable
{
  public static final String TYPE_LINK = "1";
  public static final String TYPE_TEXT_IMAGE = "0";
  private static final long serialVersionUID = 1L;
  @DatabaseField
  public String account;
  @DatabaseField
  public String comment;
  private List<Comment> commentList = new ArrayList();
  @DatabaseField
  public String content;
  @DatabaseField(id=true)
  public String gid;
  @DatabaseField
  public String link;
  @DatabaseField
  public String thumbnail;
  @DatabaseField
  public String timestamp;
  @DatabaseField
  public String type;
  
  public void addComment(Comment paramComment)
  {
    if (this.commentList == null) {
      this.commentList = new ArrayList();
    }
    this.commentList.add(paramComment);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof Article)) {
      return ((Article)paramObject).gid.equals(this.gid);
    }
    return false;
  }
  
  public List<Comment> getCommentList()
  {
    if (this.commentList == null) {
      this.commentList = new ArrayList();
    }
    return this.commentList;
  }
  
  public int hasCode()
  {
    return this.gid.hashCode();
  }
  
  public void setCommentList(List<Comment> paramList)
  {
    this.commentList = paramList;
  }
}
