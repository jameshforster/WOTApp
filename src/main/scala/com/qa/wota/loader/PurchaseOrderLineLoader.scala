package com.qa.wota.loader

import java.sql.ResultSet
import com.qa.James.entities.PurchaseOrderLine

/**
 * @author jforster
 * Class to create SQL commands and load ResultSets into entities from a query for PurchaseOrderLines
 */
class PurchaseOrderLineLoader [T] {
  val sqlSelect:String = "SELECT purchaseorderline.*"
  val sqlFrom:String = " FROM purchaseorderline"
  val sqlUpdate:String = "UPDATE purchaseorderline"
  
  /**
   * Method to query purchase order lines based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid function PurchaseOrderLineLoader.createQueryPurchaseOrderLinesByOrderID: Creates query to search for purchase order lines with the same order ID
   * param t: Generic attribute defined by search term
   * returns: The produced Array of PurchaseOrder objects
   */
  def queryPurchaseOrderLines(f: T => String, t:T): Array[PurchaseOrderLine] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createPurchaseOrderLineEntities(rs, null)
  }
  
  /**
   * Method to update purchase order lines based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL update based on it
   * Valid function PurchaseOrderLineLoader.createUpdatePurchaseOrderLinesDamagedStock: Creates update to add damaged stock to purchase order
   */
  def updatePurchaseOrderLine(f: PurchaseOrderLine => String, pOL:PurchaseOrderLine) {
    JDBCConnector.executeSQL(JDBCConnector.updateSQL, f(pOL))
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of PurchaseOrderLines already created
   * returns: Array of PurchaseOrderLines constructed from ResultSet
   */
  def createPurchaseOrderLineEntities(rs:ResultSet, list:Array[PurchaseOrderLine]): Array[PurchaseOrderLine] = {
     val iL = new ItemLoader[Int]
      if (rs.next()) {
        var item = iL.queryItem("ItemID", rs.getInt("idItem")).head
        var purchaseOrderLine = new PurchaseOrderLine(rs.getInt("idPurchaseOrder"), item, rs.getInt("quantity"), rs.getInt("quantityDamaged"))
        if (list != null) {
          createPurchaseOrderLineEntities(rs, list:+(purchaseOrderLine))
        }
        else {
          var tempList = new Array[PurchaseOrderLine](1)
          tempList(0) = purchaseOrderLine
          createPurchaseOrderLineEntities(rs, tempList)
        }
      }
      else {
        rs.close()
        JDBCConnector.closeConnectionSQL()
        list
      }
  }
  
  /**
   * Method to create the SQL command to query customer order lines with the input ID
   * return: String containing the sql command to query
   */
  def createQueryPurchaseOrderLinesByOrderID(id:T): String = {
    sqlSelect + sqlFrom + " WHERE purchaseorderline.idPurchaseOrder = " + id
  }
  
  /**
   * Method to create the SQL command to update a customer order line with the new damaged quantity
   * return: String containing the sql command to update
   */
  def createUpdatePurchaseOrderLinesDamagedStock(pOL:PurchaseOrderLine): String = {
    sqlUpdate + " SET quantityDamaged = " + pOL.damagedQuantity + " WHERE idPurchaseOrder = " + pOL.idPurchaseOrder + " AND idItem = " + pOL.item.idItem
  }
}