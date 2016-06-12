package game;

/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
import Network.MinaServer;
import java.io.IOException;

public class GameServer
{
  protected MinaServer _server;
  protected static GameServer instance = null;
 
 public  static int port = 5556;
  
  private GameServer()
  {
    try
    {
      System.out.println("Lancement du Game : ");
      this._server = new MinaServer(port, new GameIoHandler()) {};
      System.out.println("Ok");
      System.out.println("port " + port + " accepté");
   
    }
    catch (IOException e)
    {
      System.out.println("Impossible de lancer le serveur de Game avec port "+port);
      System.exit(1);
    }
  }
  
  public static void start()
  {
    if (instance != null) {
      System.out.println("Serveur de Game déjà lancé !");
    }
    instance = new GameServer();
  }
  
  public static void stop()
  {
    System.out.println("Arrêt du Game : ");
    instance._server.stop();
   
    System.out.println("ok");
  }
}

