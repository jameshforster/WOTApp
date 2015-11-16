package com.qa.wota.entities

import scalafx.beans.property.StringProperty
import scalafx.beans.property.ObjectProperty

/**
 * @author jforster
 * Class to hold the location line objects and formatting for GUI display
 */
class LocationLine (val locationID:String, val item:Item, var quantity:Int){
  val lID = new StringProperty(this, "lID", locationID)
  val iID = new ObjectProperty(this, "iID", item.idItem)
  val quant = new ObjectProperty(this, "quant", quantity)
}