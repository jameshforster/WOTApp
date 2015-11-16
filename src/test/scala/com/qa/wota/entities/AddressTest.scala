package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class AddressTest extends FlatSpec{
 "The Address" should "initialise with correct values" in {
   val address = new Address(1, 2, null, "City", "P0S CD3")
   assert (address.idAddress == 1)
   assert (address.idCustomer == 2)
   assert (address.city.equals("City"))
   assert (address.postcode.equals("P0S CD3"))
 }
}