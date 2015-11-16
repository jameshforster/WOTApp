package com.qa.wota.loader

import org.scalatest.FlatSpec

import com.qa.wota.entities.CustomerOrderLine
import com.qa.wota.loader.JDBCConnector;

/**
 * @author jforster
 */
class CustomerOrderLineLoaderTest extends FlatSpec{
  
  createQueryCustomerOrderLinesByOrderIDTest
  def createQueryCustomerOrderLinesByOrderIDTest {
    "String" should "match query with input ID" in {
      val cOLLoader = new CustomerOrderLineLoader[Int]
      assert(cOLLoader.createQueryCustomerOrderLinesByOrderID(1).equals("SELECT customerorderline.* FROM customerorderline WHERE customerorderline.idCustomerOrder = 1"))
    }
  }
  
  createCustomerOrderLineEntitiesTest
  def createCustomerOrderLineEntitiesTest{
    "Array " should "not be empty" in {
      val cOLLoader = new CustomerOrderLineLoader[Int]
      assert(!cOLLoader.createCustomerOrderLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOLLoader.createQueryCustomerOrderLinesByOrderID(1)), null).isEmpty)
    }
    
    "Array[CustomerOrderLines]" should "contain CustomerOrderLines" in {
      val cOLLoader = new CustomerOrderLineLoader[Int]
      assert(cOLLoader.createCustomerOrderLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOLLoader.createQueryCustomerOrderLinesByOrderID(1)), null).head.isInstanceOf[CustomerOrderLine])
    }
    
    "Array[CustomerOrderLine]" should "contain non-null CustomerOrderLines" in {
      val cOLLoader = new CustomerOrderLineLoader[Int]
      assert(cOLLoader.createCustomerOrderLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOLLoader.createQueryCustomerOrderLinesByOrderID(1)), null).head != null)
    }
  }
  
  queryCustomerOrderLinesTest
  def queryCustomerOrderLinesTest {
    "Final Array" should "not be empty" in {
      val cOLLoader = new CustomerOrderLineLoader[Int]
      assert(!cOLLoader.queryCustomerOrderLines(cOLLoader.createQueryCustomerOrderLinesByOrderID, 1).isEmpty)
    }
    
    "Final Array" should "contain CustomerOrderLines" in {
      val cOLLoader = new CustomerOrderLineLoader[Int]
      assert(cOLLoader.queryCustomerOrderLines(cOLLoader.createQueryCustomerOrderLinesByOrderID, 1).head.isInstanceOf[CustomerOrderLine])
    }
    
    "Final Array" should "contain non-null CustomerOrderLines" in {
      val cOLLoader = new CustomerOrderLineLoader[Int]
      assert(cOLLoader.queryCustomerOrderLines(cOLLoader.createQueryCustomerOrderLinesByOrderID, 1).head != null)
    }
  }
}