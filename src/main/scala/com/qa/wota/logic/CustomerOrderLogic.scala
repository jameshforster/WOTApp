package com.qa.wota.logic

import com.qa.wota.gui.MainGUI
import com.qa.wota.entities.CustomerOrder
import com.qa.wota.loader.CustomerOrderLoader
import com.qa.wota.loader.CustomerOrderStatusLoader
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import com.qa.wota.gui.CustomerOrderGUI

/**
 * @author jforster
 * Object to manage logic application in regards to customer orders
 */
object CustomerOrderLogic {
  
  /**
   * Method to update customer orders from current state and check whether operation is permitted
   */
  def updateCustomerOrder(cO:CustomerOrder){
    val cOSLoader = new CustomerOrderStatusLoader[Int]
    val cOLoader = new CustomerOrderLoader[Unit]
    cO.customerOrderStatus.idCustomerOrderStatus match {
      //order status is "Placed"
      case 1 => {
        cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 2).head
        cO.employee = MainGUI.employee
        cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
        updatedCustomerOrderMessage(cO)
        CustomerOrderGUI.cOList.clear()
        CustomerOrderGUI.cOList.appendAll(cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, ()))
      }
      //order status is "Picking"
      case 2 => {
        if (cO.employee.user.idUser == MainGUI.employee.user.idUser){
          //TODO condition of having picked all items in the order
          cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 3).head
          cO.employee = MainGUI.employee
          cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
          updatedCustomerOrderMessage(cO)
          CustomerOrderGUI.cOList.clear()
          CustomerOrderGUI.cOList.appendAll(cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, ()))
        }
        else {
           //customer order is already being handled by another employee
           new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Failed"
                        contentText =  "Cannot update this customer order as it has been claimed by employee ID: " + cO.employee.user.idUser
                      }.showAndWait()
        }
      }
      //order status is "Picked"
      case 3 => {
        cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 4).head
          cO.employee = MainGUI.employee
          cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
          updatedCustomerOrderMessage(cO)
          CustomerOrderGUI.cOList.clear()
          CustomerOrderGUI.cOList.appendAll(cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, ()))
      }
      //order status is "Packed"
      case 4 => {
        cO.customerOrderStatus = cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 5).head
          cO.employee = MainGUI.employee
          cOLoader.updateCustomerOrders(cOLoader.updateCustomerOrderByStatus, cO)
          updatedCustomerOrderMessage(cO)
          CustomerOrderGUI.cOList.clear()
          CustomerOrderGUI.cOList.appendAll(cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, ()))
      }
      //customer order cannot be updated from this state by the WOTApp
      case _ => new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Failed"
                        contentText =  "Cannot update the purchase order from the current status using this system.\nPlease contact the appropriate department to update."
                      }.showAndWait()
    }
  }
  
  //customer order successfully updated
  def updatedCustomerOrderMessage (customerOrder:CustomerOrder) {
    new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Update Completed"
                        contentText =  "The customer order has been updated to: " + customerOrder.customerOrderStatus.statusName + "\nBy employee ID: " + customerOrder.employee.user.idUser
                      }.showAndWait()
  }
}