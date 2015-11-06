package com.qa.James.loader

import com.qa.James.entities.CustomerOrder
import java.sql.ResultSet
import com.qa.James.entities.Role
import com.qa.James.entities.User
import com.qa.James.entities.Customer
import com.qa.James.entities.Employee
import com.qa.James.entities.CustomerOrderStatus
import com.qa.James.entities.CustomerOrderLine
import com.qa.James.entities.Address

/**
 * @author jforster
 * Object to manage loading of customer orders from database and create relevant entities
 */
class CustomerOrderLoader[T] {
  val sqlSelect:String = "SELECT customerorder.*, customerorderstatus.*, employee.*, customer.*, employeeUser.*, customerUser.*, role.*"
  val sqlFrom:String = " FROM customerorder"
  val sqlJoins:String = " LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user AS employeeUser ON employee.idEmployee = employeeUser.idUser LEFT JOIN user AS customerUser ON customer.idUser = customerUser.idUser LEFT JOIN role ON role.idRole = employee.role_idRole"
  val testList: Array[CustomerOrder] = null
  
  /**
   * Method to create the sql command to query all customer orders
   * return: String containing the sql command to query
   */
  def createQueryAllCustomerOrders(i:T): String ={
    sqlSelect + sqlFrom + sqlJoins
  }
  
  def queryCustomerOrders(f: T => String, t:T): Array[CustomerOrder] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t)) 
    rs.next()
    println("Not Null Loader")
    createCustomerOrderEntities(rs, testList)
  }
  
  def createCustomerOrderEntities(rs:ResultSet, list:Array[CustomerOrder]): Array[CustomerOrder] = {
    if (rs.next()) {
      //construct user
      var user = new User(rs.getInt("customerUser.idUser"), rs.getString("customerUser.password"), rs.getString("customerUser.forename"), rs.getString("customerUser.surname"), rs.getString("customerUser.email"), rs.getBoolean("customerUser.isEmployee"))
      var customer = new Customer(user, rs.getDate("dateOfBirth"), rs.getFloat("credit"), rs.getString("phoneNumber"), rs.getInt("blackStrikes"))
      var role = new Role(rs.getInt("role.idRole"), rs.getString("Role"))
      user = new User(rs.getInt("employeeUser.idUser"), rs.getString("employeeUser.password"), rs.getString("employeeUser.forename"), rs.getString("employeeUser.surname"), rs.getString("employeeUser.email"), rs.getBoolean("employeeUser.isEmployee"))
      var employee = new Employee(user, role)
      var customerOrderStatus = new CustomerOrderStatus (rs.getInt("customerorderstatus.idCustomerOrderStatus"), rs.getString("customerorderstatus.status"))
      //TODO get customer order lines
      var customerOrderLines:Array[CustomerOrderLine] = null
      //TODO get address
      var address:Address = null
      var customerOrder = new CustomerOrder(rs.getInt("customerorder.idCustomerOrder"), customer, customerOrderStatus, address, customerOrderLines, employee, rs.getDate("customerorder.datePlaced"), rs.getDate("customerorder.dateShipped"), rs.getBoolean("customerOrder.isPaid"))
      createCustomerOrderEntities(rs, list:+(customerOrder))
    }
    list
  }
}