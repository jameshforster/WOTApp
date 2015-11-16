package com.qa.James.loader

import com.qa.James.entities.Location
import java.sql.ResultSet

/**
 * @author jforster
 */
class LocationLoader [T]{
  val sqlSelect = "SELECT location.*"
  val sqlFrom = " FROM location"
  
  def queryLocation(f:T => String, t:T): Array[Location] ={
    //TODO write general query construction
    null
  }
  
  def createLocationEntities(rs:ResultSet, list:Array[Location]): Array[Location] = {
    //TODO write creation of entities from ResultSet
    null
  }
  
  def createQueryLocationByID(locationID:T): String = {
    sqlSelect + sqlFrom + " WHERE location.locationID = '" + locationID + "'"
  }
  
  def createQueryLocationByAll(t:T): String = {
    //TODO write sql query creation code for all locations
    null
  }
  
  def createQueryLocationByItemID(t:T): String = {
    //TODO write sql query creation code for locations by Item
    null
  }
}