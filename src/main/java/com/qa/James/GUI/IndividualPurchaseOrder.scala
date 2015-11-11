package com.qa.James.GUI

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

/**
 * @author jforster
 */
class IndividualPurchaseOrder extends JFXApp{
  def initUI(purchaseOrderID:Int) {
    val dF = new SimpleDateFormat("dd/MM/yy")
    val pOLoader = new PurchaseOrderLoader[Int]
    val purchaseOrder = pOLoader.queryPurchaseOrders(pOLoader.createQueryPurchaseOrderByID, purchaseOrderID).head
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
      
      var pOLList = ObservableBuffer[PurchaseOrderLine](purchaseOrder.lines)
      
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
          text = "Quantity Picked"
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
      }
      
      //create stage for the GUI
    val stage = new PrimaryStage {
      title = "Purchase Order"
      width = 800
      height = 600
      resizable = false
      scene = new Scene {
        content = new BorderPane {
          top_=(new Label{
            text = (  "Order ID:  " + purchaseOrder.idPurchaseOrder + "\n"
                            + "Order Status: " + purchaseOrder.purchaseOrderStatus.statusName + "\n"
                            + "Supplier: " + purchaseOrder.supplier.supplierName + "\n"
                            + "Employee ID: " + purchaseOrder.employee.user.idUser + "\n"
                            + "Date Placed: " + datePlaced + "\n"
                            + "Date Shipped: " + dateExpected + "\n")
          })
          
          //create main table display
          center_= (tV)
          
          //create button to return to main GUI
          bottom_=(new GridPane{
            add(new Button("Back"){
            onAction = handle {
              MainGUI.initUI
            }
          }, 0, 0)
            add(new Button("Update"){
              onAction = handle {
               PurchaseOrderLogic.updatePurchaseOrder(purchaseOrder)
              }
            }, 0, 1)
          })
        }
      }
    }
  }
}