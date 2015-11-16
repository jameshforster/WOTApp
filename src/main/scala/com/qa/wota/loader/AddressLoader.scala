package com.qa.wota.loader

import com.qa.wota.entities.Address

/**
 * @author jforster
 */
class AddressLoader [T] {
  
  val collection = "Address"
  
  /**
   * Method to query the MongoDB database using a column name and search value
   * param searchTerm: String containing name of MongoDB column to filter by
   * param t: Generic containing value for filter to search for
   * return: Array[Address] of all matching addresses
   */
  def queryAddress(searchTerm: String, t:T): Array[Address] = {
    val mC = new MongoConnector [T] (collection)
    val result = mC.queryMongo(searchTerm, t)
    var addressList: Array[Address] = null
    for (x <- result) {
      val addressLines: Array[String] = null
      val address = new Address(x.get("AddressID").asInstanceOf[Double].toInt, x.get("CustomerID").asInstanceOf[Double].toInt, addressLines, x.get("City").toString(), x.get("Postcode").toString())
      if (addressList != null) {
        addressList.+:(address)
      }
      else {
        addressList = new Array[Address](1)
        addressList.update(0, address)
      }
    }
    addressList
  }
}