/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.objects;

import org.apache.mina.core.session.IoSession;


/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
public class Player {

    private final Characters _character;
    private byte classID;
    private final byte sexe;
    private int id;
   private IoSession session = null;
    private String chanels = "*#$:?i^!%";
    private Account _account;
    public String restriction = "6bk";
    public Byte orientation = 2;

    protected int level;
    protected int gfxID;
 
    protected String[] colors = new String[3];
    protected String name;
  
    public Player(Characters c) {
        _character = c;
        gfxID = c.gfxid;
        level = c.level;
        name = c.name;
        classID = c.classId;
        sexe = c.sexe;
        id = c.id;
        orientation = c.orientation;

        colors[0] = c.color1 == -1 ? "-1" : Integer.toHexString(c.color1);
        colors[1] = c.color2 == -1 ? "-1" : Integer.toHexString(c.color2);
        colors[2] = c.color3 == -1 ? "-1" : Integer.toHexString(c.color3);

    }
      public Integer getLevel() {
        return level;
    }

    public Integer getGfxID() {
        return gfxID;
    }
        public Integer getID() {
        return id;
    }
 public Byte getClassID() {
        return classID;
    }

    public Byte getSexe() {
        return sexe;
    }
      public String getName() {
        return name;
    }
        public String[] getColors() {
        return colors;
    }



public void logout() {
        _character.logout();
    }

    /**
     * Retourne ne nombre total de pods
     *
     * @return
     */
  

    public int getUsedPods() {
        int pods = 0;
        /*for (GameItem GI : inventory.values()) {
            pods += GI.getPods();
        }*/
        return pods;
    }
    


    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public Account getAccount() {
        return _account;
    }
}

    /**
     * Charge les stats du perso
     */
   

    /**
     * Charge les stats du stuff
     */
 
    

