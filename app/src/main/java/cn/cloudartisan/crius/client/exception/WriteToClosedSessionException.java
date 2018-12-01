package cn.cloudartisan.crius.client.exception;

import java.io.Serializable;

public class WriteToClosedSessionException
  extends Exception
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  public WriteToClosedSessionException() {}
  
  public WriteToClosedSessionException(String paramString)
  {
    super(paramString);
  }
}
