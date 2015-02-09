package Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer
{
  private static final String OUT_EOP = "";
  private static final String IN_EOP = "";
  private static final int BUFFER = 512;
  public static final int DECO_TIME = 900;
  public static final String DOFUS_VER = "1.29.1";
  protected final NioSocketAcceptor acceptor = new NioSocketAcceptor();
  


    protected NioSocketAcceptor _acceptor = new NioSocketAcceptor();

    public MinaServer(int port, MinaIoHandler handler) throws IOException {
        _acceptor.getSessionConfig().setReadBufferSize(256);
        _acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 900);
        _acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(
                Charset.forName("UTF-8"),
                "\000",
                "\n\000")));
        _acceptor.setHandler(handler);
        _acceptor.bind(new InetSocketAddress(port));
    }

    public NioSocketAcceptor getAcceptor() {
        return _acceptor;
    }

    public void stop() {
        try {
            _acceptor.getManagedSessions().values().stream().filter((session) -> !(session == null || session.isClosing())).forEach((session) -> {
                session.close(true);
            }); 
            
        } catch (Exception e) {
        }
    }
}

