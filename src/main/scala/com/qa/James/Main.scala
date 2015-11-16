package com.qa.James

import com.qa.James.entities.CustomerOrder
import com.qa.James.entities.Address
import com.qa.James.entities.Employee
import java.util.Date
import com.qa.James.entities.CustomerOrderLine
import com.qa.James.entities.CustomerOrder
import com.qa.James.entities.Customer
import com.qa.James.loader.MongoConnector


object Main {
  def main (args: Array[String]) {
     val mC = new MongoConnector[Unit]("Address")
     val test = (mC.queryMongo("AddressID", 2))
  }
}