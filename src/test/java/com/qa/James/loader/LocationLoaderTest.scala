package com.qa.James.loader

import org.scalatest.FlatSpec
import com.qa.James.entities.Location

/**
 * @author jforster
 */
class LocationLoaderTest extends FlatSpec{
  
  queryLocationTest
  def queryLocationTest{
    "Final Array" should "not be empty" in {
      val lLoader = new LocationLoader [Unit]
      assert(!lLoader.queryLocation(lLoader.createQueryLocationByAll, ()).isEmpty)
    }
  }
  
  createLocationEntitiesTest
  def createLocationEntitiesTest {
    
    "Array" should "not be empty" in {
      val lLoader = new LocationLoader[Unit]
      assert(!lLoader.createLocationEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLoader.createQueryLocationByAll(())), null).isEmpty)
    }
    
    "Array" should "contain only one object when queried by ID" in {
      val lLoader = new LocationLoader[String]
      assert(lLoader.createLocationEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLoader.createQueryLocationByID("A1")), null).length == 1)
    }
    
    "Array" should "be null if invalid ID queried" in {
      val lLoader = new LocationLoader[String]
      assert(lLoader.createLocationEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLoader.createQueryLocationByID("5B")), null) == null)
    }
    
    "Array" should "not be empty when querying by a valid Item ID" in {
      val lLoader = new LocationLoader[Int]
      assert(!lLoader.createLocationEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLoader.createQueryLocationByItemID(1)), null).isEmpty)
    }
    
    "Array" should "contain Location objects" in {
      val lLoader = new LocationLoader[Unit]
      assert(!lLoader.createLocationEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLoader.createQueryLocationByAll(())), null).head.isInstanceOf[Location])
    }
    
    "Array" should "contain non-null Location Objects" in {
      val lLoader = new LocationLoader[Unit]
      assert(lLoader.createLocationEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLoader.createQueryLocationByAll(())), null).head != null)
    }
  }
  
  createQueryLocationByIDTest
  def createQueryLocationByIDTest {
    "SQL Query(ID)" should "equal the query String" in {
      val lLoader = new LocationLoader[String]
      assert(lLoader.createQueryLocationByID("A1").equals("SELECT location.* FROM location WHERE location.locationID = 'A1'"))
    }
  }
  
  def createQueryLocationByAll {
    "SQL Query(All)" should "equal the query String" in {
      val lLoader = new LocationLoader[Unit]
      assert(lLoader.createQueryLocationByAll(()).equals("SELECT location.* FROM location"))
    }
  }
  
  def createQueryLocationByItemID {
    "SQL Query(ItemID)" should "equal the query String" in {
      val lLoader = new LocationLoader[Int]
      assert(lLoader.createQueryLocationByItemID(1).equals("SELECT location.* FROM location WHERE location.itemID = 1"))
    }
  }
}