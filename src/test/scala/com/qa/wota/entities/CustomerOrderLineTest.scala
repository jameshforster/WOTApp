package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class CustomerOrderLineTest extends FlatSpec{
  "CustomerOrderLine" should "initialise with the correct values" in {
    val customerOrderLine = new CustomerOrderLine(1, null, 10, 5)
    assert(customerOrderLine.idCustomerOrder == 1)
    assert(customerOrderLine.quantity == 10)
    assert(customerOrderLine.quantityPicked == 5)
  }
}