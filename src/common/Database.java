package common;

/**
 *
 * @author Mohammed TABIT <nap.software@gmail.com>
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;

public class Database
{
  public Connection connect;
  private static Database self = null;
  private ScheduledExecutorService scheduledCommit;
 

  private Database()
  {
    try
    {
   
      System.out.println("Connecting database...");
      StringBuilder dsn = new StringBuilder();
    
    
      dsn.append("jdbc:mysql://");
      dsn.append("127.0.0.1");
      dsn.append("/").append("napemu");
       this.connect = DriverManager.getConnection(dsn.toString(), "root", "");
    
        System.out.println("Database connected!");
    }
    catch (SQLException ex)
    {
     System.out.println("Connexion impossible"+ ex);
      System.exit(1);
    }
  }
  
 
  public static ResultSet query(String query)
  {
    try
    {
      ResultSet RS;
      synchronized (self)
      {
        RS = self.connect.createStatement().executeQuery(query);
        System.out.println("Execution de la requête : %s" + Arrays.toString(new Object[] { query }));
      }
      return RS;
    }
    catch (SQLException e)
    {
      System.out.println("exécution de la requête '" + query + "' impossible !" + e);
    }
    return null;
  }
  
  public static PreparedStatement prepare(String query)
  {
    try
    {
      PreparedStatement stmt = self.connect.prepareStatement(query);
      System.out.println("Préparation de la requête : %s" + query);
      return stmt;
    }
    catch (SQLException e) {}
    return null;
  }
  
  public static PreparedStatement prepareInsert(String query)
  {
    try
    {
      return self.connect.prepareStatement(query, 1);
    }
    catch (SQLException e) {}
    return null;
  }
  
  public static void connect()
  {
    if (self == null)
    {
      self = new Database();
     
    }
  }
  
  public static void close()
  {
    try
    {
      System.out.println("Database shutdown ! ");
      self.scheduledCommit.shutdown();
      self.scheduledCommit = null;
      self.connect.close();
      System.out.println("ok");
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}

