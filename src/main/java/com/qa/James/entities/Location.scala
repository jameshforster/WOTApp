package com.qa.James.entities

import scalafx.beans.property.StringProperty
import scalafx.beans.property.ObjectProperty

/**
 * @author jforster
 * Class to hold the location objects and formatting for GUI display
 */
class Location (val locationID:String, val item:Item, var quantity:Int, val lines:Array[LocationLine]){
  val lID = new StringProperty(this, "lID", locationID)
  val iID = new ObjectProperty(this, "iID", item.idItem)
  val quant = new ObjectProperty(this, "quant", quantity)
}