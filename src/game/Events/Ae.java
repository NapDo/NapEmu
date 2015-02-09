package game.Events;

import base.Account;
import game.GamePacket;
import game.GameServer;
import java.net.InetSocketAddress;
import org.apache.mina.core.session.IoSession;
import realm.RealmPacketEnum;

public class Ae
{
  public static void onAttach(IoSession session, String ticket)
  {
    Account acc = Account.getWaitingAccount(ticket);
    if (acc == null)
    {
      GamePacket.ACCOUNT_ATTACH_ERROR.send(session);
      session.close(true);
      return;
    }
    InetSocketAddress ISA = (InetSocketAddress)session.getRemoteAddress();
    acc.setSession(session);
    
  
  }
  
  public static void onServerSelected(IoSession session, String data)
  {
      String ip = "127.0.0.1";
    Account acc = (Account)session.getAttribute("account");
    if (acc == null)
    {
      session.close(true);
      return;
    }
    String p = ip + ";" + GameServer.port; 
    RealmPacketEnum.SELECT_SERVER.send(session, p);
  }
}

