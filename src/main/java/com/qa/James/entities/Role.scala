package com.qa.James.entities

import scalafx.beans.property.StringProperty
import scalafx.beans.property.IntegerProperty
import scalafx.beans.property.ObjectProperty

/**
 * @author jforster
 */
class Role (val idRole:Int, val roleName:String){
  
  val _idRole = new ObjectProperty(this, "roleid", idRole)
  val _roleName = new StringProperty(this, "rolename", roleName)
}