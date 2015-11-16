package com.qa.wota

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class SCTest extends FlatSpec{
   "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }
}