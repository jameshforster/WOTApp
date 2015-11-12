package com.qa.James.logic

import com.qa.James.GUI.MainGUI
import com.qa.James.entities.CustomerOrder
import com.qa.James.loader.CustomerOrderLoader
import com.qa.James.loader.CustomerOrderStatusLoader
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType


object CustomerOrderLogic {
  def updateCustomerOrder(cO:CustomerOrder){
    val cOSLoader = new CustomerOrderStatusLoader[Int]
    val cOLoader = new CustomerOrderLoader[Unit]
    cO.customerOrderStatus.idCustomerOrderStatus match {
      case 1 => {
        cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 2).head
        cO.employee = MainGUI.employee
        cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
        updatedCustomerOrderMessage(cO)
      }
      case 2 => {
        if (cO.employee.user.idUser == MainGUI.employee.user.idUser){
          //TODO condition of having picked all items in the order
          cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 3).head
          cO.employee = MainGUI.employee
          cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
          updatedCustomerOrderMessage(cO)
        }
        else {
           new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Failed"
                        contentText =  "Cannot update this customer order as you are not employee ID: " + cO.employee.user.idUser
                      }.showAndWait()
        }
      }
      case _ => new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Failed"
                        contentText =  "Cannot update the customer order's status any further using this system."
                      }.showAndWait()
    }
  }
  
  def updatedCustomerOrderMessage (customerOrder:CustomerOrder) {
    new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Completed"
                        contentText =  "The customer order has been updated to: " + customerOrder.customerOrderStatus.statusName + "\nBy employee ID: " + customerOrder.employee.user.idUser
                      }.showAndWait()
  }
}