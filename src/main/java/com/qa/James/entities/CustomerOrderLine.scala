package com.qa.James.entities

/**
 * @author jforster
 */
class CustomerOrderLine (idCustomerOrder: Int, item: Item, var quantity: Int, var quantityPicked: Int){
  def this(idCustomerOrder: Int, item: Item, quantity: Int) = this(idCustomerOrder, item, quantity, 0)
  
  def getIDCustomerOrder(): Int = 
    idCustomerOrder
    
  def getItem(): Item = 
    item
}