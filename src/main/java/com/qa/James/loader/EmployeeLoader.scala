package com.qa.James.loader

import com.qa.James.entities.Employee
import java.sql.ResultSet
import com.qa.James.entities.User
import com.qa.James.entities.Role

/**
 * @author jforster
 */
class EmployeeLoader [T] {
  val sqlSelect:String = "SELECT employee.*, user.*, role.*"
  val sqlFrom:String = " FROM employee"
  val sqlJoins:String = " LEFT JOIN user ON employee.idEmployee = user.idUser LEFT JOIN role ON employee.role_idRole = role.idRole"
  
  /**
   * Method to query employees based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * param t: Generic attribute defined by search term
   * returns: The produced Array of Employee objects
   */
  def queryEmployee(f: T => String, t:T): Array[Employee] = {
    var rs = JDBCConnector.executeSQL(JDBCConnector.querySQL, f(t)) 
    createEmployeeEntities(rs, null)
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of Employees already created
   * returns: Array of Employees constructed from ResultSet
   */
  def createEmployeeEntities(rs:ResultSet, list:Array[Employee]): Array[Employee] = {
    if (rs.next()) {
      var user = new User(rs.getInt("user.idUser"), rs.getString("user.password"), rs.getString("user.forename"), rs.getString("user.surname"), rs.getString("user.email"), rs.getBoolean("user.isEmployee"))
      var role = new Role(rs.getInt("role.idRole"), rs.getString("Role"))
      var employee = new Employee(user, role)
      //if an Array exists, append to it, else create a new Array
      if (list != null) {
        createEmployeeEntities(rs, list:+(employee))
      }
      else {
        var tempList = new Array[Employee](1)
        tempList(0) = employee
        createEmployeeEntities(rs, tempList)
      }
    }
    else {
      rs.close()
      JDBCConnector.closeConnectionSQL()
      list
    }
  }
  
  def createQueryEmployeeByID (i:T):String = {
    sqlSelect + sqlFrom + sqlJoins + " WHERE employee.idEmployee = " + i
  }
  
  def createQueryEmployeeByEmail (i:T): String = {
    println( sqlSelect + sqlFrom + sqlJoins + " WHERE CONTAINS(user.email, '" + i + "')")
    sqlSelect + sqlFrom + sqlJoins + " WHERE user.email LIKE '%" + i + "%'"
  }
}