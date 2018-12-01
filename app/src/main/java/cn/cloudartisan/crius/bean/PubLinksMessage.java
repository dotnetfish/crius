package cn.cloudartisan.crius.bean;

import java.io.Serializable;
import java.util.List;

public class PubLinksMessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String image;
  public List<PubLinksMessage> items;
  public String link;
  public String title;
  
  public PubLinksMessage getSubLink(int paramInt)
  {
    return (PubLinksMessage)this.items.get(paramInt);
  }
  
  public boolean hasMore()
  {
    return (this.items != null) && (!this.items.isEmpty());
  }
}
