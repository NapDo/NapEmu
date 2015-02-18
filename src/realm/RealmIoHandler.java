package realm;

import Network.MinaIoHandler;
import base.objects.Account;
import base.objects.DatabaseObjects.DatabaseAccount;
import game.Events.Ae;
import game.Events.Ce;
import java.util.Random;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class RealmIoHandler extends MinaIoHandler
{
    private static final Random _rand = new Random();
    
  
  
  @Override
  public void messageReceived(IoSession session, Object message) throws Exception
  
  {
    super.messageReceived(session, message);
    String packet = ((String)message).trim();
    if (packet.length() == 0) {
      return;
    }
    if (packet.length() > 0)
    {
      System.out.println("Recv << " + packet);
      int numPacket = session.containsAttribute(1) ? ((Integer)session.getAttribute(Integer.valueOf(1))) : 1;
      switch (numPacket)
      {
      case 1: 
        if (!packet.equals("1.29.1"))
        {
          RealmPacketEnum.REQUIRE_VERSION.send(session);
          session.close(true);
        }
        else
        {
          session.setAttribute("good_version");
          session.setAttribute(1, numPacket + 1);
        }
        break;
      case 2: 
        String[] data = packet.split("\n");
        if (data.length == 2)
        {
          String username = data[0].trim();
          String pass = data[1].trim();
          Account acc = DatabaseAccount.account().getByName(username);
          if ((acc == null) || (!acc.passValid(pass, (String)session.getAttribute("HC"))))
          {
            RealmPacketEnum.LOGIN_ERROR.send(session);
            session.close(true);
            return;
          }
          if (acc.getSession() == null)
          {
            acc.setSession(session);
            RealmPacketEnum.COMMUNITY.send(session);
            RealmPacketEnum.PSEUDO.send(session, acc.pseudo);
            RealmPacketEnum.HOSTS_LIST.send(session);
            RealmPacketEnum.QUESTION.send(session, acc.question);
            RealmPacketEnum.LOGIN_OK.send(session, acc.level > 0 ? "1" : "0");
          }
          else
          {
            RealmPacketEnum.LOGIN_ALREADY_CONNECTED.send(session);
            session.close(false);
          }
          session.setAttribute(1, numPacket + 1);
        }
        break;
      }
        switch (packet.charAt(0)) {
           
                        case 'A':
                            switch (packet.charAt(1)) {
                                case 'x':
                                    RealmPacketEnum.SERVER_LIST.send(session);
                                    break;
                                 case 'X':
                                    Ae.ListServers(session, packet.substring(2));
                                              break;
                                        case 'L':
                                   
                                            Ae.ListPersos(session);
                                            break;
                                              case 'S':
                                 
                                        Ce.onCharacterSelected(session, packet.substring(2));
                              
                          
    }
             break;           
    }
          
    
  }
  }
  
  @Override
  public void exceptionCaught(IoSession session, Throwable cause)
    throws Exception
  {
    super.exceptionCaught(session, cause);
  }
  
  @Override
  public void sessionIdle(IoSession session, IdleStatus status)
    throws Exception
  {
    super.sessionIdle(session, status);
  }
  
    @Override
  public void sessionClosed(IoSession session)
    throws Exception
  {
    super.sessionClosed(session);
    Account acc = (Account)session.getAttribute("account");
     if(acc != null){
                acc.removeSession();
            }
    System.out.println("Fermeture d'une connexion ");
  }
  
    @Override
  public void sessionCreated(IoSession session) throws Exception {
        String HC = str_aleat(32);
        session.setAttribute("HC", HC);
        RealmPacketEnum.HELLO_CONNECTION.send(session, HC);
    }
  public static String str_aleat(int size)
  {
     
    StringBuilder b = new StringBuilder(size);
    
    char[] chrs = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    for (int i = 0; i < size; i++) {
      b.append(chrs[_rand.nextInt(chrs.length)]);
    }
    return b.toString();
  }
}


