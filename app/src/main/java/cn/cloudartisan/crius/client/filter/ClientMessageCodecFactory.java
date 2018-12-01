package cn.cloudartisan.crius.client.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class ClientMessageCodecFactory
  implements ProtocolCodecFactory
{
  private final ClientMessageDecoder decoder = new ClientMessageDecoder();
  private final ClientMessageEncoder encoder = new ClientMessageEncoder();
  
  public ProtocolDecoder getDecoder(IoSession paramIoSession)
    throws Exception
  {
    return this.decoder;
  }
  
  public ProtocolEncoder getEncoder(IoSession paramIoSession)
    throws Exception
  {
    return this.encoder;
  }
}
