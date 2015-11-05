package com.qa.James.GUI

import scalafx.scene.layout.BorderPane
import com.qa.James.entities.CustomerOrder
import com.qa.James.DummyData
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}

/**
 * @author jforster
 * Object to create the pane to display and interact with customer orders to be loaded into the main GUI scene
 */
object CustomerOrderGUI extends BorderPane {
  //Load in customer order list data
  val cOList = ObservableBuffer[CustomerOrder](
      //TODO connect to actual loader and database instead of dummy data
      DummyData.getCustomerOrdersByAll()
      )
      
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
  })
}