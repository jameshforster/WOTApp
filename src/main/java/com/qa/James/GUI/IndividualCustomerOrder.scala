package com.qa.James.GUI

import scalafx.application.JFXApp
import com.qa.James.loader.CustomerOrderLoader
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.VBox
import scalafx.scene.control.Label
import java.text.SimpleDateFormat
import com.qa.James.entities.CustomerOrderLine
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn
import scalafx.scene.control.TableColumn._
import scalafx.Includes._
import scalafx.scene.control.Button

/**
 * @author jforster
 * Class to create the display for an individual customer order
 */
class IndividualCustomerOrder() extends JFXApp {
  
  
  def initUI(customerOrderID:Int) {
      val dF = new SimpleDateFormat("dd/MM/yy")
      val cOLoader = new CustomerOrderLoader[Int]
      val customerOrder = cOLoader.queryCustomerOrders(cOLoader.createQueryCustomerOrdersByID, customerOrderID).head
      var datePlaced:String = ""
      var dateShipped:String = ""
      
      
      //Try and format the dates into a string or set to empty if value is null
      try {
        datePlaced = dF.format(customerOrder.datePlaced)
      }
      catch{
        case npe:NullPointerException => datePlaced = ""
      }
      
      try {
        dateShipped = dF.format(customerOrder.dateShipped)
      }
      catch{
        case npe:NullPointerException => dateShipped = ""
      }
      
      //load customer order lines into observable buffer
      var cOLList = ObservableBuffer[CustomerOrderLine](
        customerOrder.lines
        )
      //create table view
      var tV = new TableView[CustomerOrderLine](cOLList){
        columns ++= List(new TableColumn[CustomerOrderLine, Int]{
          text = "Item ID"
          cellValueFactory = {_.value.iID}
        },
        new TableColumn[CustomerOrderLine, String]{
          text = "Item Name"
          cellValueFactory = {_.value.iName}
        },
        new TableColumn[CustomerOrderLine, Int]{
          text = "Quantity"
          cellValueFactory = {_.value.quant}
        },
        new TableColumn[CustomerOrderLine, Int]{
          text = "Quantity Picked"
          cellValueFactory = {_.value.quantP}
        },
        new TableColumn[CustomerOrderLine, Float]{
          text = "Item Price"
          cellValueFactory = {_.value.iPrice}
        },
        new TableColumn[CustomerOrderLine, Float]{
          text = "Subtotal"
          cellValueFactory = {_.value.subtotal}
        })
        columnResizePolicy = TableView.ConstrainedResizePolicy
      }
      
    //create stage for the GUI
    val stage = new PrimaryStage {
      title = "Customer Order"
      width = 800
      height = 600
      resizable = false
      scene = new Scene {
        content = new BorderPane {
          top_=(new Label{
            text = (  "Order ID:  " + customerOrder.idCustomerOrder + "\n"
                            + "Order Status: " + customerOrder.customerOrderStatus.statusName + "\n"
                            + "Customer ID: " + customerOrder.customer.user.idUser + "\n"
                            + "Employee ID: " + customerOrder.employee.user.idUser + "\n"
                            + "Date Placed: " + datePlaced + "\n"
                            + "Date Shipped: " + dateShipped + "\n")
          })
          
          //create main table display
          center_= (tV)
          
          //create button to return to main GUI
          bottom_=(new Button("Back"){
            onAction = handle {
              MainGUI.initUI
            }
          })
        }
      }
    }
  }
}