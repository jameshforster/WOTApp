package com.qa.James.GUI

import com.qa.James.loader.PurchaseOrderLoader
import com.qa.James.entities.PurchaseOrder
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableView
import scalafx.scene.control.TableColumn
import scalafx.Includes._
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.TextField
import scalafx.scene.control.ComboBox
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.GridPane
import scalafx.scene.control.Button
import scalafx.scene.control.Label
import scalafx.geometry.Insets
import com.qa.James.loader.CustomerOrderLoader

/**
 * @author jforster
 */
object PurchaseOrderGUI extends BorderPane{
  //Load in purchase order list data
  var pOLoader = new PurchaseOrderLoader[Unit]
  var pOList = ObservableBuffer[PurchaseOrder](
    pOLoader.queryPurchaseOrders(pOLoader.createQueryAllPurchaseOrders, ())    
  )
  
  //create table view
  var tV = new TableView[PurchaseOrder](pOList){
    columns ++= List(
      new TableColumn[PurchaseOrder, Int]{
        text = "Purchase Order ID"
        cellValueFactory = {_.value.pOID}
      },
      new TableColumn[PurchaseOrder, String]{
        text = "Purchase Order Status"
        cellValueFactory = {_.value.pOStatus}
      },
      new TableColumn[PurchaseOrder, Int]{
        text = "Employee ID"
        cellValueFactory = {_.value.eID}
      },
      new TableColumn[PurchaseOrder, String]{
        text = "Date Placed"
        cellValueFactory = {_.value.dPlaced}
      },
      new TableColumn[PurchaseOrder, String] {
        text = "Date Expected"
        cellValueFactory = {_.value.dExpected}
      }
    )
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
      //TODO modify to filter from database not from dummy data
      onAction = handle {pOList.clear()
          filterTerms.value.apply() match {
            case "Order ID" => 
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
      }
    }, 4, 0)
    
    //create button to select an individual order from the highlighted list
    add(new Button{
      text = "Select Order"
      onAction = handle {
        var selected = tV.getSelectionModel.getSelectedItem.idPurchaseOrder
        val iPO = new IndividualPurchaseOrder()
        iPO.initUI(selected)
        //TODO load individual purchase order
      }
    }, 5, 0)
  })
  
}