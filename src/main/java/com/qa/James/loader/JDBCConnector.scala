package com.qa.James.loader

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.ResultSet
import java.sql.Statement

/**
 * Object to maintain connection to the database and execute queries
 * @author jforster
 */
object JDBCConnector {
  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/mydb"
  val username = "root"
  val password = "root"
  
  var connection:Connection = null
  
  /**
   * Method to open a connection to the database
   * return: Connection to the database
   */
  def makeConnectionSQL():Connection = {
    try{
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
     
    }
    catch {
      case sqle:SQLException => 
        sqle.printStackTrace()
        connection = null
      case e:Exception =>
        e.printStackTrace()
        connection = null
    }
     connection
  }
  
  /**
   * Method to close the connection to the database
   * param: Connection to close
   */
  def closeConnectionSQL(conn:Connection):Unit = {
   try {
    connection = conn
    connection.close()
   }
   catch{
     case sqle:SQLException => sqle.printStackTrace()
   }
  }
  
  /**
   * Method to create a statement and execute any sql command 
   * param f: function that executes the sql in the correct way (query vs update)
   * param sql: string containing the command to run in sql
   * return: ResultSet produced from query, or null if one is not produced
   */
  def executeSQL(f:(String, Statement) => ResultSet, sql:String):ResultSet = {
    var rs:ResultSet = null
    try{
      val statement = makeConnectionSQL.createStatement()
      rs = f(sql, statement)
      }
    catch{
      case sqle:SQLException => sqle.printStackTrace()
    }
    rs
  }
  
  /**
   * Method to execute sql command as a query and produce a ResultSet
   * param sql: string containing the command to run in sql
   * param statement: sql Statement to execute the query
   * return: the ResultSet produced by the query or null if an error occurs
   */
  def querySQL(sql:String, statement:Statement):ResultSet = {
    try{
      statement.executeQuery(sql)
    }
    catch{
      case sqle:SQLException => null
    }
  }
  
  /**
   * Method to execute sql command as an update
   * param sql: string containing the command to run in sql
   * param statement: sql Statement to execute the update
   * return: a null ResultSet
   */
  def updateSQL(sql:String, statement:Statement):ResultSet = {
    try{
      statement.executeUpdate(sql)
      null
    }
    catch{
      case sqle:SQLException => sqle.printStackTrace()
        null
    }
  }
}