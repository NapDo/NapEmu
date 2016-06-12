
package common;

import base.objects.Characters;



/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
public class CharactGFX {
       //classes

   enum Data {
        CLASS_FECA(1),
       CLASS_OSAMODAS(2 ),
        CLASS_ENUTROF(3),
        CLASS_SRAM (4),
        CLASS_XELOR(5),
       CLASS_ECAFLIP(6),
        CLASS_ENIRIPSA(7),
        CLASS_IOP(8),
        CLASS_CRA(9),
       CLASS_SADIDA(10),
         CLASS_SACRIEUR(11),
         CLASS_PANDAWA(12), 
        SEX_MALE(0),
            SEX_FEMALE(1);
            
           

          private final byte id;

        Data(int id) {
            this.id = (byte) id;
        }
   }
        public static int getCharacterGfxID(byte classID, byte sexe) {
        return classID * 10 + sexe;
    }


    public static int getCharacterGfxID(Characters c) {
        return c.classId * 10 + c.sexe;
    }
}
