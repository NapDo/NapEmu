
package game.Events;

import base.objects.DatabaseObjects.DatabaseCharacters;
import base.objects.Account;
import base.objects.Characters;
import base.objects.Player;
import common.CharactGFX;
import game.GamePacket;
import org.apache.mina.core.session.IoSession;


/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
public class Ce {
    public static void onCharacterAdd(IoSession session, String packet) {

        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }

        String[] arr_data;
        arr_data = packet.split("\\|");

        try {
           
           

            Characters c;
            c = new Characters();
            c.accID = acc.id;
            c.name = arr_data[0];
            c.classId = Byte.parseByte(arr_data[1]);
            c.sexe = Byte.parseByte(arr_data[2]);
            c.color1 = Integer.parseInt(arr_data[3]);
            c.color2 = Integer.parseInt(arr_data[4]);
            c.color3 = Integer.parseInt(arr_data[5]);
            c.gfxid = CharactGFX.getCharacterGfxID(c);
        
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
     public static String implode(String glue, Object[] pieces) {
        if (pieces == null || pieces.length < 1) {
            return "";
        }

        StringBuilder b = new StringBuilder();

        for (Object p : pieces) {
            b.append(p).append(glue);
        }

        String r = b.toString();

        return r.substring(0, r.length() - 1);
    }

        public static void onCharacterSelected(IoSession session, String packet) {

        try {
            Account acc = (Account) session.getAttribute("account");

            if (acc == null) {
                return;
            }

            int id, svr;
            svr = 0;
            
            try{
                String[] data = packet.split("\\|");
                
                id = Integer.parseInt(data[0]);
                    Characters chr = acc.getPerso(id);
            
                      if (chr == null) {
                GamePacket.SELECT_CHARACTER_ERROR.send(session);
                return;
            }
                if(data.length == 2){
                    svr = Integer.parseInt(data[1]);
                }
            }catch(Exception e){
               GamePacket.SELECT_CHARACTER_ERROR.send(session);
                return;
            }
            
            Characters chr = acc.getCharacter(id);

            if (chr == null) {
                GamePacket.SELECT_CHARACTER_ERROR.send(session);
                return;
            }

            Player p = chr.getPersos();

            
                session.setAttribute("player", p);
                chr.getPersos().setSession(session);

         

             
                StringBuilder param = new StringBuilder();

                param.append('|').append(chr.id).append("|").append(chr.name).append("|")
                        .append(chr.level).append("|").append(chr.classId).append("|")
                        .append(chr.sexe).append("|").append(chr.gfxid).append("|")
                        .append(implode("|", p.getColors())).append("|");

                

                GamePacket.SELECT_CHARACTER_OK.send(session, param.toString());
       
        } catch (Exception e) {
            System.out.println("Impossible de sélectionner le personnage"+ e);
            GamePacket.SELECT_CHARACTER_ERROR.send(session);
        }
         
}
}