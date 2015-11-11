package com.qa.James.loader

import com.qa.James.entities.PurchaseOrderStatus
import java.sql.ResultSet

/**
 * @author jforster
 */
class PurchaseOrderStatusLoader [T] {
  val sqlSelect:String = "SELECT purchaseorderstatus.*"
  val sqlFrom:String = " FROM purchaseorderstatus"
  
 def queryPurchaseOrderStatus(f: T => String, t:T): Array[PurchaseOrderStatus] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createPurchaseOrderStatusEntities(rs, null)
  }
  
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
  def createQueryPurchaseOrderStatusByID (i:T):String = {
    sqlSelect + sqlFrom + " WHERE idPurchaseOrderStatus = " + i
  }
}