package com.qa.James.loader

import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.Imports._

/**
 * @author jforster
 * Class to create a connection to a mongoDB server and run queries and updates
 */
class MongoConnector [S](coll:String){
  val mongoClient = MongoClient("localhost", 27017)
  
  val db = mongoClient("nbgardens")
  
  val collection = db(coll)
  
  /**
   * Method to query MongoDB and return a collection of results
   * param term: The field name to match to the value
   * param value: The term to search by
   * returns: Filtered Collection of results
   */
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