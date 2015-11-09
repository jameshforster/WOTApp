package com.qa.James.loader

import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.Imports._

/**
 * @author jforster
 */

class MongoConnector [S](coll:String){
  val mongoClient = MongoClient("localhost", 27017)
  
  val db = mongoClient("nbgardens")
  
  val collection = db(coll)
  
  def queryMongo (term:String, value:S) = {
    if (value.isInstanceOf[Unit]) {
      collection.find()
    }
    else {
      val resultTarget = MongoDBObject(term -> value)
      collection.find(resultTarget)
    }
  } 
}