package com.qa.James.GUI

import com.qa.James.entities.Role
import scalafx.application.JFXApp
import scalafx.Includes._
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.control.TableColumn
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.TableView
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.GridPane
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.layout.Priority
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color
import scalafx.scene.layout.Background
import scalafx.scene.layout.BackgroundFill
import scalafx.scene.layout.CornerRadii
import scalafx.geometry.Insets
import scalafx.event.ActionEvent
import scalafx.application.Platform
import scalafx.scene.control.Label
import scalafx.scene.control.TextField
import scalafx.scene.control.PasswordField
import javafx.geometry.Pos

/**
 * @author jforster
 * Object to start up the GUI and land on the login screen
 */
object GUIApp extends JFXApp {
 
  initUI()
  
  def initUI() {
    stage=new PrimaryStage {
      title = "Login"
      resizable = false
      scene = new Scene {
        content = new BorderPane{
          //create labels and input fields in the center of the borderpane
          center_=(new GridPane{
            padding = Insets(20)
            hgap = 20
            vgap = 20
            add(new Label("Enter Username:"){}, 0,0)
            add(new TextField{}, 1,0)
            add(new Label("Enter Password:"){}, 0,1)
            add(new PasswordField{}, 1,1)
          })
          //create buttons and actions in the bottom of the borderpane
          bottom_=(new GridPane{
            padding = Insets(10,10,10,165)
            hgap = 10
            add(new Button("Login"){
              prefWidth = 50
              onAction = handle {MainGUI.initUI}
            }, 0,0)
            add(new Button("Quit"){
              prefWidth = 50
              onAction = handle {Platform.exit()}
            }, 1,0)
          })
        }
      }
    }
  }
}