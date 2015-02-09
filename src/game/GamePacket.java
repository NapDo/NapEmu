package game;

import org.apache.mina.core.session.IoSession;

public enum GamePacket
{
  HELLO_GAME("HG"),  
  ACCOUNT_ATTACH_ERROR("ATE"),  
  ACCOUNT_ATTACH_OK("ATK");
  
  private String packet;
  private Object param;
  
  private GamePacket(String packet)
  {
    this.packet = packet;
    this.param = "";
  }
  
  private GamePacket(String packet, Object param)
  {
    this.packet = packet;
    

    this.param = param;
  }
  
  public void send(IoSession session, Object param)
  {
    if ((session == null) || (session.isClosing())) {
      return;
    }
    session.write(this.packet + String.valueOf(param));
  }
  
  public void send(IoSession session)
  {
    send(session, this.param);
  }
}



