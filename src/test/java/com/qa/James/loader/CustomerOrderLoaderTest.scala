package com.qa.James.loader

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class CustomerOrderLoaderTest extends FlatSpec{
  
  createQueryAllCustomerOrdersTest
  def createQueryAllCustomerOrdersTest {
    "SQL Query (All)" should "produce a matching query" in {
      val cOLoader = new CustomerOrderLoader[Unit]
      assert(cOLoader.createQueryAllCustomerOrders(()).equals("SELECT customerorder.*, customerorderstatus.*, employee.*, customer.*, employeeUser.*, customerUser.*, role.* FROM customerorder LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user AS employeeUser ON employee.idEmployee = employeeUser.idUser LEFT JOIN user AS customerUser ON customer.idUser = customerUser.idUser LEFT JOIN role ON role.idRole = employee.role_idRole"))
    }
  }
  
  createQueryCustomerOrdersByIDTest
  def createQueryCustomerOrdersByIDTest {
    "SQL Query (ID)" should "produce a matching query" in {
      val cOLoader = new CustomerOrderLoader[Int]
      assert(cOLoader.createQueryCustomerOrdersByID(1).equals("SELECT customerorder.*, customerorderstatus.*, employee.*, customer.*, employeeUser.*, customerUser.*, role.* FROM customerorder LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user AS employeeUser ON employee.idEmployee = employeeUser.idUser LEFT JOIN user AS customerUser ON customer.idUser = customerUser.idUser LEFT JOIN role ON role.idRole = employee.role_idRole WHERE customerorder.idCustomerOrder = 1"))
    }
  }
  
  createQueryCustomerOrdersByEmployeeIDTest
  def createQueryCustomerOrdersByEmployeeIDTest{
    "SQL Query (Employee ID)" should "produce a matching query" in {
    val cOLoader = new CustomerOrderLoader[Int]
      assert(cOLoader.createQueryCustomerOrdersByEmployeeID(1).equals("SELECT customerorder.*, customerorderstatus.*, employee.*, customer.*, employeeUser.*, customerUser.*, role.* FROM customerorder LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user AS employeeUser ON employee.idEmployee = employeeUser.idUser LEFT JOIN user AS customerUser ON customer.idUser = customerUser.idUser LEFT JOIN role ON role.idRole = employee.role_idRole WHERE customerorder.idEmployee = 1"))
    }  
  }
  
  createQueryCustomerOrdersByStatusTest
  def createQueryCustomerOrdersByStatusTest{
    "SQL Query (Status" should "produce a matching query" in {
       val cOLoader = new CustomerOrderLoader[String]
       assert(cOLoader.createQueryCustomerOrdersByStatus("Picked").equals("SELECT customerorder.*, customerorderstatus.*, employee.*, customer.*, employeeUser.*, customerUser.*, role.* FROM customerorder LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user AS employeeUser ON employee.idEmployee = employeeUser.idUser LEFT JOIN user AS customerUser ON customer.idUser = customerUser.idUser LEFT JOIN role ON role.idRole = employee.role_idRole WHERE customerorderstatus.status LIKE '%Picked%'"))
    }
  }
  
  //TODO test to produce an update query from a customer order
  
  createCustomerOrderEntitiesTest
  def createCustomerOrderEntitiesTest {
    "Array" should "not be empty" in {
      val cOLoader = new CustomerOrderLoader[Unit]
      assert(!cOLoader.createCustomerOrderEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, "SELECT customerorder.*, customerorderstatus.*, employee.*, customer.*, employeeUser.*, customerUser.*, role.* FROM customerorder LEFT JOIN customerorderstatus ON customerorder.idCustomerOrderStatus = customerorderstatus.idCustomerOrderStatus LEFT JOIN employee ON customerorder.idEmployee = employee.idEmployee LEFT JOIN customer ON customerorder.idCustomer = customer.idUser LEFT JOIN user AS employeeUser ON employee.idEmployee = employeeUser.idUser LEFT JOIN user AS customerUser ON customer.idUser = customerUser.idUser LEFT JOIN role ON role.idRole = employee.role_idRole"), null).isEmpty)
    }
  }
}