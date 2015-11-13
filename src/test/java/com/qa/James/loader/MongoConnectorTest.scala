package com.qa.James.loader
import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class MongoConnectorTest extends FlatSpec{
  
  queryMongo
  def queryMongo {
    
    "A Collection" should "not be empty when pulling all documents from a table" in {
      val mC = new MongoConnector[Unit]("Address")
      assert(!mC.queryMongo("", ()).isEmpty)
    }
    
    "A Collection" should "contain one entry when searching a specific entry" in {
      val mC = new MongoConnector[Int]("Address")
      assert(mC.queryMongo("AddressID", 1).size == 1)
    }
    
    "A Collection" should "contain no entires when searching a non-existant document " in {
      val mC = new MongoConnector[Int]("Address")
      assert(mC.queryMongo("AddressID", 5).isEmpty)
    }
    
    "A Collection" should "be empty if invalid search term used" in {
      val mC = new MongoConnector[Int]("Address")
      assert(mC.queryMongo("AdressID", 1).isEmpty)
    }
    
    "A Collection" should "be empty if invalid collection term used" in {
      val mC = new MongoConnector[Int]("Adress")
      assert(mC.queryMongo("AddressID", 1).isEmpty)
    }
  }
}