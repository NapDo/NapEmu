package game.Events;

import base.objects.Account;
import base.objects.Player;
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
    Player p = acc.getWaitingCharacter();
            session.setAttribute("player", p);
            
            if(p == null){
                return;
            }

        
            p.setSession(session);

            //génération du packet ASK
            StringBuilder param = new StringBuilder();

            param.append('|').append(p.getID()).append("|").append(p.getName()).append("|")
                    .append(p.getLevel()).append("|").append(p.getClassID()).append("|")
                    .append(p.getSexe()).append("|").append(p.getGfxID()).append("|")
                    .append(implode("|", p.getColors())).append("|");

           
            
            GamePacket.ACCOUNT_ATTACH_OK.send(session, param);
        

    
  
  }
      public static void ListPersos(IoSession session) {
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }
        GamePacket.CHARCTERS_LIST.send(session, acc.getPersoL());
        
    }
      
  public static void ListServers(IoSession session, String data)
  {
    String ip = "127.0.0.1";
    Account acc = (Account)session.getAttribute("account");
    if (acc == null)
    {
      session.close(true);
      return;
    }
    String pe = ip+ ";" +GameServer.port; 
    RealmPacketEnum.SELECT_SERVER.send(session, pe);
     
  }
   public static String implode(String glue, Object[] pieces) {
        if (pieces == null || pieces.length < 1) {
            return "";
        }

        StringBuilder b = new StringBuilder();

        for (Object p : pieces) {
            b.append(p).append(glue);
        }

        String r = b.toString();

        return r.substring(0, r.length() - 1);
    }
}

