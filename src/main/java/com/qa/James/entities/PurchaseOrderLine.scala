package com.qa.James.entities

/**
 * @author jforster
 * Class to hold purchase order line objects
 */
class PurchaseOrderLine (val idPurchaseOrder:Int, val item:Item, var quantity:Int, var damagedQuantity:Int){
  
}