package cn.cloudartisan.crius.client.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class SentBody
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private HashMap<String, String> data = new HashMap();
  private String key;
  private long timestamp = System.currentTimeMillis();
  
  public String get(String paramString)
  {
    return (String)this.data.get(paramString);
  }
  
  public HashMap<String, String> getData()
  {
    return this.data;
  }
  
  public String getKey()
  {
    return this.key;
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
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
  
  public void setTimestamp(long paramLong)
  {
    this.timestamp = paramLong;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    localStringBuffer.append("<sent>");
    localStringBuffer.append("<key>").append(this.key).append("</key>");
    localStringBuffer.append("<timestamp>").append(this.timestamp).append("</timestamp>");
    localStringBuffer.append("<data>");
    Iterator localIterator = this.data.keySet().iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        localStringBuffer.append("</data>");
        localStringBuffer.append("</sent>");
        return localStringBuffer.toString();
      }
      String str = (String)localIterator.next();
      localStringBuffer.append("<" + str + ">").append((String)this.data.get(str)).append("</" + str + ">");
    }
  }
  
  public String toXmlString()
  {
    return toString();
  }
}
