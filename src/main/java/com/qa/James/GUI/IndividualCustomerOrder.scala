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
import com.qa.James.logic.CustomerOrderLogic
import scalafx.geometry.Insets
import scalafx.stage.Stage
import com.qa.James.entities.CustomerOrder

/**
 * @author jforster
 * Class to create the display for an individual customer order
 */
class IndividualCustomerOrder() extends JFXApp {
  var customerOrder:CustomerOrder = null
  var cOLList:ObservableBuffer[CustomerOrderLine] = null
  var label:Label = null
  
  def initUI(customerOrderID:Int) {
      val dF = new SimpleDateFormat("dd/MM/yy")
      val cOLoader = new CustomerOrderLoader[Int]
      customerOrder = cOLoader.queryCustomerOrders(cOLoader.createQueryCustomerOrdersByID, customerOrderID).head
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
      
      label = new Label{
            text = (  "Order ID:  " + customerOrder.idCustomerOrder + "\n"
                            + "Order Status: " + customerOrder.customerOrderStatus.statusName + "\n"
                            + "Customer ID: " + customerOrder.customer.user.idUser + "\n"
                            + "Employee ID: " + customerOrder.employee.user.idUser + "\n"
                            + "Date Placed: " + datePlaced + "\n"
                            + "Date Shipped: " + dateShipped + "\n")
            style = "-fx-font-size: 16pt"
          }
      
      //load customer order lines into observable buffer
      cOLList = ObservableBuffer[CustomerOrderLine](
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
        prefWidth = 630
      }
      
    //create stage for the GUI
    val stage:Stage = new Stage {
      title = "Customer Order"
      width = 650
      height = 700
      resizable = false
      scene = new Scene {
        content = new BorderPane {
          padding = Insets(10)
          top_=(label)
          
          //create main table display
          prefWidth = 650
          center_= (tV)
          
          //create button to return to main GUI
          bottom_=(new GridPane{
            padding = Insets(10, 10, 10, 250)
            hgap = 10
            add(new Button("Close"){
               prefWidth = 120
            onAction = handle {
              close
            }
          }, 2, 0)
            add(new Button("Update"){
               prefWidth = 120
              onAction = handle {
                customerOrder = cOLoader.queryCustomerOrders(cOLoader.createQueryCustomerOrdersByID, customerOrderID).head
                CustomerOrderLogic.updateCustomerOrder(customerOrder)
                reload(customerOrder.idCustomerOrder)
              }
            }, 1, 0)
            add (new Button("Pick Item"){
              prefWidth = 120
            }, 0,0)
            
          })
        }
      }
    }
    stage.show()
  }
  
  def reload(cOID:Int){
    val dF = new SimpleDateFormat("dd/MM/yy")
      val cOLoader = new CustomerOrderLoader[Int]
      customerOrder = cOLoader.queryCustomerOrders(cOLoader.createQueryCustomerOrdersByID, cOID).head
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
      
      label.text = (  "Order ID:  " + customerOrder.idCustomerOrder + "\n"
                            + "Order Status: " + customerOrder.customerOrderStatus.statusName + "\n"
                            + "Customer ID: " + customerOrder.customer.user.idUser + "\n"
                            + "Employee ID: " + customerOrder.employee.user.idUser + "\n"
                            + "Date Placed: " + datePlaced + "\n"
                            + "Date Shipped: " + dateShipped + "\n")
            
      
      cOLList.clear()
      cOLList.appendAll(customerOrder.lines)
  }
}