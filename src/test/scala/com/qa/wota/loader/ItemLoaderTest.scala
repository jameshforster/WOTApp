package com.qa.wota.loader

import org.scalatest.FlatSpec
import com.qa.wota.entities.Item

/**
 * @author jforster
 */
class ItemLoaderTest extends FlatSpec{
  
  queryItemTest
  def queryItemTest {
    
    "Item List" should "not be empty" in {
      val iLoader = new ItemLoader[Unit]
      assert(!iLoader.queryItem("", ()).isEmpty)
    }
    
    "Item List" should "contain one entry for an individual ID" in {
      val iLoader = new ItemLoader[Int]
      assert(iLoader.queryItem("ItemID", 1).length == 1)
    }
    
    "Item List" should "be null on an invalid ID search" in {
      val iLoader = new ItemLoader[Int]
      assert(iLoader.queryItem("ItemID", 500) == null)
    }
    
    "Item List" should "be null on an invalid search term" in {
      val iLoader = new ItemLoader[Int]
      assert(iLoader.queryItem("ItID", 1) == null)
    }
    
    "Item List" should "contain valid Item objects" in {
      val iLoader = new ItemLoader[Int]
      assert(iLoader.queryItem("ItemID", 1).head.isInstanceOf[Item])
    }
    
    "Item List" should "contain non-null Item objects" in  {
      val iLoader = new ItemLoader[Int]
      assert(iLoader.queryItem("ItemID", 1).head != null)
    }
  }
}