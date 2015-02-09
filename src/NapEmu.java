/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bonjour
 */

import common.Database;
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
  

     
    RealmServer.start();
    GameServer.start();
    Database.connect();
  }
}

