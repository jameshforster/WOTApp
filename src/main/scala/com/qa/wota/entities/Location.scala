package com.qa.wota.entities

import scalafx.beans.property.StringProperty
import scalafx.beans.property.ObjectProperty

/**
 * @author jforster
 * Class to hold the location objects and formatting for GUI display
 */
class Location (val locationID:String, var capacity:Int, var stored: Int, val lines:Array[LocationLine]){
  val lID = new StringProperty(this, "lID", locationID)
  val lCap = new ObjectProperty(this, "lCap", capacity)
  val lStor = new ObjectProperty(this, "lStor", stored)
}