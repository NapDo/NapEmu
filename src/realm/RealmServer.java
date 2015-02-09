package realm;

import Network.MinaServer;
import java.io.IOException;

public class RealmServer {
    
    
  protected MinaServer _server;
  protected static RealmServer instance = null ;
 
   protected static final int port = 449;

   private RealmServer()
  {
    try
    {

      System.out.println("Lancement du Realm : ");
      _server = new MinaServer(port, new RealmIoHandler());
      System.out.println("Ok");
      System.out.println("port"+ port+ "Accepté");
    }
    catch (IOException e)
    {
      System.out.println("Impossible de lancer le serveur de Realm port "+port);
      System.exit(1);
    }
  }
  
  public static void start()
  {
    if (instance != null) {
      System.out.println("Serveur de Realm déjà lancé !");
    }
    instance = new RealmServer();
  }
  
  public static void stop()
  {
    System.out.println("Arrêt du realm : ");
    instance._server.stop();
  }
}

