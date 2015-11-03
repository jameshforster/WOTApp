package com.qa.James

import com.qa.James.entities.CustomerOrder
import com.qa.James.entities.Address
import com.qa.James.entities.Employee
import java.util.Date
import com.qa.James.entities.CustomerOrderLine
import com.qa.James.entities.CustomerOrder
import com.qa.James.entities.Customer


object Main {
  def main (args: Array[String]) {
    var address = new Address()
    var employee = new Employee()
    var datePlaced = new Date()
    var customer = new Customer()
    var lineList = new Array[CustomerOrderLine](1)
    var customerOrder = new CustomerOrder(1, customer, address, lineList, employee, datePlaced, false)
    println(customerOrder.isPaid)
    customerOrder.isPaid = true
    println(customerOrder.isPaid)
  }
}