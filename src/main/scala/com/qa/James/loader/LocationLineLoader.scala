package com.qa.James.loader

import com.qa.James.entities.LocationLine
import java.sql.ResultSet

class LocationLineLoader [T]{
  val sqlSelect = "SELECT *"
  val sqlFrom = " FROM locationline"
  val sqlUpdate = "UPDATE locationline" 
  
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
    sqlSelect + sqlFrom + " WHERE idLocation = '" + id + "'"
  }
  
  def createQueryLocationLineByItemID(id:T):String = {
    sqlSelect + sqlFrom + " WHERE idItem = " + id
  }
  
  def createUpdateLocationLine(lL:LocationLine):String = {
    sqlUpdate + " SET quantity = " + lL.quantity + " WHERE idLocation = '" + lL.locationID + "'"
    null
  }
}