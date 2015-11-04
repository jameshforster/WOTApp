package com.qa.James.entities

import java.util.Date

/**
 * @author jforster
 */
class CustomerOrder (val idCustomerOrder:Int, val customer:Customer, var customerOrderStatus:CustomerOrderStatus, val address:Address, val lines:Array[CustomerOrderLine], var employee:Employee, datePlaced:Date, var dateShipped:Date, var isPaid:Boolean){
  def this(idCustomerOrder:Int, customer:Customer, customerOrderStatus:CustomerOrderStatus, address:Address, lines:Array[CustomerOrderLine], employee:Employee, datePlaced:Date, isPaid:Boolean) = this(idCustomerOrder, customer, customerOrderStatus, address, lines, employee, datePlaced, null, isPaid)
  
}