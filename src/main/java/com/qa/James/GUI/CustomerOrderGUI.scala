package com.qa.James.GUI

import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import com.qa.James.entities.CustomerOrder
import com.qa.James.DummyData
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.control.TextField
import scalafx.scene.control.Label
import scalafx.scene.layout.GridPane
import scalafx.scene.control.ComboBox
import scalafx.scene.control.Button
import scalafx.geometry.Insets
import com.qa.James.loader.CustomerOrderLoader
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
 * @author jforster
 * Object to create the pane to display and interact with customer orders to be loaded into the main GUI scene
 */
object CustomerOrderGUI extends BorderPane {
  //Load in customer order list data
  var cOLoader = new CustomerOrderLoader[Unit]
  var cOList = ObservableBuffer[CustomerOrder](
      cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, ())
      )
      
  //create table view
  var tV = new TableView[CustomerOrder](cOList){
    columns ++= List(new TableColumn[CustomerOrder, Int]{
      text = "Customer Order ID"
      cellValueFactory = {_.value.cOID}
    },
    new TableColumn[CustomerOrder, String]{
      text = "Customer Order Status"
      cellValueFactory = {_.value.cOStatus}
    },
    new TableColumn[CustomerOrder, Int]{
      text = "Employee ID"
      cellValueFactory = {_.value.eID}
    },
    new TableColumn[CustomerOrder, String]{
      text = "Date Placed"
      cellValueFactory = {_.value.dPlaced}
    },
    new TableColumn[CustomerOrder, String]{
      text = "Date Shipped"
      cellValueFactory = {_.value.dShipped}
    })
    columnResizePolicy = TableView.ConstrainedResizePolicy
  }
      
  //create filter Strings
  val filterList = ObservableBuffer(
    "Order ID", "Order Status", "Date Placed", "Employee ID"    
  )
  
  //create stored variables from interactions with GUI
  val filterTextField = new TextField {
     prefWidth = 150
  }
  
  //create combobox to select filter type
  val filterTerms = new ComboBox[String] {
    items = filterList
    prefWidth = 120
  }
      
  //add tableview of customer orders in the center of the borderpane
  center_=(tV)
  
  //create interface for manipulating customer order list
  bottom_=(new GridPane{
    padding = Insets(10)
    hgap = 10
    add(new Label{
    
    text = "Filter:"
  }, 0, 0)
    add(filterTextField, 1, 0)
    
    //add combobox to select filter type
    add(filterTerms, 2, 0)
    
    //create button to update results based on a search function
    add(new Button{
      text = "Filter Results"
      onAction = handle {cOList.clear()
          filterTerms.value.apply() match {
            case "Order ID" => try{val cOLoader2 = new CustomerOrderLoader[Int]
                  cOList.clear()
                  cOList.appendAll(cOLoader2.queryCustomerOrders(cOLoader2.createQueryCustomerOrdersByID, Integer.parseInt(filterTextField.text.getValue)))
                  }
                catch{
                  case nfe:NumberFormatException => new Alert(AlertType.Information){
                    title = "System Message"
                    headerText = "Invalid Filter Term"
                    contentText =  "Invalid order ID inputted!"
                  }.showAndWait()
                }
            case "Order Status" => println ("Order status filter selected")
            case "Date Placed" => println ("Date placed filter selected")
            case "Employee ID" => println ("Employee ID filter selected")
            case _ => new Alert(AlertType.Information){
                    title = "System Message"
                    headerText = "Invalid Filter Term"
                    contentText =  "No filter term selected!"
                  }.showAndWait()
          }
        }
    }, 3, 0)
    
    //create a button to reset filters and reload all customer orders
    add(new Button{
      text = "Reset Filters"
      onAction = handle {
        cOList.clear()
        cOList.appendAll(cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, ()))
      }
    }, 4, 0)
    
    //create button to select an individual order from the highlighted list
    add(new Button{
      text = "Select Order"
      onAction = handle {
        try {
          var selected = tV.getSelectionModel.getSelectedItem.idCustomerOrder
          val iCO = new IndividualCustomerOrder()
          iCO.initUI(selected)
        }
        catch {
          case npe:NullPointerException => new Alert(AlertType.Information){
                    title = "System Message"
                    headerText = "No Order Selected"
                    contentText =  "Please select an order in the table before trying to view an order!"
                  }.showAndWait()
        }
      }
    }, 5, 0)
  })
}