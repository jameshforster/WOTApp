package com.qa.James.GUI

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

/**
 * @author jforster
 * ScalaFX App object to hold main GUI and load Panes from other objects
 */
object MainGUI extends JFXApp {
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
               content = new Label("Purchase Order List")
               closable = false
             }))
          })
          bottom_= (new GridPane {
            padding = Insets(10, 10, 10, 180)
            add(new Button {
              text = "Logout"
              onAction = handle {GUIApp.initUI()}
            }, 0, 0)
          })
        }
      }
    }
  }
}