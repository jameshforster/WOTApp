package com.qa.James.loader

import org.scalatest.FlatSpec
import com.qa.James.entities.Employee

class EmployeeLoaderTest extends FlatSpec {
  
  createQueryEmployeeByEmailTest
  def createQueryEmployeeByEmailTest {
    "SQL String" should "be equal to email query" in {
      val eLoader = new EmployeeLoader[String]
      assert(eLoader.createQueryEmployeeByEmail("Al.Stock@NBgardens.co.uk") == "SELECT employee.*, user.*, role.* FROM employee LEFT JOIN user ON employee.idEmployee = user.idUser LEFT JOIN role ON employee.role_idRole = role.idRole WHERE user.email LIKE '%Al.Stock@NBgardens.co.uk%'")
    }
  }
  
  createQueryEmployeeByIDTest
  def createQueryEmployeeByIDTest {
    "SQL String" should "be equal to ID query" in {
      val eLoader = new EmployeeLoader[Int]
      assert(eLoader.createQueryEmployeeByID(1) == "SELECT employee.*, user.*, role.* FROM employee LEFT JOIN user ON employee.idEmployee = user.idUser LEFT JOIN role ON employee.role_idRole = role.idRole WHERE employee.idEmployee = 1")
    }
  }
  
  def createEmployeeEntitiesTest {
    "Array" should "produce one result with a valid ID" in {
      val eLoader = new EmployeeLoader[Int]
      assert(eLoader.createEmployeeEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, eLoader.createQueryEmployeeByID(1)), null).length == 1)
    }
    
    "Array" should "not be empty with a valid email search term" in {
      val eLoader = new EmployeeLoader[String]
      assert(!eLoader.createEmployeeEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, eLoader.createQueryEmployeeByEmail("@NBgardens")), null).isEmpty)
    }
    
    "Array" should "not be empty with an invalid search term" in {
      val eLoader = new EmployeeLoader[Int]
      assert(!eLoader.createEmployeeEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, eLoader.createQueryEmployeeByID(100)), null).isEmpty)
    }
    
    "Array" should "contain Employees" in {
      val eLoader = new EmployeeLoader[Int]
      assert(eLoader.createEmployeeEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, eLoader.createQueryEmployeeByID(1)), null).head.isInstanceOf[Employee])
    }
    
    "Array" should "contain non-null Employees" in {
      val eLoader = new EmployeeLoader[Int]
      assert(eLoader.createEmployeeEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, eLoader.createQueryEmployeeByID(1)), null).head != null)
    }
  }
}