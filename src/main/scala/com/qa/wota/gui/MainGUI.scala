package com.qa.wota.gui

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TabPane
import scalafx.scene.control.Tab
import scalafx.scene.control.Label
import scalafx.scene.layout.GridPane
import scalafx.scene.control.Button
import scalafx.geometry.Insets
import com.qa.wota.entities.Employee
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

/**
 * @author jforster
 * ScalaFX App object to hold main GUI and load Panes from other objects
 */
object MainGUI extends JFXApp {
  //currently logged in employee
  var employee:Employee = null
  
  /**
   * Method to initialise the Main GUI and load the sub-panes into the tabbed pane
   */
  def initUI {
    stage = new PrimaryStage {
      title = "Warehouse Order Tracking System"
      width = 800
      height = 600
      resizable = false
      scene = new Scene {
        content = new BorderPane {
          center_= (new TabPane {
             prefWidth = 800
             tabs_=(Seq(new Tab{
               text = "Customer Orders"
               content = CustomerOrderGUI
               closable = false
             },
             new Tab{
               text = "Purchase Orders"
               content = PurchaseOrderGUI
               closable = false
             }))
          })
          bottom_= (new GridPane {
            padding = Insets(40, 10, 10, 10)
            hgap = 480
            add(new Label {
              text = "Employee ID: " + employee.user.idUser + "\nEmployee Name: " + employee.user.foreName + " " + employee.user.surname + "\n" + employee.user.email
            }, 0, 0)
            add(new Button {
              text = "Logout"
              prefWidth = 150
              prefHeight = 30
              onAction = handle {GUIApp.initUI()}
            }, 1, 0)
          })
        }
      }
    }
    new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Logged In"
                        contentText =  "Welcome " + employee.user.foreName + " " + employee.user.surname + "!"
                      }.showAndWait()
  }
}