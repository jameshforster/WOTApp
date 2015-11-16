package com.qa.wota.entities

import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author jforster
 * Class to hold purchase order line objects
 */
class PurchaseOrderLine (val idPurchaseOrder:Int, val item:Item, var quantity:Int, var damagedQuantity:Int){
  var iID = new ObjectProperty(this, "iID", item.idItem)
  var iName = new StringProperty(this, "iName", item.itemName)
  var quant = new ObjectProperty(this, "quant", quantity)
  var quantD = new ObjectProperty(this, "quantD", damagedQuantity)
  var iPrice = new ObjectProperty(this, "iPrice", item.itemPrice)
  var subtotal = new ObjectProperty(this, "subtotal", quantity * item.itemPrice)
}