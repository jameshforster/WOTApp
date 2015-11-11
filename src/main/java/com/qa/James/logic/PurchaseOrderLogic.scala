package com.qa.James.logic

import com.qa.James.entities.PurchaseOrder
import com.qa.James.loader.PurchaseOrderLoader
import com.qa.James.loader.PurchaseOrderStatusLoader
import com.qa.James.GUI.MainGUI


/**
 * @author jforster
 */
object PurchaseOrderLogic {
  def updatePurchaseOrder(pO:PurchaseOrder){
    val pOSLoader = new PurchaseOrderStatusLoader[Int]
    val pOLoader = new PurchaseOrderLoader[Unit]
    pO.purchaseOrderStatus.idPurchaseOrderStatus match {
      case 1 => {
        pO.purchaseOrderStatus = pOSLoader.queryPurchaseOrderStatus(pOSLoader.createQueryPurchaseOrderStatusByID, 2).head
        pO.employee = MainGUI.employee
        pOLoader.updatePurchaseOrders(pOLoader.updatePurchaseOrderByStatus, pO)
      }
      case 2 => {
        //TODO condition of having added damaged stock to system
        pO.purchaseOrderStatus = pOSLoader.queryPurchaseOrderStatus(pOSLoader.createQueryPurchaseOrderStatusByID, 3).head
        pO.employee = MainGUI.employee
        pOLoader.updatePurchaseOrders(pOLoader.updatePurchaseOrderByStatus, pO)
        //TODO send JMS signal
        
        
      }
      case _ => println("Not a valid customer order ID")
    }
  }
}