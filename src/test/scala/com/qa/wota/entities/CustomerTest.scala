package com.qa.wota.entities

import org.scalatest.FlatSpec
import java.text.SimpleDateFormat

/**
 * @author jforster
 */
class CustomerTest extends FlatSpec{
  "The Customer" should "initialise with correct values" in {
    val df = new SimpleDateFormat("dd/MM/yy")
    val customer = new Customer(null, df.parse("12/04/90"), 4, "01945 783455", 0)
    assert(df.format(customer.dateOfBirth).equals("12/04/90"))
    assert(customer.credit == 4)
    assert(customer.phoneNumber.equals("01945 783455"))
    assert(customer.blackListStrikes == 0)
  }
}