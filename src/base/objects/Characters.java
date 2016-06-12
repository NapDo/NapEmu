
package base.objects;



/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
public class Characters {
     public int id;
    public String name;
    public int accID;
    public int level = 1;
    public int color1;
    public int color2;
    public int color3;
    public int gfxid;
    public byte sexe;
    public byte classId;
    public short lastMap;
    public short lastCell;
    public short startMap;
    public short startCell;
    public String baseStats;
    public byte orientation = 0;
    private Player _player = null;

    public String getForALK() {
        StringBuilder perso = new StringBuilder();
        perso.append("|");
        perso.append(id).append(";");
        perso.append(name).append(";");
        perso.append(level).append(";");
        perso.append(gfxid).append(";");
        perso.append((color1 != -1 ? Integer.toHexString(color1) : "-1")).append(";");
        perso.append((color2 != -1 ? Integer.toHexString(color2) : "-1")).append(";");
        perso.append((color3 != -1 ? Integer.toHexString(color3) : "-1")).append(";");
        perso.append(0).append(";");
        perso.append("1;");//ServerID
        perso.append(";");//DeathCount	this.deathCount;
        perso.append(";");//LevelMax
        return perso.toString();
    }
   public Player getPersos() {
        if (_player == null) {
            _player = new Player(this);
        }
        return _player;
    }

 public void logout() {
        if (_player == null) {
        }
 }


    
}