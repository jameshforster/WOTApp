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
  val cOLoader = new CustomerOrderLoader[Unit]
  var cOList = ObservableBuffer[CustomerOrder](
      //TODO connect to actual loader and database instead of dummy data
      cOLoader.queryCustomerOrders(cOLoader.createQueryAllCustomerOrders, ())
      )
      
  //create filter Strings
  var filterList = ObservableBuffer(
    "Order ID", "Order Status", "Date Placed", "Employee ID"    
  )
  
  //create stored variables from interactions with GUI
  var filterTextField = new TextField {
     prefWidth = 150
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
  bottom_=(new GridPane{
    padding = Insets(10)
    hgap = 10
    add(new Label{
    
    text = "Filter:"
  }, 0, 0)
    add(filterTextField, 1, 0)
    add(new ComboBox[String]{
      items = filterList
      prefWidth = 100
    }, 2, 0)
    add(new Button{
      text = "Filter Results"
      onAction = handle {cOList.clear()
        cOList.append(DummyData.getCustomerOrderByID(Integer.parseInt(filterTextField.text.getValue)))}
    }, 3, 0)
    add(new Button{
      text = "Select Order"
      onAction = handle {
        
      }
    }, 4, 0)
  })
}