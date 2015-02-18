/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import base.objects.Characters;

/**
 *
 * @author Bonjour
 */
public class CharactGFX {
       //classes
    public static final byte CLASS_FECA = 1;
    public static final byte CLASS_OSAMODAS = 2;
    public static final byte CLASS_ENUTROF = 3;
    public static final byte CLASS_SRAM = 4;
    public static final byte CLASS_XELOR = 5;
    public static final byte CLASS_ECAFLIP = 6;
    public static final byte CLASS_ENIRIPSA = 7;
    public static final byte CLASS_IOP = 8;
    public static final byte CLASS_CRA = 9;
    public static final byte CLASS_SADIDA = 10;
    public static final byte CLASS_SACRIEUR = 11;
    public static final byte CLASS_PANDAWA = 12;
    public static final byte SEX_MALE = 0;
    public static final byte SEX_FEMALE = 1;
        public static int getCharacterGfxID(byte classID, byte sexe) {
        return classID * 10 + sexe;
    }


    public static int getCharacterGfxID(Characters c) {
        return c.classId * 10 + c.sexe;
    }
}
