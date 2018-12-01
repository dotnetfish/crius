package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class ChatItem
  implements Serializable
{
  public static final String NAME = ChatItem.class.getSimpleName();
  private static final long serialVersionUID = 1L;
  public Message message;
  public MessageItemSource source;
  
  public ChatItem() {}
  
  public ChatItem(Message paramMessage)
  {
    this.message = paramMessage;
  }
  
  public ChatItem(Message paramMessage, MessageItemSource paramMessageItemSource)
  {
    this.message = paramMessage;
    this.source = paramMessageItemSource;
  }
  
  public ChatItem(MessageItemSource paramMessageItemSource)
  {
    this.source = paramMessageItemSource;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof ChatItem))
    {
      paramObject = (ChatItem)paramObject;
      if ((this.source != null) && (((ChatItem)paramObject).source != null)) {
        return this.source.getId().equals(((ChatItem)paramObject).source.getId());
      }
      if ((this.message != null) && (((ChatItem)paramObject).message != null)) {
        return this.message.gid.equals(this.message.gid);
      }
    }
    return false;
  }
  
  public int hasCode()
  {
    if (this.source == null) {
      return this.message.gid.hashCode();
    }
    return this.source.getId().hashCode();
  }
}
