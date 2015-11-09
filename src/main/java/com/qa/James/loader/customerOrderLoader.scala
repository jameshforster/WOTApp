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
 * Class to manage loading of customer orders from database and create relevant entities
 */
class CustomerOrderLoader[T] {
  val sqlSelect:String = "SELECT customerorder.*, customerorderstatus.*, employee.*, customer.*, employeeUser.*, customerUser.*, role.*"
  val sqlFrom:String = " FROM customerorder"
  val sqlJoins:String = " LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user AS employeeUser ON employee.idEmployee = employeeUser.idUser LEFT JOIN user AS customerUser ON customer.idUser = customerUser.idUser LEFT JOIN role ON role.idRole = employee.role_idRole"
  
  /**
   * Method to create the sql command to query all customer orders
   * return: String containing the sql command to query
   */
  def createQueryAllCustomerOrders(i:T): String ={
    sqlSelect + sqlFrom + sqlJoins
  }
  
  /**
   * Method to create the SQL command to query customer orders with the input ID
   * return: String containing the sql command to query
   */
  def createQueryCustomerOrdersByID(i:T): String = {
    sqlSelect + sqlFrom + sqlJoins + " WHERE customerorder.idCustomerOrder = " + i
  }
  
  /**
   * Method to query customer orders based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid function CustomerOrderLoader.createQueryAllCustomerOrders: Creates query to search for all customer orders
   * Valid function CustomerOrderLoader.createQueryCustomerOrdersByID: Creates query to search for customer orders with the same ID (T:Int)
   * param t: Generic attribute defined by search term
   * returns: The produced Array of CustomerOrder objects
   */
  def queryCustomerOrders(f: T => String, t:T): Array[CustomerOrder] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t)) 
    createCustomerOrderEntities(rs, null)
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of CustomerOrders already created
   * returns: Array of CustomerOrders constructed from ResultSet
   */
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
      var customerOrderLine = new CustomerOrderLine(1,null,1,1)
      var customerOrderLines:Array[CustomerOrderLine] = null
      // get address from MongoDB
      val aL = new AddressLoader[Int]
      val address = aL.queryAddress("AddressID", rs.getInt("customerorder.idAddress")).apply(0)
      var customerOrder:CustomerOrder = null
      customerOrder = new CustomerOrder(rs.getInt("customerorder.idCustomerOrder"), customer, customerOrderStatus, address, customerOrderLines, employee, rs.getDate("customerorder.datePlaced"), rs.getDate("customerorder.dateShipped"), rs.getBoolean("customerOrder.isPaid"))
      //if an Array exists, append to it, else create a new Array
      if (list != null) {
        createCustomerOrderEntities(rs, list:+(customerOrder))
      }
      else {
        var tempList = new Array[CustomerOrder](1)
        tempList(0) = customerOrder
        createCustomerOrderEntities(rs, tempList)
      }
      
    }
    else {
      rs.close()
      JDBCConnector.closeConnectionSQL()
      list
    }
  }
}