package com.qa.wota.gui

import com.qa.wota.entities.Role
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
import com.qa.wota.loader.EmployeeLoader
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.input.KeyEvent

/**
 * @author jforster
 * Object to start up the GUI and land on the login screen
 */
object GUIApp extends JFXApp {
 
  initUI()
  
  /**
   * Method to initialise the GUI and open the login screen
   */
  def initUI() {
    val tField = new TextField
    val pField = new PasswordField {
    }
    
    stage=new PrimaryStage {
      title = "Login"
      resizable = false
      width = 300
      height = 200
      scene = new Scene {
        content = new BorderPane{
          //create labels and input fields in the center of the borderpane
          center_=(new GridPane{
            padding = Insets(20)
            hgap = 20
            vgap = 20
            add(new Label("Enter User:"){}, 0,0)
            add(tField, 1,0)
            add(new Label("Enter Password:"){}, 0,1)
            add(pField, 1,1)
          })
          //create buttons and actions in the bottom of the borderpane
          bottom_=(new GridPane{
            padding = Insets(10,10,10,165)
            hgap = 10
            add(new Button("Login"){
              prefWidth = 50
              onAction = handle {
                var loggedIn:Boolean = false
                val identifier = tField.text.getValue
                val password = pField.text.getValue
                try {
                  val id = Integer.parseInt(identifier)
                  val eLoader = new EmployeeLoader[Int]
                  val employee = eLoader.queryEmployee(eLoader.createQueryEmployeeByID, id).head
                  if (employee.user.userPassword == password){
                    MainGUI.employee = employee
                    MainGUI.initUI
                  }
                  else {
                     new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Login Failed"
                        contentText =  "Incorrect Password Entered!\nPlease try again."
                      }.showAndWait()
                  }
                }
                catch {
                  case nfe:NumberFormatException => {
                    try {
                      if (identifier.length() > 3){
                        val eLoader = new EmployeeLoader[String]
                        val employee = eLoader.queryEmployee(eLoader.createQueryEmployeeByEmail, identifier).head
                        if (employee.user.userPassword == password){
                          MainGUI.employee = employee
                          MainGUI.initUI
                        }
                        else {
                          new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Login Failed"
                        contentText =  "Input password does not match the User\nPlease try again."
                      }.showAndWait()
                        }
                      }
                      else {
                        new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Login Failed"
                        contentText =  "User input is too short!\nYou must enter a User ID or an identifier larger than 3 characters!\nPlease try again."
                      }.showAndWait()
                      }
                    }
                    catch{
                      case npe:NullPointerException => {
                        new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Login Failed"
                        contentText =  "User input does not match any User!\nPlease try again."
                      }.showAndWait()
                      }
                    }
                  }
                  case npe:NullPointerException => {
                    new Alert(AlertType.Information){
                        title = "System Message"
                        headerText = "Login Failed"
                        contentText =  "User ID does not match any User!\nPlease try again."
                      }.showAndWait()
                  }
                }
                
                }
            }, 0,0)
            add(new Button("Quit"){
              prefWidth = 50
              onAction = {
                handle {Platform.exit()}
              }
            }, 1,0)
          })
        }
      }
    }
  }
}