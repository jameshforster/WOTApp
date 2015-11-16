package com.qa.wota.gui

import scalafx.application.JFXApp
import java.text.SimpleDateFormat
import com.qa.James.loader.PurchaseOrderLoader
import scalafx.collections.ObservableBuffer
import com.qa.James.entities.PurchaseOrderLine
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.TableColumn
import scalafx.scene.control.TableView
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.Includes._
import com.qa.James.logic.PurchaseOrderLogic
import scalafx.scene.paint.LinearGradient
import scalafx.scene.paint.Stops
import scalafx.scene.paint.Color._
import scalafx.geometry.Insets
import scalafx.stage.Stage
import scalafx.scene.layout.VBox
import scalafx.scene.control.Alert
import scalafx.scene.control.Dialog
import scalafx.scene.control.Alert.AlertType
import com.qa.James.logic.PurchaseOrderLineLogic
import scalafx.scene.control.TextInputDialog
import com.qa.James.entities.PurchaseOrder


/**
 * @author jforster
 * Class to create the display for an individual purchase order
 */
class IndividualPurchaseOrder extends JFXApp{
  var purchaseOrder:PurchaseOrder = null
  var label:Label = null
  var pOLList:ObservableBuffer[PurchaseOrderLine] = null
  
  /**
   * Method to construct the stage for display and load the data
   */
  def initUI(purchaseOrderID:Int) {
    val dF = new SimpleDateFormat("dd/MM/yy")
    val pOLoader = new PurchaseOrderLoader[Int]
    purchaseOrder = pOLoader.queryPurchaseOrders(pOLoader.createQueryPurchaseOrderByID, purchaseOrderID).head
    var datePlaced: String = ""
    var dateExpected:String = ""
    
    try {
        datePlaced = dF.format(purchaseOrder.datePlaced)
      }
      catch{
        case npe:NullPointerException => datePlaced = ""
      }
      
      try {
        dateExpected = dF.format(purchaseOrder.dateExpected)
      }
      catch{
        case npe:NullPointerException => dateExpected = ""
      }
      
      label = new Label{
            text = (  "Order ID:  " + purchaseOrder.idPurchaseOrder + "\n"
                            + "Order Status: " + purchaseOrder.purchaseOrderStatus.statusName + "\n"
                            + "Supplier: " + purchaseOrder.supplier.supplierName + "\n"
                            + "Employee ID: " + purchaseOrder.employee.user.idUser + "\n"
                            + "Date Placed: " + datePlaced + "\n"
                            + "Date Expected: " + dateExpected + "\n")
            style = "-fx-font-size: 16pt"

          }
      
      pOLList = ObservableBuffer[PurchaseOrderLine](purchaseOrder.lines)
      
      
      
       //create table view
      var tV = new TableView[PurchaseOrderLine](pOLList){
        columns ++= List(new TableColumn[PurchaseOrderLine, Int]{
          text = "Item ID"
          cellValueFactory = {_.value.iID}
        },
        new TableColumn[PurchaseOrderLine, String]{
          text = "Item Name"
          cellValueFactory = {_.value.iName}
        },
        new TableColumn[PurchaseOrderLine, Int]{
          text = "Quantity"
          cellValueFactory = {_.value.quant}
        },
        new TableColumn[PurchaseOrderLine, Int]{
          text = "Damaged Units"
          cellValueFactory = {_.value.quantD}
        },
        new TableColumn[PurchaseOrderLine, Float]{
          text = "Item Price"
          cellValueFactory = {_.value.iPrice}
        },
        new TableColumn[PurchaseOrderLine, Float]{
          text = "Subtotal"
          cellValueFactory = {_.value.subtotal}
        })
        columnResizePolicy = TableView.ConstrainedResizePolicy
        prefWidth = 630
      }
      
      //create stage for the GUI
    val stage = new Stage {
      title = "Purchase Order"
      width = 650
      height = 700
      resizable = false
      scene = new Scene {
        content = new BorderPane {
          padding = Insets(10)
          top_=(label)
          
          //create main table display
          center_= (tV)
          prefWidth = 650
          
          bottom_=(new GridPane{
            padding = Insets(10, 10, 10, 250)
            hgap = 10
            //create button to close the stage
            add(new Button("Close"){
              prefWidth = 120
            onAction = handle {
              close
            }
          }, 2, 0)
            //create button to update orders
            add(new Button("Update"){
              prefWidth = 120
              onAction = handle {
               purchaseOrder = pOLoader.queryPurchaseOrders(pOLoader.createQueryPurchaseOrderByID, purchaseOrderID).head
               PurchaseOrderLogic.updatePurchaseOrder(purchaseOrder)
               reload(purchaseOrder.idPurchaseOrder)
              }
            }, 1, 0)
            //create button to record damaged stock of an item
            add(new Button("Report Damage"){
              prefWidth = 120
              onAction = handle {
                try {
                  purchaseOrder = pOLoader.queryPurchaseOrders(pOLoader.createQueryPurchaseOrderByID, purchaseOrderID).head
                  if (purchaseOrder.purchaseOrderStatus.idPurchaseOrderStatus == 3) {
                    if (purchaseOrder.employee.user.idUser == MainGUI.employee.user.idUser){
                      val dialog = new TextInputDialog() {
                        title = "System Input"
                        headerText = "Record Damaged Stock"
                        contentText = "Please enter number of damaged items:"
                      }
                      PurchaseOrderLineLogic.addDamagedStock(tV.getSelectionModel.getSelectedItem, Integer.parseInt((dialog.showAndWait().get)))
                      
                      
                    }
                    else {
                      new Alert(AlertType.Information){
                      title = "System Message"
                      headerText = "Cannot Record Damage"
                      contentText =  "Cannot set damaged stock on a purchase order claimed by employeeID: " + purchaseOrder.employee.user.idUser
                    }.showAndWait()
                   
                    }
                  }
                  else {
                    new Alert(AlertType.Information){
                      title = "System Message"
                      headerText = "Cannot Record Damage"
                      contentText =  "Cannot set damaged stock to a purchase order unless the order is in the state: Arrived!"
                    }.showAndWait()
                    
                  }
                  reload(purchaseOrder.idPurchaseOrder)
                }
                catch{
                  case npe:NullPointerException => new Alert(AlertType.Information){
                    title = "System Message"
                    headerText = "No Item Selected"
                    contentText =  "Please select an item in the table when reporting damaged stock!"
                  }.showAndWait()
                }
              }
            }, 0, 0)
          })
        }
      }
    }
    stage.show()
  }
  
  /**
   * Method to refresh all the data in the GUI
   */
  def reload (pOID:Int){
    val dF = new SimpleDateFormat("dd/MM/yy")
    val pOLoader = new PurchaseOrderLoader[Int]
    purchaseOrder = pOLoader.queryPurchaseOrders(pOLoader.createQueryPurchaseOrderByID, pOID).head
    
    var datePlaced: String = ""
    var dateExpected:String = ""
    
    //Try and format the dates into a string or set to empty if value is null
    try {
        datePlaced = dF.format(purchaseOrder.datePlaced)
      }
      catch{
        case npe:NullPointerException => datePlaced = ""
      }
      
      try {
        dateExpected = dF.format(purchaseOrder.dateExpected)
      }
      catch{
        case npe:NullPointerException => dateExpected = ""
      }
      
      label.text = ("Order ID:  " + purchaseOrder.idPurchaseOrder + "\n"
                            + "Order Status: " + purchaseOrder.purchaseOrderStatus.statusName + "\n"
                            + "Supplier: " + purchaseOrder.supplier.supplierName + "\n"
                            + "Employee ID: " + purchaseOrder.employee.user.idUser + "\n"
                            + "Date Placed: " + datePlaced + "\n"
                            + "Date Expected: " + dateExpected + "\n")
      
      pOLList.clear()
      pOLList.appendAll(purchaseOrder.lines)
  }
}