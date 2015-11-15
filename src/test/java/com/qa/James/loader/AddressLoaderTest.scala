package com.qa.James.loader

import org.scalatest.FlatSpec
import com.qa.James.entities.Address

/**
 * @author jforster
 */
class AddressLoaderTest extends FlatSpec{
  
  queryAddressTest
  def queryAddressTest {
    "Address List" should "not be empty" in {
      val aLoader = new AddressLoader[Unit]
      assert (!aLoader.queryAddress("", ()).isEmpty)
    } 
    
    "Address List" should "contain one entry when individual ID is searched" in {
      val aLoader = new AddressLoader[Int]
      assert (aLoader.queryAddress("AddressID", 1).length == 1)
    }
    
    "Address List" should "be null on an invalid ID search" in {
      val aLoader = new AddressLoader[Int]
      assert(aLoader.queryAddress("AddressID", 100) == null)
    }
    
    "Address List" should "be null on an invalid search term" in {
      val aLoader = new AddressLoader[Int]
      assert(aLoader.queryAddress("AdressID", 1) == null)
    }
    
    "Address List" should "contain valid address entities" in {
      val aLoader = new AddressLoader[Unit]
      assert (aLoader.queryAddress("", ()).head.isInstanceOf[Address])
    }
    
    "Address List" should "contain non-null entities" in {
      val aLoader = new AddressLoader[Unit]
      assert (aLoader.queryAddress("", ()).head != null)
    }
  }
}