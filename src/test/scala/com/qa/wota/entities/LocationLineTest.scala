package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class LocationLineTest extends FlatSpec{
  "LocationLine" should "initialise with the correct values" in {
    val locationLine = new LocationLine("A1", null, 10)
    assert(locationLine.locationID.equals("A1"))
    assert(locationLine.quantity == 10)
  }
  
}