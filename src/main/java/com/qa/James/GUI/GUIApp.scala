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



/**
 * @author jforster
 */
object GUIApp extends JFXApp{
  
   val characters = ObservableBuffer[Role](
       new Role (1, "Operative"),
       new Role (2, "Manager"),
       new Role (3, "CEO")
   )
  stage = new PrimaryStage {
    title = "TableView"
    scene = new Scene {
      content = new BorderPane {
        center_=( new TableView[Role](characters) {
          columns ++= List(
            new TableColumn[Role, Int] {
              text = "Role ID"
              cellValueFactory = {_.value._idRole}
            },
            new TableColumn[Role, String] {
              text = "Role Title"
              cellValueFactory = {_.value._roleName}
            }
          )
          columnResizePolicy = TableView.ConstrainedResizePolicy
        }
        )
      }
    }
  }
}