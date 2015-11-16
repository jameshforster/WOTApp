package com.qa.wota

import com.qa.James.loader.JDBCConnectorTest
import org.scalatest.FlatSpec
import org.scalatest.Suite

/**
 * @author jforster
 */
object TestApp extends Suite{
  def main (args:Array[String]) {
    val f = new JDBCConnectorTest
//    f.makeConnectionSQLTest
  }
}