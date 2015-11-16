package com.qa.wota.loader

import com.qa.wota.entities.Location
import java.sql.ResultSet

/**
 * @author jforster
 * Class to create SQL commands and load ResultSets into entities from a query for Locations
 */
class LocationLoader [T]{
  val sqlSelect = "SELECT location.*"
  val sqlFrom = " FROM location"
  
  /**
   * Method to query Locations based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid function LocationLoader.createQueryLocationByID: Creates query to search for Locations with the same LocationID
   * Valid function LocationLoader.createQueryLocationByAll: Creates query to search for all Locations
   * Valid function LocationLoader.createQueryLocationByItemID: Creates query to search for Locations with the same ItemID
   * param t:  Generic attribute defined by search term
   * returns: The produced Array of Location objects
   */
  def queryLocation(f:T => String, t:T): Array[Location] ={
    //TODO write general query construction and execution
    null
  }

  def updateLocation(f:Location => String, l:Location) {
    //TODO write general update construction and execution
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of Locations already created
   * returns: Array of Locations constructed from ResultSet
   */
  def createLocationEntities(rs:ResultSet, list:Array[Location]): Array[Location] = {
    //TODO write creation of entities from ResultSet
    null
  }
  
  /**
  * Method to create the SQL command to query Locations with the input ID
  * return: String containing the sql command to query
  */
  def createQueryLocationByID(locationID:T): String = {
    sqlSelect + sqlFrom + " WHERE location.locationID = '" + locationID + "'"
  }
  
  /**
  * Method to create the SQL command to query all Locations
  * return: String containing the sql command to query
  */
  def createQueryLocationByAll(t:T): String = {
    //TODO write sql query creation code for all locations
    null
  }
  
  /**
  * Method to create the SQL command to query Locations with the input ItemID
  * return: String containing the sql command to query
  */
  def createQueryLocationByItemID(t:T): String = {
    //TODO write sql query creation code for locations by Item
    null
  }
  
  /**
   * Method to create the SQL command to update Location's stored value
   * return: String containing the sql command to update
   */
  def createUpdateLocation(l:Location): String = {
    //TODO write sql update creation code for the input Location
    null
  }
}