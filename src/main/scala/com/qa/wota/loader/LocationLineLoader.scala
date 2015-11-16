package com.qa.wota.loader

import com.qa.wota.entities.LocationLine
import java.sql.ResultSet

/**
 * @author jforster
 * Class to create SQL commands and load ResultSets into entities from a query for LocationLines
 */
class LocationLineLoader [T]{
  val sqlSelect = "SELECT *"
  val sqlFrom = " FROM locationline"
  val sqlUpdate = "UPDATE locationline" 
  
  /**
   * Method to query LocationLines based on input function
   * param f:(T) => String: Function that takes a search parameter and creates an SQL query based on it
   * Valid function LocationLineLoader.createQueryLocationLineByID: Creates query to search for LocationLines with the same LocationID
   * Valid function LocationLineLoader.createQueryLocationLineByItemID: Creates query to search for LocationLines with the same ItemID
   * param t:  Generic attribute defined by search term
   * returns: The produced Array of Location objects
   */
  def queryLocationLines(f: T => String, t:T): Array[LocationLine] = {
    //TODO write general query construction and execution
    null
  }
  
   /**
   * Method to update LocationLine based on input function
   * param f:(LocationLine) => String: Function that takes an input LocationLine and creates an SQL update from it
   * Valid function LocationLineLoader.createUpdateLocationLine: Creates update based on quantity in Location Line
   * param lL: LocationLine to be updated in the database
   */
  def updateLocationLine(f: LocationLine => String, lL:LocationLine) {
    //TODO write general update construction and execution
  }
  
  /**
   * Method to load data from database into the relevant entities through recursion
   * param rs: ResultSet of information from the database
   * param list: current Array of LocationLines already created
   * returns: Array of LocationLines constructed from ResultSet
   */
  def createLocationLineEntities(rs:ResultSet, list: Array[LocationLine]):Array[LocationLine] = {
    //TODO generate Location Line objects from result sets of the mySQL query
    null
  }
  
  /**
  * Method to create the SQL command to query LocationLines with the input ID
  * return: String containing the sql command to query
  */
  def createQueryLocationLineByID(id:T):String = {
    sqlSelect + sqlFrom + " WHERE idLocation = '" + id + "'"
  }
  
  /**
  * Method to create the SQL command to query LocationLines with the input ItemID
  * return: String containing the sql command to query
  */
  def createQueryLocationLineByItemID(id:T):String = {
    sqlSelect + sqlFrom + " WHERE idItem = " + id
  }
  
  /**
  * Method to create the SQL command to update the LocationLine
  * return: String containing the sql command to update
  */
  def createUpdateLocationLine(lL:LocationLine):String = {
    sqlUpdate + " SET quantity = " + lL.quantity + " WHERE idLocation = '" + lL.locationID + "'"
  }
}