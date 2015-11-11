package com.qa.James.entities

import java.util.Date
import java.text.SimpleDateFormat
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author jforster
 * Class to hold the purchase order objects
 */
class PurchaseOrder (val idPurchaseOrder:Int, val supplier:Supplier, var purchaseOrderStatus:PurchaseOrderStatus, var lines:Array[PurchaseOrderLine], var employee:Employee, var dateExpected:Date, var datePlaced:Date){
  //constructor for no assigned employee
  def this(idPurchaseOrder:Int, supplier:Supplier, purchaseOrderStatus:PurchaseOrderStatus, lines:Array[PurchaseOrderLine], dateExpected:Date, datePlaced:Date) = this (idPurchaseOrder, supplier, purchaseOrderStatus, lines, null, dateExpected, datePlaced)
  //constructor for no expected date
  def this(idPurchaseOrder:Int, supplier:Supplier, purchaseOrderStatus:PurchaseOrderStatus, lines:Array[PurchaseOrderLine], employee:Employee, datePlaced:Date) = this (idPurchaseOrder, supplier, purchaseOrderStatus, lines, employee, null, datePlaced)
  //constructor for no expected date and no assigned employee
  def this(idPurchaseOrder:Int, supplier:Supplier, purchaseOrderStatus:PurchaseOrderStatus, lines:Array[PurchaseOrderLine], datePlaced:Date) = this (idPurchaseOrder, supplier, purchaseOrderStatus, lines, null, null, datePlaced)
  //constructor for unplaced order
  def this(idPurchaseOrder:Int, supplier:Supplier, purchaseOrderStatus:PurchaseOrderStatus, lines:Array[PurchaseOrderLine]) = this (idPurchaseOrder, supplier, purchaseOrderStatus, lines, null, null, null)
  
  val dF = new SimpleDateFormat("dd/MM/yy")
  var pOID = new ObjectProperty(this, "pOID", idPurchaseOrder)
  var pOStatus = new StringProperty(this, "cpOStatus", purchaseOrderStatus.statusName)
  var eID = new ObjectProperty(this, "eID", employee.user.idUser)
  var dPlaced: StringProperty = null
  var dExpected: StringProperty = null
  try {
    dPlaced = new StringProperty(this, "dPlaced", dF.format(datePlaced))
  }
  catch{
    case npe:NullPointerException => dPlaced = new StringProperty(this, "dPlaced", "")
  }
  try {
    dExpected = new StringProperty(this, "dShipped", dF.format(dExpected))
  }
  catch{
    case npe:NullPointerException => dExpected = new StringProperty(this, "dShipped", "")
  }
}