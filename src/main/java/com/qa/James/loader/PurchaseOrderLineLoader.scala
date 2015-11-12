package com.qa.James.loader

import java.sql.ResultSet
import com.qa.James.entities.PurchaseOrderLine

/**
 * @author jforster
 */
class PurchaseOrderLineLoader [T] {
  val sqlSelect:String = "SELECT purchaseorderline.*"
  val sqlFrom:String = " FROM purchaseorderline"
  val sqlUpdate:String = "UPDATE purchaseorderline"
  
  def queryPurchaseOrderLines(f: T => String, t:T): Array[PurchaseOrderLine] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createPurchaseOrderLineEntities(rs, null)
  }
  
  def updatePurchaseOrderLine(f: PurchaseOrderLine => String, pOL:PurchaseOrderLine) {
    JDBCConnector.executeSQL(JDBCConnector.updateSQL, f(pOL))
  }
  
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
  
  def createQueryPurchaseOrderLinesByOrderID(id:T): String = {
    sqlSelect + sqlFrom + " WHERE purchaseorderline.idPurchaseOrder = " + id
  }
  
  def createUpdatePurchaseOrderLinesDamagedStock(pOL:PurchaseOrderLine): String = {
    sqlUpdate + " SET quantityDamaged = " + pOL.damagedQuantity + " WHERE idPurchaseOrder = " + pOL.idPurchaseOrder + " AND idItem = " + pOL.item.idItem
  }
}