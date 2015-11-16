package com.qa.wota.entities

import org.scalatest.FlatSpec
import java.text.SimpleDateFormat

/**
 * @author jforster
 */
class CustomerOrderTest extends FlatSpec{
  "CustomerOrder" should "initialise with correct values" in {
    val df = new SimpleDateFormat("dd/MM/yy")
    val customerOrder = new CustomerOrder(1, null, null, null, null, null, df.parse("10/05/15"), df.parse("15/06/15"), true)
    assert(customerOrder.idCustomerOrder == 1)
    assert(df.format(customerOrder.datePlaced).equals("10/05/15"))
    assert(df.format(customerOrder.dateShipped).equals("15/06/15"))
    assert(customerOrder.isPaid)
  }
}