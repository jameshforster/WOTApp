package com.qa.James.GUI

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TabPane
import scalafx.scene.control.Tab
import scalafx.scene.control.Label

/**
 * @author jforster
 */
object MainGUI extends JFXApp {
  def initUI {
    stage = new PrimaryStage {
      title = "Warehouse Order Tracking System"
      resizable = false
      scene = new Scene {
        content = new TabPane {
           tabs_=(Seq(new Tab{
             text = "Customer Orders"
             content = new Label("Hello world")
             closable = false
           },
           new Tab{
             text = "Purchase Orders"
             content = new Label("Purchase Order List")
             closable = false
           }))
        }
      }
    }
  }
}