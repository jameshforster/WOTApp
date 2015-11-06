package com.qa.James.loader

import com.qa.James.entities.CustomerOrder
import java.sql.ResultSet
import com.qa.James.entities.Role
import com.qa.James.entities.User

/**
 * @author jforster
 * Object to manage loading of customer orders from database and create relevant entities
 */
class customerOrderLoader[T] {
  val sqlSelect:String = "SELECT customerorder.*, customerorderline.*, customerorderstatus.*, employee.*, customer.*, user.*, role.*"
  val sqlFrom:String = " FROM customerorder"
  val sqlJoins:String = " LEFT JOIN customerorderline ON customerorder.idCustomerOrder = customerorderline.idCustomerOrder LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user ON employee.idEmployee = user.idUser LEFT JOIN user ON customer.idEmployee = user.idUser LEFT JOIN role ON role.idRole = employee.idRole"
  val list: Array[CustomerOrder] = null
  
  /**
   * Method to create the sql command to query all customer orders
   * return: String containing the sql command to query
   */
  def createQueryAllCustomerOrders(): String ={
    sqlSelect + sqlFrom + sqlJoins
  }
  
  def queryCustomerOrders(f: T => String, t:T): Array[CustomerOrder] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t))
    
    list
  }
  
  def createCustomerOrderEntities(rs:ResultSet): Array[CustomerOrder] = {
    if (rs.next()) {
      //construct user
      var user = new User(rs.getInt("user.idUser"), rs.getString("user.password"), rs.getString("user.forename"), rs.getString("user.surname"), rs.getString("user.email"), rs.getBoolean("user.isEmployee"))
    }
    list
  }
}