package com.qa.James.loader

import com.qa.James.entities.LocationLine
import java.sql.ResultSet

class LocationLineLoader [T]{
  
  def queryLocationLines(f: T => String, t:T): Array[LocationLine] = {
    //TODO write general query construction and execution
    null
  }
  
  def updateLocationLine(f: LocationLine => String, lL:LocationLine) {
    //TODO write general update construction and execution
  }
  
  def createLocationLineEntities(rs:ResultSet, list: Array[LocationLine]):Array[LocationLine] = {
    //TODO generate Location Line objects from result sets of the mySQL query
    null
  }
  
  def createQueryLocationLineByID(id:T):String = {
    //TODO create SQL query string for finding location lines from location ID
    null
  }
  
  def createQueryLocationLineByItemID(id:T):String = {
    //TODO create SQL query string for finding location lines from item ID
    null
  }
  
  def createUpdateLocationLine(lL:LocationLine):String = {
    //TODO create SQL update string for updating location lines
    null
  }
}