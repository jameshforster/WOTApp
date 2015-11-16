package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class LocationTest extends FlatSpec{
  "Location" should "initialise with the correct values" in {
    val location = new Location("A1", 100, 40, null)
    assert(location.locationID.equals("A1"))
    assert(location.capacity == 100)
    assert(location.stored == 40)
  }
}