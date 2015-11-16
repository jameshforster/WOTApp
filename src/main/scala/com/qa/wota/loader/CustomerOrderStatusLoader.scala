package com.qa.wota.loader

import com.qa.James.entities.CustomerOrderStatus
import java.sql.ResultSet

/**
 * @author jforster
 * Class to manage loading of customer order statuses from database and create relevant entities
 */
class CustomerOrderStatusLoader [T] {
  val sqlSelect:String = "SELECT customerorderstatus.*"
  val sqlFrom:String = " FROM customerorderstatus"
  
  /**
   * Method to query CustomerOrderStatuses based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid Function CustomerOrderStatusLaoder.createQueryCustomerOrderStatusByID
   * param t: Generic attribute defined by search term
   * returns: The produced Array of CustomerOrderStatus objects
   */
  def queryCustomerOrderStatus(f: T => String, t:T): Array[CustomerOrderStatus] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createCustomerOrderStatusEntities(rs, null)
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of CustomerOrderStatus already created
   * returns: Array of CustomerOrderStatuses constructed from ResultSet
   */
  def createCustomerOrderStatusEntities(rs:ResultSet, list:Array[CustomerOrderStatus]): Array[CustomerOrderStatus] = {
    if (rs.next()) {
      var customerOrderStatus = new CustomerOrderStatus(rs.getInt("idCustomerOrderStatus"), rs.getString("status"))
      //if an Array exists, append to it, else create a new Array
      if (list != null) {
        createCustomerOrderStatusEntities(rs, list:+(customerOrderStatus))
      }
      else {
        var tempList = new Array[CustomerOrderStatus](1)
        tempList(0) = customerOrderStatus
        createCustomerOrderStatusEntities(rs, tempList)
      }
    }
    else {
      rs.close()
      JDBCConnector.closeConnectionSQL()
      list
    }
  }
  
  /**
   * Method to create the SQL command to query CustomerOrderStatus with the input ID
   * return: String containing the sql command to query
   */
  def createQueryCustomerOrderStatusByID (i:T):String = {
    sqlSelect + sqlFrom + " WHERE idCustomerOrderStatus = " + i
  }
}