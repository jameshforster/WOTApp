package com.qa.James.loader

import com.qa.James.entities.CustomerOrderLine
import java.sql.ResultSet
import com.qa.James.entities.Item

/**
 * @author jforster
 */
class CustomerOrderLineLoader [T]{
  val sqlSelect:String = "SELECT customerorderline.*"
  val sqlFrom:String = " FROM customerorderline"
  
  /**
   * Method to query customer order lines based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * param t: Generic attribute defined by search term
   * returns: The produced Array of CustomerOrder objects
   */
  def queryCustomerOrderLines(f: T => String, t:T): Array[CustomerOrderLine] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    
    null
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of CustomerOrderLines already created
   * returns: Array of CustomerOrderLines constructed from ResultSet
   */
  def createCustomerOrderLineEntities(rs:ResultSet, list:Array[CustomerOrderLine]): Array[CustomerOrderLine] = {
    if (rs.next()) {
      var item = new Item(1, "test", 10, "test", 20, 15, false, 0, 0, false)
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
}