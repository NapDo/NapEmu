 package Network;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
 
public class MinaIoHandler
  extends IoHandlerAdapter
{
  @Override
  public void messageSent(IoSession session, Object message)
    throws Exception
{
   super.messageSent(session, message);
  System.out.println("Send >> " + message);
}

  @Override
  public void messageReceived(IoSession session, Object message)
   throws Exception
 {
    super.messageReceived(session, message);
    System.out.println("Recv << " + message);
   }
 }


