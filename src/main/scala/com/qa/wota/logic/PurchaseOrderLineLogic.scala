package com.qa.wota.logic

import com.qa.James.entities.PurchaseOrderLine
import com.qa.James.loader.PurchaseOrderLineLoader
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
 * @author jforster
 * Object to manage logic application in regards to purchase order lines
 */
object PurchaseOrderLineLogic {
  
  /**
   * Method to apply damaged stock to a purchase order
   */
  def addDamagedStock(pOL:PurchaseOrderLine, damagedQuantity:Int){
    if (damagedQuantity < pOL.quantity){
      pOL.damagedQuantity = damagedQuantity
      val pOLLoader = new PurchaseOrderLineLoader[Unit]
      pOLLoader.updatePurchaseOrderLine(pOLLoader.createUpdatePurchaseOrderLinesDamagedStock, pOL)
    }
    //If attempting to set damaged quantity higher than total quantity arriving
    else {
      new Alert(AlertType.Information){
                    title = "System Message"
                    headerText = "Cannot Record Damage"
                    contentText =  "Cannot set damaged stock to a value higher than order quantity!"
                  }.showAndWait()
    }
  }
}