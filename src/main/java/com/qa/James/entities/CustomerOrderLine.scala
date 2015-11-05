package com.qa.James.entities

/**
 * @author jforster
 * Class to hold the customer order line objects
 */
class CustomerOrderLine (val idCustomerOrder: Int, val item: Item, var quantity: Int, var quantityPicked: Int){
  def this(idCustomerOrder: Int, item: Item, quantity: Int) = this(idCustomerOrder, item, quantity, 0)
}