package com.qa.wota.entities

import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty

/**
 * @author jforster
 * Class to hold the customer order line objects
 */
class CustomerOrderLine (val idCustomerOrder: Int, val item: Item, var quantity: Int, var quantityPicked: Int){
  def this(idCustomerOrder: Int, item: Item, quantity: Int) = this(idCustomerOrder, item, quantity, 0)
  var iID = new ObjectProperty(this, "iID", item.idItem)
  var iName = new StringProperty(this, "iName", item.itemName)
  var quant = new ObjectProperty(this, "quant", quantity)
  var quantP = new ObjectProperty(this, "quantP", quantityPicked)
  var iPrice = new ObjectProperty(this, "iPrice", item.itemPrice)
  var subtotal = new ObjectProperty(this, "subtotal", quantity * item.itemPrice)
}