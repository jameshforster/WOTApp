package com.qa.James.logic

import com.qa.James.entities.PurchaseOrderLine
import com.qa.James.loader.PurchaseOrderLineLoader
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
 * @author jforster
 */
object PurchaseOrderLineLogic {
  def addDamagedStock(pOL:PurchaseOrderLine, damagedQuantity:Int){
    if (damagedQuantity < pOL.quantity){
      pOL.damagedQuantity = damagedQuantity
      val pOLLoader = new PurchaseOrderLineLoader[Unit]
      pOLLoader.updatePurchaseOrderLine(pOLLoader.createUpdatePurchaseOrderLinesDamagedStock, pOL)
    }
    else {
      new Alert(AlertType.Information){
                    title = "System Message"
                    headerText = "Cannot Record Damage"
                    contentText =  "Cannot set damaged stock to a value higher than order quantity!"
                  }.showAndWait()
    }
  }
}