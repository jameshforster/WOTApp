package com.qa.wota.loader

import com.qa.James.entities.CustomerOrderLine
import java.sql.ResultSet
import com.qa.James.entities.Item

/**
 * @author jforster
 * Class to create SQL commands and load ResultSets into entities from a query for CustomerOrderLines
 */
class CustomerOrderLineLoader [T]{
  val sqlSelect:String = "SELECT customerorderline.*"
  val sqlFrom:String = " FROM customerorderline"
  
  /**
   * Method to query customer order lines based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid function CustomerOrderLineLoader.createQueryCustomerOrderLinesByOrderID: Creates query to search for customer order lines with the same order ID
   * param t: Generic attribute defined by search term
   * returns: The produced Array of CustomerOrder objects
   */
  def queryCustomerOrderLines(f: T => String, t:T): Array[CustomerOrderLine] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createCustomerOrderLineEntities(rs, null)
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of CustomerOrderLines already created
   * returns: Array of CustomerOrderLines constructed from ResultSet
   */
  def createCustomerOrderLineEntities(rs:ResultSet, list:Array[CustomerOrderLine]): Array[CustomerOrderLine] = {
    val iL = new ItemLoader[Int]
    if (rs.next()) {
      var item = iL.queryItem("ItemID", rs.getInt("idItem")).head
      var customerOrderLine = new CustomerOrderLine(rs.getInt("idCustomerOrder"), item, rs.getInt("quantity"), rs.getInt("quantityPicked"))
      if (list != null) {
        createCustomerOrderLineEntities(rs, list:+(customerOrderLine))
      }
      else {
        var tempList = new Array[CustomerOrderLine](1)
        tempList(0) = customerOrderLine
        createCustomerOrderLineEntities(rs, tempList)
      }
    }
    else{
      rs.close()
      JDBCConnector.closeConnectionSQL()
      list
    }
  }
   /**
   * Method to create the SQL command to query customer order lines with the input ID
   * return: String containing the sql command to query
   */
  def createQueryCustomerOrderLinesByOrderID(id:T): String = {
    sqlSelect + sqlFrom + " WHERE customerorderline.idCustomerOrder = " + id
  }
}