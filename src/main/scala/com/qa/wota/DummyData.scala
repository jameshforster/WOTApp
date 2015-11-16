package com.qa.wota

import com.qa.wota.entities.CustomerOrder
import com.qa.wota.entities.Employee
import com.qa.wota.entities.User
import com.qa.wota.entities.Role
import java.text.SimpleDateFormat
import com.qa.wota.entities.CustomerOrderStatus
import java.util.Date

/**
 * @author jforster
 */
object DummyData {
  val dF = new SimpleDateFormat("dd/MM/yy")
  def getCustomerOrdersByAll(): Array[CustomerOrder] = {
   var order1 = new CustomerOrder(1, null, getCustomerOrderStatusByID, null, null, getEmployeeByID(1), dF.parse("08/10/15"), dF.parse("08/11/15"), false)
   var order2 = new CustomerOrder(2, null, getCustomerOrderStatusByID, null, null, getEmployeeByID(2), dF.parse("10/10/15"), dF.parse("08/12/15"), false)
   Array[CustomerOrder](order1, order2)
  }
  
  def getEmployeeByID(id:Int): Employee = {
    new Employee(getUserByID(id), getRoleByRoleID)
  }
  
  def getUserByID(id:Int): User = {
    new User(id, "spikerules", "Ben", "Back",  "ben.back@NBgardens.co.uk", false)
  }
  
  def getRoleByRoleID(): Role = {
    new Role(1, "Warehouse Operative")
  }
  
  def getCustomerOrderStatusByID(): CustomerOrderStatus = {
    new CustomerOrderStatus(1, "Placed")
  }
  
  def getCustomerOrderByID(id:Int): CustomerOrder = {
    var result: CustomerOrder = null
    for (i:Int <- 0 to getCustomerOrdersByAll().size - 1) {
      if (getCustomerOrdersByAll.apply(i).idCustomerOrder == id){
        result = getCustomerOrdersByAll.apply(i)
      }
    }
    result
  }
}