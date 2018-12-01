package cn.cloudartisan.crius.client.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class ReplyBody
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String code;
  private HashMap<String, String> data = new HashMap();
  private String key;
  private String message;
  private long timestamp = System.currentTimeMillis();
  
  public String get(String paramString)
  {
    return (String)this.data.get(paramString);
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public HashMap<String, String> getData()
  {
    return this.data;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public long getTimestamp()
  {
    return this.timestamp;
  }
  
  public void put(String paramString1, String paramString2)
  {
    this.data.put(paramString1, paramString2);
  }
  
  public void remove(String paramString)
  {
    this.data.remove(paramString);
  }
  
  public void setCode(String paramString)
  {
    this.code = paramString;
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public void setTimestamp(long paramLong)
  {
    this.timestamp = paramLong;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    localStringBuffer.append("<reply>");
    localStringBuffer.append("<key>").append(getKey()).append("</key>");
    localStringBuffer.append("<timestamp>").append(this.timestamp).append("</timestamp>");
    localStringBuffer.append("<code>").append(this.code).append("</code>");
    localStringBuffer.append("<data>");
    Iterator localIterator = getData().keySet().iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        localStringBuffer.append("</data>");
        localStringBuffer.append("</reply>");
        return localStringBuffer.toString();
      }
      String str = (String)localIterator.next();
      localStringBuffer.append("<" + str + ">").append(get(str)).append("</" + str + ">");
    }
  }
  
  public String toXmlString()
  {
    return toString();
  }
}


/* Location:              F:\download\classes-dex2jar.jar!\com\farsunset\cim\client\model\ReplyBody.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */