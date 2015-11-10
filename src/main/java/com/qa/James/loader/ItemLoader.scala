package com.qa.James.loader

import com.qa.James.entities.Item

/**
 * @author jforster
 */
class ItemLoader [T] {
  
  val collection = "Items"
  
  def queryItem(searchTerm:String, t:T): Array[Item] = {
    val mC = new MongoConnector [T] (collection)
    val result = mC.queryMongo(searchTerm, t)
    var itemList: Array[Item] = null
    for (x <- result) {
      val item = new Item(x.get("ItemID").asInstanceOf[Double].toInt, x.get("ItemName").toString(), x.get("ItemStock").asInstanceOf[Double].toInt, x.get("ItemDescription").toString(), x.get("ItemPrice").asInstanceOf[Double].toFloat, x.get("ItemCost").asInstanceOf[Double].toFloat, x.get("Discontinued").asInstanceOf[Boolean], x.get("SalesRate").asInstanceOf[Double].toFloat, x.get("PSalesRate").asInstanceOf[Double].toFloat, x.get("IsPorousWare").asInstanceOf[Boolean])
      if (itemList != null) {
        itemList.+:(item)
      }
      else{
        itemList = new Array[Item](1)
        itemList.update(0, item)
      }
    }
    itemList
  }
}