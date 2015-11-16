package com.qa.wota.entities

import java.util.Date

/**
 * @author jforster
 * Class to hold the customer objects
 */
class Customer (val user:User, val dateOfBirth:Date, val credit:Float, val phoneNumber:String, val blackListStrikes:Int){
  
}