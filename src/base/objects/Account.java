package base.objects;

import base.objects.DatabaseObjects.DatabaseCharacters;
import common.Crypt;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.mina.core.session.IoSession;

public class Account
{
  public int id;
  public String account;
  public String pass;
  public String pseudo;
  public byte level;
  public String question;
  public String response;
  private IoSession _session;
  private String current_ip;
  private static final ConcurrentHashMap<String, Account> pendingAccounts = new ConcurrentHashMap();
  protected HashMap<Integer, Characters> _characters = null;
   private Player waitingCharacter = null;
     

    public HashMap<Integer, Characters> getCharacters() {
        if (_characters == null) {
            _characters = DatabaseCharacters.character().getByAccountId(id);
        }
        return _characters;
    }
  public static Account getWaitingAccount(String ticket)
  {
    return (Account)pendingAccounts.get(ticket);
  }
  
  public IoSession getSession()
  {
    return this._session;
  }
  
  public void setSession(IoSession session)
  {
    this._session = session;
    session.setAttribute("account", this);
    InetSocketAddress ISA = (InetSocketAddress)session.getRemoteAddress();
    this.current_ip = ISA.getAddress().getHostAddress();
  }
  
  public void removeSession()
  {
    this._session = null;
  }
   public Player getWaitingCharacter(){
        Player p = waitingCharacter;
        waitingCharacter = null;
        return p;
    }
  

  
  public boolean passValid(String password, String Key)
  {
    String cryptPass = Crypt.encodePacket(this.pass, Key);
    return password.equals(cryptPass);
  }
    
     
   public void addCharacter(Characters c) {
        getCharacters().put(c.id, c);
    }

    public String getPersoL() {
              if (getCharacters().isEmpty()) {
            return "0";
        }
        StringBuilder packet = new StringBuilder();

        packet.append(getCharacters().size());
        getCharacters().values().stream().forEach((c) -> {
            packet.append(c.getForALK());
      });

        return packet.toString();
    }
}

   

   




    




