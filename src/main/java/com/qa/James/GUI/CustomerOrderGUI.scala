package com.qa.James.GUI

import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TableView
import com.qa.James.entities.CustomerOrder

/**
 * @author jforster
 */
object CustomerOrderGUI extends BorderPane {
  center_=(new TableView[CustomerOrder](cOList){
    
  })
}