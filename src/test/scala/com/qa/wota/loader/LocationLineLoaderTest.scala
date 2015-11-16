package com.qa.wota.loader

import org.scalatest.FlatSpec

import com.qa.wota.entities.LocationLine
import com.qa.wota.loader.JDBCConnector;

/**
 * @author jforster
 */
class LocationLineLoaderTest extends FlatSpec{
  
 
  createUpdateLocationLineTest
  def createUpdateLocationLineTest {
    "SQL Update" should "equal the input string" in {
      val lLLoader = new LocationLineLoader [String]
      val locationLine = lLLoader.queryLocationLines(lLLoader.createQueryLocationLineByID, "A1").head
      assert(lLLoader.createUpdateLocationLine(locationLine).equals("UPDATE locationline SET quantity = " + locationLine.quantity + "WHERE idLocation = '" + locationLine.locationID + "'"))
    }
  }
  
  createQueryLocationLineByIDTest
  def createQueryLocationLineByIDTest {
    "SQL Query(LocationID)" should "equal the input string" in {
      val lLLoader = new LocationLineLoader[String]
      assert(lLLoader.createQueryLocationLineByID("A1").equals("SELECT * FROM locationline WHERE idLocation = 'A1'"))
    }
  }
  
  createQueryLocationLineByItemID
  def createQueryLocationLineByItemID {
    "SQL Query(ItemID)" should "equal the input string" in {
      val lLLoader = new LocationLineLoader[Int]
      assert(lLLoader.createQueryLocationLineByItemID(1).equals("SELECT * FROM locationline WHERE idItem = 1"))
    }
  }
  
  createLocationLineEntitiesTest
  def createLocationLineEntitiesTest {
    "Array" should "not be empty when queried by ID" in{
      val lLLoader = new LocationLineLoader[String]
      assert(!lLLoader.createLocationLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLLoader.createQueryLocationLineByID("A1")), null).isEmpty)
    }
    
    "Array" should "not be empty when queried by itemID" in {
      val lLLoader = new LocationLineLoader[Int]
      assert(!lLLoader.createLocationLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLLoader.createQueryLocationLineByItemID(1)), null).isEmpty)
    }
    
    "Array" should "be null when queried by an invalid ID" in {
      val lLLoader = new LocationLineLoader[String]
      assert(lLLoader.createLocationLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLLoader.createQueryLocationLineByID("1B")), null) == null)
    }
    
    "Array" should "contain LocationLine objects" in {
      val lLLoader = new LocationLineLoader[String]
      assert(lLLoader.createLocationLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLLoader.createQueryLocationLineByID("A1")), null).head.isInstanceOf[LocationLine])
    }
    
    "Array" should "contain non null LocationLine objects" in {
      val lLLoader = new LocationLineLoader[String]
      assert(lLLoader.createLocationLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, lLLoader.createQueryLocationLineByID("A1")), null).head != null)
    }
  }
  
  updateLocationLineTest
  def updateLocationLineTest {
    "Database LocationLine" should "be equal to the inputted LocationLine" in {
      val lLLoader = new LocationLineLoader [String]
      val locationLine = lLLoader.queryLocationLines(lLLoader.createQueryLocationLineByID, "A1").head
      locationLine.quantity = 10
      lLLoader.updateLocationLine (lLLoader.createUpdateLocationLine, locationLine)
      assert(lLLoader.queryLocationLines(lLLoader.createQueryLocationLineByID, "A1").head.equals(locationLine))
    }
  }
  

}