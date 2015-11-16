package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class CustomerOrderStatusTest extends FlatSpec{
  "CustomerOrderStatus" should "initialise with correct values" in {
    val customerOrderStatus = new CustomerOrderStatus(1, "Arrived")
    assert(customerOrderStatus.idCustomerOrderStatus == 1)
    assert(customerOrderStatus.statusName.equals("Arrived"))
  }
}