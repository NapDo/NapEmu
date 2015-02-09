/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Scanner;

/**
 *
 * @author Bonjour
 */
public class Command {

    /**
     *
     */
    public static void exit (){
        Scanner sc = new Scanner(System.in);        
        String ex = sc.nextLine();
        System.exit(1);
      
    }
            
    
    
}
