package com.qa.James.entities

import java.util.Date

/**
 * @author jforster
 */
class CustomerOrder (idCustomerOrder:Int, customer:Customer, address:Address, lines:Array[CustomerOrderLine], var employee:Employee, datePlaced:Date, var dateShipped:Date, var isPaid:Boolean){
  def this(idCustomerOrder:Int, customer:Customer, address:Address, lines:Array[CustomerOrderLine], employee:Employee, datePlaced:Date, isPaid:Boolean) = this(idCustomerOrder, customer, address, lines, employee, datePlaced, null, isPaid)
  
  def getID():Int = 
    idCustomerOrder
    
  def getCustomer():Customer =
    customer
    
  def getAddress():Address =
    address
    
  def getLines():Array[CustomerOrderLine] =
    lines
    
  def getDatePlaced():Date =
    datePlaced
}