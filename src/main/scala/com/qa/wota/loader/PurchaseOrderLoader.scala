package com.qa.wota.loader

import com.qa.wota.entities.PurchaseOrder
import java.sql.ResultSet
import com.qa.wota.entities.User
import com.qa.wota.entities.Employee
import com.qa.wota.entities.Role
import com.qa.wota.entities.PurchaseOrderStatus
import com.qa.wota.entities.Supplier

/**
 * @author jforster
 * Class to manage loading of purchase orders from database and create relevant entities
 */
class PurchaseOrderLoader [T] {
  val sqlSelect:String = "SELECT purchaseorder.*, purchaseorderstatus.*, employee.*, role.*, supplier.*, user.*"
  val sqlFrom:String = " FROM purchaseorder"
  val sqlJoins:String = " LEFT JOIN purchaseorderstatus ON purchaseorder.idPurchaseOrderStatus = purchaseorderstatus.idPurchaseOrderStatus LEFT JOIN employee ON purchaseorder.idEmployee = employee.idEmployee LEFT JOIN role ON role.idRole = employee.role_idRole LEFT JOIN user ON user.idUser = employee.idEmployee LEFT JOIN supplier ON supplier.idSupplier = purchaseorder.idSupplier"
  val sqlUpdate:String = "UPDATE purchaseorder"
  
  /**
   * Method to query customer orders based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid function PurchaseOrderLoader.createQueryAllPurchaseOrders: Creates query to search for all purchase orders (T:Unit)
   * Valid function PurchaseOrderLoader.createQueryPurchaseOrderByID: Creates query to search for purchase orders with the same ID (T:Int)
   * Valid function PurchaseOrderLoader.createQueryPurchaseOrderByEmployeeID: Creates query to search for purchase orders with the same employee ID (T:Int)
   * Valid function PurchaseOrderLoader.createQueryPurchaseOrderByStatus: Creates query to search for purchase orders with a matching status(T:String)
   * param t: Generic attribute defined by search term
   * returns: The produced Array of PurchaseOrder objects
   */
  def queryPurchaseOrders(f: T => String, t:T): Array[PurchaseOrder] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    createPurchaseOrderEntities(rs, null)
  }
  
  /**
   * Method to update purchase orders based on input function
   * param f:(PurchaseOrder) => String: Function that takes an input purchase order and creates an SQL update from it
   * Valid function PurchaseOrderLoader.updatePurchaseOrderByStatus: Creates update based on PurchaseOrderStatus and Employee of PurchaseOrder
   * param pO: PurchaseOrder to be updated in the database
   */
  def updatePurchaseOrders(f: PurchaseOrder => String, pO:PurchaseOrder) {
    JDBCConnector.executeSQL(JDBCConnector.updateSQL, f(pO))
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of PurchaseOrders already created
   * returns: Array of PurchaseOrders constructed from ResultSet
   */
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
  
  /**
   * Method to create the sql command to query all purchase orders
   * return: String containing the sql command to query
   */
  def createQueryAllPurchaseOrders(i:T): String ={
    sqlSelect + sqlFrom + sqlJoins
  }
  
  /**
   * Method to create the SQL command to query purchase orders with the input ID
   * return: String containing the sql command to query
   */
  def createQueryPurchaseOrderByID(i:T): String = {
    sqlSelect + sqlFrom + sqlJoins + " WHERE purchaseorder.idPurchaseOrder = " + i
  }
  
  /**
   * Method to create the SQL command to query purchase orders with the input employee ID
   * return: String containing the sql command to query
   */
  def createQueryPurchaseOrderByEmployeeID(i:T): String = {
    sqlSelect + sqlFrom + sqlJoins + " WHERE purchaseorder.idEmployee = " + i
  }
  
  /**
   * Method to create the SQL command to query purchase orders containing a status similar to the inputed string
   * return: String containing the sql command to query
   */
  def createQueryPurchaseOrderByStatus(i:T): String = {
    sqlSelect + sqlFrom + sqlJoins + " WHERE purchaseorderstatus.status LIKE '%" + i + "%'"
  }
  
  /**
   * Method to create the SQL command to update purchase order's status and employee
   * return: String containing the sql command to update
   */
  def updatePurchaseOrderByStatus(pO:PurchaseOrder):String = {
    sqlUpdate + " SET idPurchaseOrderStatus = " + pO.purchaseOrderStatus.idPurchaseOrderStatus + ", idEmployee = " + pO.employee.user.idUser + " WHERE idPurchaseOrder = " + pO.idPurchaseOrder
  }
  
}