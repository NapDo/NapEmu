/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base.objects.DatabaseObjects;

import base.objects.Characters;
import common.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Bonjour
 */
public class DatabaseCharacters {
    private static DatabaseCharacters persos = null;
    private PreparedStatement findByNameStatement = null;
    private PreparedStatement getByAccountId = null;
    private PreparedStatement createStatement = null;
    private PreparedStatement countNameStatement = null;
    private PreparedStatement countByAccountStatement = null;
    private PreparedStatement updateStatement = null;
    private final ConcurrentHashMap<Integer, Characters> charactersById = new ConcurrentHashMap<>();

    public DatabaseCharacters() {
        createStatement = Database.prepareInsert("INSERT INTO characters(name, class, sexe, color1, color2, color3, account, gfxid, lastMap, lastCell, startMap, startCell) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);");
        updateStatement = Database.prepare("UPDATE characters SET level = ?, gfxid = ?, lastMap = ?, lastCell = ?, startMap = ?, startCell = ?, baseStats = ?, orientation = ? WHERE id = ?");
        findByNameStatement = Database.prepare("SELECT * FROM characters WHERE name = ?");
        getByAccountId = Database.prepare("SELECT * FROM characters WHERE account = ?");
        countNameStatement = Database.prepare("SELECT COUNT(*) AS count FROM characters WHERE name = ?");
        countByAccountStatement = Database.prepare("SELECT COUNT(*) AS count FROM characters WHERE account = ?");
    }
          
        protected Characters createByResultSet(ResultSet RS) {
        try {
            Characters p = new Characters();

            p.id = RS.getInt("id");
            p.name = RS.getString("name");
            p.accountId = RS.getInt("account");
            p.classId = RS.getByte("class");
            p.color1 = RS.getInt("color1");
            p.color2 = RS.getInt("color2");
            p.color3 = RS.getInt("color3");
            p.gfxid = RS.getInt("gfxid");
            p.sexe = RS.getByte("sexe");
            p.level = RS.getInt("level");
            p.lastMap = RS.getShort("lastMap");
            p.lastCell = RS.getShort("lastCell");
            p.baseStats = RS.getString("baseStats");
            p.orientation = RS.getByte("orientation");

            charactersById.put(p.id, p);

            return p;
        } catch (SQLException e) {
            System.out.println("Impossible de charger le personnage !"+ e);
            return null;
        }
    }
          public boolean create(Characters p) {
        try {
            synchronized (createStatement) {
                createStatement.setString(1, p.name);
                createStatement.setShort(2, p.classId);
                createStatement.setShort(3, p.sexe);
                createStatement.setInt(4, p.color1);
                createStatement.setInt(5, p.color2);
                createStatement.setInt(6, p.color3);
                createStatement.setInt(7, p.accountId);
                createStatement.setInt(8, p.gfxid);
                createStatement.setShort(9, p.lastMap);
                createStatement.setShort(10, p.lastCell);
                createStatement.setShort(11, p.startMap);
                createStatement.setShort(12, p.startCell);

                int id = createStatement.executeUpdate();

                ResultSet RS = createStatement.getGeneratedKeys();

                if (RS.next()) {
                    id = RS.getInt(1);
                }

                if (id == 0) {
                    return false;
                } else {
                    p.id = id;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Création du personnage impossible !"+ ex);
            return false;
        }

        return true;
    }


  public static DatabaseCharacters character()
  {
    if (persos == null) {
      persos = new DatabaseCharacters();
    }
    return persos;
  }
     public Characters getById(int id) {
       
        return charactersById.get(id);
    }
    public HashMap<Integer, Characters> getByAccountId(int accountId) {
        HashMap<Integer, Characters> players = new HashMap<>();

        try {
            synchronized (getByAccountId) {
                getByAccountId.setInt(1, accountId);
                ResultSet RS = getByAccountId.executeQuery();

                while (RS.next()) {
                    Characters c = createByResultSet(RS);
                    if (c != null) {
                        players.put(c.id, c);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Impossible de charger la liste des personnages du compte " + accountId + e);
        }

        return players;
    }
    

  
    
}
