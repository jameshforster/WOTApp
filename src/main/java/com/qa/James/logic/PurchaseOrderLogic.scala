package com.qa.James.logic

import com.qa.James.entities.PurchaseOrder
import com.qa.James.loader.PurchaseOrderLoader
import com.qa.James.loader.PurchaseOrderStatusLoader
import com.qa.James.GUI.MainGUI
import com.qa.James.JMS.Sender
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType


/**
 * @author jforster
 */
object PurchaseOrderLogic {
  def updatePurchaseOrder(pO:PurchaseOrder){
    val pOSLoader = new PurchaseOrderStatusLoader[Int]
    val pOLoader = new PurchaseOrderLoader[Unit]
    pO.purchaseOrderStatus.idPurchaseOrderStatus match {
      case 2 => {
        pO.purchaseOrderStatus = pOSLoader.queryPurchaseOrderStatus(pOSLoader.createQueryPurchaseOrderStatusByID, 2).head
        pO.employee = MainGUI.employee
        pOLoader.updatePurchaseOrders(pOLoader.updatePurchaseOrderByStatus, pO)
        
        //send JMS signal to backend to report arrival
        val sender = new Sender
        val mPurchaseOrder = convertPurchaseOrderToJava(pO)
        val mC = new entities.MessageContent(mPurchaseOrder, "receivePurchaseOrder")
        println("Sending Message")
        sender.sendMessage(mC)
        
        //notify user of confirmation
        updatedPurchaseOrderMessage(pO)
      }
      case 3 => {
        if (pO.employee.user.idUser == MainGUI.employee.user.idUser){
          pO.purchaseOrderStatus = pOSLoader.queryPurchaseOrderStatus(pOSLoader.createQueryPurchaseOrderStatusByID, 1).head
          pO.employee = MainGUI.employee
          pOLoader.updatePurchaseOrders(pOLoader.updatePurchaseOrderByStatus, pO)
          
          //send JMS signal
          val sender = new Sender
          val mPurchaseOrder = convertPurchaseOrderToJava(pO)
          val mC = new entities.MessageContent(mPurchaseOrder, "completePurchaseOrder")
          println("Sending Message")
          sender.sendMessage(mC)
          
          //notify user of confirmation
          updatedPurchaseOrderMessage(pO)
        }
        else {
          new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Failed"
                        contentText =  "Cannot update the purchase order as it has been claimed by employee ID: " + pO.employee.user.idUser
                      }.showAndWait()
        }
      }
      case _ => new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Failed"
                        contentText =  "Cannot update the purchase order from the current status using this system.\nPlease contact the appropriate department to update."
                      }.showAndWait()
    }
  }
  def updatedPurchaseOrderMessage (purchaseOrder:PurchaseOrder) {
    new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Completed"
                        contentText =  "The customer order has been updated to: " + purchaseOrder.purchaseOrderStatus.statusName + "\nBy employee ID: " + purchaseOrder.employee.user.idUser
                      }.showAndWait()
  }
  
  def convertPurchaseOrderToJava(pO:PurchaseOrder):entities.PurchaseOrder ={
    val mUser = new entities.User(pO.employee.user.userPassword, pO.employee.user.foreName, pO.employee.user.surname, pO.employee.user.email, pO.employee.user.isEmployee)
        mUser.setUserID(pO.idPurchaseOrder)
        val mRole = new entities.Role(pO.employee.role.roleName)
        mRole.setID(pO.employee.role.idRole)
        val mEmployee = new entities.Employee(mUser, mRole)
        val mPurchaseOrderStatus = new entities.PurchaseOrderStatus(pO.purchaseOrderStatus.statusName)
        mPurchaseOrderStatus.setPurchOrderID(pO.purchaseOrderStatus.idPurchaseOrderStatus)
        val mSupplier = new entities.Supplier(pO.supplier.supplierName, pO.supplier.supplierAddress.idAddress)
        mSupplier.setSupplierID(pO.supplier.idSupplier)
        mSupplier.setEmail(pO.supplier.supplierEmail)
        mSupplier.setTelephone(pO.supplier.supplierTelephone)
        mSupplier.setAverageDeliveryTime(pO.supplier.deliveryTime)
        val mPurchaseOrder = new entities.PurchaseOrder(mPurchaseOrderStatus, mSupplier)
        mPurchaseOrder.setIDPurchaseOrder(pO.idPurchaseOrder)
        mPurchaseOrder.setDateExpected(pO.dateExpected)
        mPurchaseOrder.setDatePlaced(pO.datePlaced)
        mPurchaseOrder.setEmployee(mEmployee)
        mPurchaseOrder
  }
}