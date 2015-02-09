/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.Events;

import base.objects.DatabaseObjects.DatabaseCharacters;
import base.objects.Account;
import base.objects.Characters;
import game.GamePacket;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Bonjour
 */
public class Ce {
    public static void onCharacterAdd(IoSession session, String packet) {

        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }

        String[] arr_data = packet.split("\\|");

        try {
           
         

            Characters c;
            c = new Characters();
            c.accountId = acc.id;
            c.name = arr_data[0];
            c.classId = Byte.parseByte(arr_data[1]);
            c.sexe = Byte.parseByte(arr_data[2]);
            c.color1 = Integer.parseInt(arr_data[3]);
            c.color2 = Integer.parseInt(arr_data[4]);
            c.color3 = Integer.parseInt(arr_data[5]);
            

            if (!DatabaseCharacters.character().create(c)) {
                GamePacket.CREATE_CHARACTER_ERROR.send(session);
                return;
            }
            acc.addCharacter(c);
        } catch (ArrayIndexOutOfBoundsException e) {
            GamePacket.CREATE_CHARACTER_ERROR.send(session);
            return;
        }

        GamePacket.CREATE_CHARACTER_OK.send(session);
        GamePacket.CHARCTERS_LIST.send(session, acc.getPersoL());
        GamePacket.TUTORIAL_START.send(session);
     
    }

}
