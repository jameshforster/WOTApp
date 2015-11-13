package com.qa.James.loader

import org.scalatest.FlatSpec
import java.sql.SQLException
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException

/**
 * @author jforster
 */
class JDBCConnectorTest extends FlatSpec{
  makeConnectionSQLTest
  def makeConnectionSQLTest {
    "A Connection " should "not be null" in{
      assert(JDBCConnector.makeConnectionSQL() != null)
    }
    "A Connection " should "be open" in {
      assert(JDBCConnector.connection.isValid(100))
    }
  }
  
  closeConnectionSQLTest
  def closeConnectionSQLTest {
    "A Connection " should "be closed" in {
      JDBCConnector.makeConnectionSQL()
      JDBCConnector.closeConnectionSQL()
      assert(JDBCConnector.connection.isClosed())
    }
  }
  
  querySQLTest
  def querySQLTest {
    "A ResultSet " should " not be null" in {
      JDBCConnector.makeConnectionSQL()
      assert(JDBCConnector.querySQL("SELECT * FROM customerorder", JDBCConnector.connection.createStatement()) != null)
    }
    "An Empty ResultSet " should "not be null" in {
      JDBCConnector.makeConnectionSQL()
      assert(JDBCConnector.querySQL("SELECT * FROM customerorder WHERE idcustomerorder = 0", JDBCConnector.connection.createStatement()) != null)
    }
    "A ResultSet from an invalid query " should "be null" in {
      JDBCConnector.makeConnectionSQL()
       assert(JDBCConnector.querySQL("SELECT * FROM customrorder WHERE idcustomerorder = 0", JDBCConnector.connection.createStatement()) == null)
    }
  }
  
  //TODO add update SQL test
  
  executeSQLTest
  def executeSQLTest {
    "A ResultSet " should " not be null again" in {
      assert(JDBCConnector.executeSQL(JDBCConnector.querySQL, "SELECT * FROM customerorder") != null)
    }
    
    "An Empty ResultSet" should "not be null again" in {
      assert(JDBCConnector.executeSQL(JDBCConnector.querySQL, "SELECT * FROM customerorder WHERE idcustomerorder = 0") != null)
    }
    
    "A ResultSet from an invalid query " should "be nullagain" in {
      assert(JDBCConnector.executeSQL(JDBCConnector.querySQL, "SELECT * FROM customarorder WHERE idcustomerorder = 1") == null)
    }
  }
}