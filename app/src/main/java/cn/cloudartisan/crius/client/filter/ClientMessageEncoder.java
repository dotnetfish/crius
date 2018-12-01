package  cn.cloudartisan.crius.client.filter;
import android.util.Log;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class ClientMessageEncoder extends ProtocolEncoderAdapter {
  static final String TAG = ClientMessageEncoder.class.getSimpleName();

  static {
  }

  public void encode(IoSession iosession, Object message, ProtocolEncoderOutput out) throws Exception {
    IoBuffer buff = IoBuffer.allocate(320).setAutoExpand(true);
    buff.put(message.toString().getBytes("UTF-8"));
    buff.put((byte)8);
    buff.flip();
    out.write(buff);
    Log.i(TAG, message.toString());
  }
}