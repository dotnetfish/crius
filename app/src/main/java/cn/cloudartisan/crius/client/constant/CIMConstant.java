package cn.cloudartisan.crius.client.constant;

public abstract interface CIMConstant
{
  public static final int CIM_DEFAULT_MESSAGE_ORDER = 1;
  public static final String CMD_HEARTBEAT_REQUEST = "cmd_server_hb_request";
  public static final String CMD_HEARTBEAT_RESPONSE = "cmd_client_hb_response";
  public static byte  MESSAGE_SEPARATE='\b';
  public static final String UTF8 = "UTF-8";
  
  public static class MessageType
  {
    public static String TYPE_999 = "999";
  }
  
  public static class RequestKey
  {
    public static String CLIENT_BIND = "client_bind";
    public static String CLIENT_LOGOUT = "client_logout";
    public static String CLIENT_OFFLINE_MESSAGE = "client_get_offline_message";
  }
  
  public static class ReturnCode
  {
    public static String CODE_200 = "200";
    public static String CODE_206 = "206";
    public static String CODE_403;
    public static String CODE_404 = "404";
    public static String CODE_405;
    public static String CODE_500 = "500";
    
    static
    {
      CODE_403 = "403";
      CODE_405 = "405";
    }
  }
}
