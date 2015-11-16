package com.qa.wota.entities

import scalafx.beans.property.StringProperty
import scalafx.beans.property.IntegerProperty
import scalafx.beans.property.ObjectProperty

/**
 * @author jforster
 * Class to hold employee role objects
 */
class Role (val idRole:Int, val roleName:String){
  
  val _idRole = new ObjectProperty(this, "roleid", idRole)
  val _roleName = new StringProperty(this, "rolename", roleName)
}