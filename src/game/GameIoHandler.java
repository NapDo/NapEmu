package game;

import Network.MinaIoHandler;
import game.Events.Ae;
import org.apache.mina.core.session.IoSession;

public class GameIoHandler extends MinaIoHandler
{
  public static int SENT = 0;
  public static int RECV = 0;
  public static int CON = 0;
  
  @Override
  public void sessionCreated(IoSession session)
    throws Exception
  {
    CON += 1;
    GamePacket.HELLO_GAME.send(session);
  }
  
  @Override
  public void messageReceived(IoSession session, Object message)
    throws Exception
  {
    RECV += 1;
    


    String packet = ((String)message).trim();
    if (packet.length() > 1)
    {
      System.out.println("Recv << " + packet);
      if ((!packet.startsWith("AT")) && (!session.containsAttribute("account")))
      {
        session.close(true);
        return;
      }
      switch (packet.charAt(0))
      {
      case 'A': 
        switch (packet.charAt(1))
        {
        case 'T': 
          Ae.onAttach(session, packet.substring(2));
        }
        break;
      case 'c': 
        switch (packet.charAt(1))
        {
        case 'C': 
          session.write(packet);
        }
        break;
      }
    }
  }
}

