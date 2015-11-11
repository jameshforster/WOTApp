package com.qa.James.logic

import com.qa.James.GUI.MainGUI
import com.qa.James.entities.CustomerOrder
import com.qa.James.loader.CustomerOrderLoader
import com.qa.James.loader.CustomerOrderStatusLoader


object CustomerOrderLogic {
  def updateCustomerOrder(cO:CustomerOrder){
    val cOSLoader = new CustomerOrderStatusLoader[Int]
    val cOLoader = new CustomerOrderLoader[Unit]
    cO.customerOrderStatus.idCustomerOrderStatus match {
      case 1 => {
        cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 2).head
        cO.employee = MainGUI.employee
        cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
      }
      case 2 => {
        //TODO condition of having picked all items in the order
        cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 3).head
        cO.employee = MainGUI.employee
        cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
        //TODO send JMS signal
        
        
      }
      case _ => println("Not a valid customer order ID")
    }
  }
}