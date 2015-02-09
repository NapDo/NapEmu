package realm;

import org.apache.mina.core.session.IoSession;

public enum RealmPacketEnum
{
  HELLO_CONNECTION("HC"),  
  REQUIRE_VERSION("AlEv", "1.29.1"), 
  LOGIN_ERROR("AlEf"),  
  ACCESS_DENIED("AlE"), 
  LOGIN_ALREADY_CONNECTED("AlEc"),  
  LOGIN_DISCONNECT("AlEd"),  
  PSEUDO("Ad"),  
  COMMUNITY("Ac", "0"), 
  HOSTS_LIST("AH", "1;1;110;1"), 
  LOGIN_OK("AlK"),  
  QUESTION("AQ"),  
  SELECT_SERVER("AYK"),  
  SELECT_SERVER_CRYPT("AXK"),  
  SERVER_LIST("AxK31536000000|1,2");
  
  private String packet;
  private String param;
  
  private RealmPacketEnum(String packet)
  {
    this.packet = packet;
    this.param = "";
  }
  
  private RealmPacketEnum(String packet, String param)
  {
    this.packet = packet;
    this.param = param;
  }
  
  public void send(IoSession session, String param)
  {
    if (session != null) {
      session.write(this.packet + param);
    }
  }
  
  public void send(IoSession session)
  {
    send(session, this.param);
  }
}
