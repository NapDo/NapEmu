package common;




/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
import game.GameServer;

import realm.RealmServer;

public class NapEmu
{
  public static void main(String[] args)
  
  {
      System.out.println("███╗   ██╗ █████╗ ██████╗ ███████╗███╗   ███╗██╗   ██╗\n" +
"████╗  ██║██╔══██╗██╔══██╗██╔════╝████╗ ████║██║   ██║\n" +
"██╔██╗ ██║███████║██████╔╝█████╗  ██╔████╔██║██║   ██║\n" +
"██║╚██╗██║██╔══██║██╔═══╝ ██╔══╝  ██║╚██╔╝██║██║   ██║\n" +
"██║ ╚████║██║  ██║██║     ███████╗██║ ╚═╝ ██║╚██████╔╝\n" +
"╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚══════╝╚═╝     ╚═╝ ╚═════╝");
    
      System.out.println("Lancement de l'emu Nap'Emu ! ");
  

     
    
    Database.connect();
    RealmServer.start();
    GameServer.start();
  }
}

