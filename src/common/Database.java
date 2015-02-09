package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database
{
  public Connection db;
  private static Database self = null;
  private ScheduledExecutorService scheduledCommit;
  private boolean _autocommit = false;
  
  private Database()
  {
    try
    {
      System.out.println("Connexion à la base de données : ");
      StringBuilder dsn = new StringBuilder();
      
      dsn.append("jdbc:mysql://");
      dsn.append("127.0.0.1");
      dsn.append("/").append("test");
      
      this.db = DriverManager.getConnection(dsn.toString(), "", "");
      


      this.scheduledCommit = Executors.newSingleThreadScheduledExecutor();
      this.scheduledCommit.scheduleAtFixedRate(() -> {
          if (!Database.self._autocommit) {
              try
              {
                  synchronized (Database.self.db)
                  {
                      Database.self.db.commit();
                  }
                  System.out.println("Commit Database");
              }
              catch (SQLException ex) {}
          }
      }, 60L, 60L, TimeUnit.SECONDS);
      








      System.out.println("Ok");
    }
    catch (SQLException ex)
    {
      Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Connexion impossible", ex);
      System.exit(1);
    }
  }
  
  public static void setAutocommit(boolean state)
  {
    self._autocommit = state;
    try
    {
      synchronized (self.db)
      {
        if (state)
        {
          System.out.println("Commit Database");
          self.db.commit();
        }
        self.db.setAutoCommit(state);
      }
    }
    catch (SQLException ex)
    {
      System.out.println("Impossible de changer le mode autoCommit !" + ex);
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
        RS = self.db.createStatement().executeQuery(query);
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
      PreparedStatement stmt = self.db.prepareStatement(query);
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
      return self.db.prepareStatement(query, 1);
    }
    catch (SQLException e) {}
    return null;
  }
  
  public static void connect()
  {
    if (self == null)
    {
      self = new Database();
      setAutocommit(false);
    }
  }
  
  public static void close()
  {
    try
    {
      System.out.println("Arrêt de database : ");
      self.scheduledCommit.shutdown();
      self.scheduledCommit = null;
      self.db.close();
      System.out.println("ok");
    }
    catch (SQLException ex)
    {
      System.out.println(ex.getMessage());
    }
  }
}

