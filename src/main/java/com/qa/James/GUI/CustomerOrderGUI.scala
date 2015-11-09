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

/**
 * @author jforster
 * Object to create the pane to display and interact with customer orders to be loaded into the main GUI scene
 */
object CustomerOrderGUI extends BorderPane {
  //Load in customer order list data
  var cOLoader = new CustomerOrderLoader[Int]
  var cOList = ObservableBuffer[CustomerOrder](
      cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, 1)
      )
      
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
      
  //create tableview of customer orders in the center of the borderpane
  center_=(new TableView[CustomerOrder](cOList){
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
  })
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
      //TODO modify to filter from database not from dummy data
      onAction = handle {cOList.clear()
          filterTerms.value.apply() match {
            case "Order ID" => println("Order ID filter selected")
                try{cOLoader = new CustomerOrderLoader[Int]
                  cOList.clear()
                  cOList.appendAll(cOLoader.queryCustomerOrders(cOLoader.createQueryCustomerOrdersByID, Integer.parseInt(filterTextField.text.getValue)))
                  }
                catch{
                  case nfe:NumberFormatException => println("Invalid Order ID entered, please use an Integer value")
                }
            case "Order Status" => println ("Order status filter selected")
            case "Date Placed" => println ("Date placed filter selected")
            case "Employee ID" => println ("Employee ID filter selected")
            case _ => println("No valid filter type selected")
          }
        }
    }, 3, 0)
    //create a button to reset filters and reload all customer orders
    add(new Button{
      text = "Reset Filters"
      onAction = handle {
        cOLoader = new CustomerOrderLoader[Int]
        cOList.clear()
        cOList.appendAll(cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, 1))
      }
    }, 4, 0)
    //create button to select an individual order from the highlighted list
    add(new Button{
      text = "Select Order"
      onAction = handle {
        //TODO button functionality
      }
    }, 5, 0)
  })
}