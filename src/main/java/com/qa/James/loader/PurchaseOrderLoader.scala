package com.qa.James.loader

import com.qa.James.entities.PurchaseOrder
import java.sql.ResultSet
import com.qa.James.entities.User
import com.qa.James.entities.Employee
import com.qa.James.entities.Role
import com.qa.James.entities.PurchaseOrderStatus
import com.qa.James.entities.Supplier

/**
 * @author jforster
 */
class PurchaseOrderLoader [T] {
  val sqlSelect:String = "SELECT purchaseorder.*, purchaseorderstatus.*, employee.*, role.*, supplier.*, user.*"
  val sqlFrom:String = " FROM purchaseorder"
  val sqlJoins:String = " LEFT JOIN purchaseorderstatus ON purchaseorder.idPurchaseOrderStatus = purchaseorderstatus.idPurchaseOrderStatus LEFT JOIN employee ON purchaseorder.idEmployee = employee.idEmployee LEFT JOIN role ON role.idRole = employee.role_idRole LEFT JOIN user ON user.idUser = employee.idEmployee LEFT JOIN supplier ON supplier.idSupplier = purchaseorder.idSupplier"

  def queryPurchaseOrders(f: T => String, t:T): Array[PurchaseOrder] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createPurchaseOrderEntities(rs, null)
  }
  
  def createPurchaseOrderEntities(rs:ResultSet, list:Array[PurchaseOrder]): Array[PurchaseOrder] = {
    if (rs.next()) {
      var user = new User(rs.getInt("user.idUser"), rs.getString("user.password"), rs.getString("user.forename"), rs.getString("user.surname"), rs.getString("user.email"), rs.getBoolean("user.isEmployee"))
      var role = new Role(rs.getInt("role.idRole"), rs.getString("Role"))
      var employee = new Employee(user, role)
      var purchaseOrderStatus = new PurchaseOrderStatus(rs.getInt("purchaseorderstatus.idPurchaseOrderStatus"), rs.getString("purchaseorderstatus.status"))
      val aLoader = new AddressLoader[Int]
      var address = aLoader.queryAddress("AddressID", rs.getInt("supplier.idAddress")).head
      var supplier = new Supplier(rs.getInt("supplier.idSupplier"), rs.getString("supplier.supplierName"), address, rs.getString("supplier.email"), rs.getString("supplier.telephoneNumber"), rs.getInt("supplier.deliveryTime"))
      val pOLLoader = new PurchaseOrderLineLoader[Int]
      var purchaseOrderLines = pOLLoader.queryPurchaseOrderLines(pOLLoader.createQueryPurchaseOrderLinesByOrderID, rs.getInt("purchaseorder.idPurchaseOrder"))
      var purchaseOrder:PurchaseOrder = new PurchaseOrder(rs.getInt("purchaseorder.idPurchaseOrder"), supplier, purchaseOrderStatus, purchaseOrderLines, employee, rs.getDate("purchaseorder.dateExpected"), rs.getDate("purchaseorder.datePlaced"))
      //if an Array exists, append to it, else create a new Array
      if (list != null) {
        createPurchaseOrderEntities(rs, list:+(purchaseOrder))
      }
      else {
        var tempList = new Array[PurchaseOrder](1)
        tempList(0) = purchaseOrder
        createPurchaseOrderEntities(rs, tempList)
      }
    }
    else {
      rs.close()
      JDBCConnector.closeConnectionSQL()
      list
    }
  }
  
  def createQueryAllPurchaseOrders(i:T): String ={
    sqlSelect + sqlFrom + sqlJoins
  }
  
  def createQueryPurchaseOrderByID(i:T): String = {
    sqlSelect + sqlFrom + sqlJoins + " WHERE purchaseorder.idPurchaseOrder = " + i
  }
  
}