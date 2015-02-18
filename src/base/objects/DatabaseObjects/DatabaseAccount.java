package base.objects.DatabaseObjects;

/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
import base.objects.Account;
import common.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseAccount
{
  private static DatabaseAccount account = null;
  
  public static DatabaseAccount account()
  {
    if (account == null) {
      account = new DatabaseAccount();
    }
    return account;
  }

    public static Object Account() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
  private final Map<String, Account> accName = new ConcurrentHashMap();
  private final Map<Integer, Account> accID = new ConcurrentHashMap();
  private PreparedStatement getByNameStatement = null;
  
  public DatabaseAccount()
  {
    this.getByNameStatement = Database.prepare("SELECT * FROM accounts WHERE account = ?");
  }
  
  protected String tableName()
  {
    return "accounts";
  }
  
  protected Account createByResultSet(ResultSet RS)
  {
    try
    {
      Account a = new Account();
      
      a.id = RS.getInt("id");
      a.account = RS.getString("account");
      a.level = RS.getByte("level");
      a.pass = RS.getString("pass");
      a.pseudo = RS.getString("pseudo");
      a.question = RS.getString("question");
      a.response = RS.getString("response");
      
      this.accName.put(RS.getString("account").toLowerCase(), a);
      this.accID.put(RS.getInt("id"), a);
      
      return a;
    }
    catch (SQLException e)
    {
      System.out.println("Impossible de charger le compte" + e);
    }
    return null;
  }

  
    public Account getByName(String name) {
        if (!accName.containsKey(name.toLowerCase())) {
            try {
                synchronized (getByNameStatement) {
                    getByNameStatement.setString(1, name);
                    ResultSet RS = getByNameStatement.executeQuery();
                    if (RS.next()) {
                        return createByResultSet(RS);
                    } else {
                        return null;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Impossible de charge le compte " + name+ ex);
                return null;
            }
        }
        return accName.get(name);
    }


    public Account getById(int id) {
     
        return accID.get(id);
    }
}


