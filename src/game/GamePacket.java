package game;

import org.apache.mina.core.session.IoSession;

public enum GamePacket
{
  HELLO_GAME("HG"),  
  ACCOUNT_ATTACH_ERROR("ATE"),  
  ACCOUNT_ATTACH_OK("ATK"),
      /**
     * Génération du pseudo (création personnage)
     */
    CHARACTER_GENERATOR_NAME("APK"),
    /**
     * Nom du personnage existe déjà
     */
    NAME_ALEREADY_EXISTS("AAEa"),
    /**
     * Compte plein
     */
    CREATE_CHARACTER_FULL("AAEf"),
    /**
     * Erreur lors de la création du perso
     */
    CREATE_CHARACTER_ERROR("AAE"),
    /**
     * Personnage crée avec succès
     */
    CREATE_CHARACTER_OK("AAK"),
     
    TUTORIAL_START("TB"),
      SELECT_CHARACTER_ERROR("ASE"),
    /**
     * sélection du personnage Ok
     */
    SELECT_CHARACTER_OK("ASK"),
    
    
     
  CHARCTERS_LIST("ALK31536000000|");

  
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



