package com.qa.James.logic

import com.qa.James.entities.CustomerOrder


object CustomerOrderLogic {
  def updateCustomerOrder(cO:CustomerOrder){
    cO.customerOrderStatus.idCustomerOrderStatus match {
      case 1 => {
        
      }
      case _ => println("Not a valid customer order ID")
    }
  }
}