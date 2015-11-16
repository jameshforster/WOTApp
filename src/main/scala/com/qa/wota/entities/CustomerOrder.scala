package com.qa.wota.entities

import java.util.Date
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty
import java.text.SimpleDateFormat

/**
 * @author jforster
 * Class to hold the customer order objects and formatting for GUI display
 */
class CustomerOrder (val idCustomerOrder:Int, val customer:Customer, var customerOrderStatus:CustomerOrderStatus, val address:Address, val lines:Array[CustomerOrderLine], var employee:Employee, val datePlaced:Date, var dateShipped:Date, var isPaid:Boolean){
  def this(idCustomerOrder:Int, customer:Customer, customerOrderStatus:CustomerOrderStatus, address:Address, lines:Array[CustomerOrderLine], employee:Employee, datePlaced:Date, isPaid:Boolean) = this(idCustomerOrder, customer, customerOrderStatus, address, lines, employee, datePlaced, null, isPaid)
  val dF = new SimpleDateFormat("dd/MM/yy")
  var cOID = new ObjectProperty(this, "cOID", idCustomerOrder)
  var cOStatus = new StringProperty(this, "cOStatus", customerOrderStatus.statusName)
  var eID = new ObjectProperty(this, "eID", employee.user.idUser)
  var dPlaced: StringProperty = null
  var dShipped: StringProperty = null
  try {
    dPlaced = new StringProperty(this, "dPlaced", dF.format(datePlaced))
  }
  catch{
    case npe:NullPointerException => dPlaced = new StringProperty(this, "dPlaced", "")
  }
  try {
    dShipped = new StringProperty(this, "dShipped", dF.format(dateShipped))
  }
  catch{
    case npe:NullPointerException => dShipped = new StringProperty(this, "dShipped", "")
  }
}