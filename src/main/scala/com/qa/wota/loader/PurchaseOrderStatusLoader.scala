package com.qa.wota.loader

import com.qa.James.entities.PurchaseOrderStatus
import java.sql.ResultSet

/**
 * @author jforster
 * Class to manage loading of purchase order statuses from database and create relevant entities
 */
class PurchaseOrderStatusLoader [T] {
  val sqlSelect:String = "SELECT purchaseorderstatus.*"
  val sqlFrom:String = " FROM purchaseorderstatus"
 
  /**
   * Method to query PurchaseOrderStatuses based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid Function CustomerOrderStatusLaoder.createQueryCustomerOrderStatusByID
   * param t: Generic attribute defined by search term
   * returns: The produced Array of CustomerOrderStatus objects
   */
 def queryPurchaseOrderStatus(f: T => String, t:T): Array[PurchaseOrderStatus] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createPurchaseOrderStatusEntities(rs, null)
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of PurchaseOrderStatus already created
   * returns: Array of PurchaseOrderStatuses constructed from ResultSet
   */
  def createPurchaseOrderStatusEntities(rs:ResultSet, list:Array[PurchaseOrderStatus]): Array[PurchaseOrderStatus] = {
    if (rs.next()) {
      var purchaseOrderStatus = new PurchaseOrderStatus(rs.getInt("idPurchaseOrderStatus"), rs.getString("status"))
      //if an Array exists, append to it, else create a new Array
      if (list != null) {
        createPurchaseOrderStatusEntities(rs, list:+(purchaseOrderStatus))
      }
      else {
        var tempList = new Array[PurchaseOrderStatus](1)
        tempList(0) = purchaseOrderStatus
        createPurchaseOrderStatusEntities(rs, tempList)
      }
    }
    else {
      rs.close()
      JDBCConnector.closeConnectionSQL()
      list
    }
  }
  
  /**
   * Method to create the SQL command to query PurchaseOrderStatus with the input ID
   * return: String containing the sql command to query
   */
  def createQueryPurchaseOrderStatusByID (i:T):String = {
    sqlSelect + sqlFrom + " WHERE idPurchaseOrderStatus = " + i
  }
}