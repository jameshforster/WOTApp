package com.qa.James.GUI

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import com.qa.James.entities.Role
import scalafx.beans.property.StringProperty
import scalafx.beans.property.IntegerProperty
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.VBox
import scalafx.scene.layout.Priority
import scalafx.scene.control.TableView.ResizeFeatures
import scalafx.scene.layout.AnchorPane
import javafx.scene.control.Button
import scalafx.scene.text.Text

object GUIApp extends JFXApp {
  
   val characters = ObservableBuffer[Role](
       new Role (1, "Operative"),
       new Role (2, "Manager"),
       new Role (3, "CEO")
   )
  stage=new PrimaryStage {
    title = "TableView"
    scene = new Scene {
      content = new AnchorPane{ 
       children=( new TableView[Role](characters) {
          columns ++= List(
            new TableColumn[Role, Int] {
              text = "Role ID"
              cellValueFactory = {_.value._idRole}
              resizable_=(true)
              hgrow_= (Priority.Always)
              vgrow_= (Priority.Always)
            },
            new TableColumn[Role, String] {
              text = "Role Title"
              cellValueFactory = {_.value._roleName}
              resizable_=(true)
              hgrow_= (Priority.Always)
              vgrow_= (Priority.Always)
            }
          )
          columnResizePolicy = TableView.ConstrainedResizePolicy
          resizable_=(true)
          hgrow_= (Priority.Always)
          vgrow_= (Priority.Always)
        }
        )
        resizable_=(true)
        hgrow_= (Priority.Always)
        vgrow_= (Priority.Always)
      }
    }
    
  }
}