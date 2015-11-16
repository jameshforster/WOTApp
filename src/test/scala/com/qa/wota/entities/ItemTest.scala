package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class ItemTest extends FlatSpec{
  "Item" should "initialise with correct values" in {
    val item = new Item(1, "Item", 15, "Description", 25, 12, false, 10, 9, false)
    assert(item.idItem == 1)
    assert(item.itemName.equals("Item"))
    assert(item.itemStock == 15)
    assert(item.itemDescription.equals("Description"))
    assert(item.itemPrice == 25)
    assert(item.itemCost == 12)
    assert(!item.discontinued)
    assert(item.salesRate == 10)
    assert(item.pSalesRate == 9)
    assert(!item.isPorousWare)
  }
}