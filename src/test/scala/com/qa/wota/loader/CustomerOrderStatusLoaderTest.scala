package com.qa.wota.loader

import org.scalatest.FlatSpec

import com.qa.wota.entities.CustomerOrderStatus
import com.qa.wota.loader.JDBCConnector;

class CustomerOrderStatusLoaderTest extends FlatSpec{
  
  createQueryCustomerOrderStatusByIDTest
  def createQueryCustomerOrderStatusByIDTest{
    "SQL String" should "be equal to string with id = 1" in {
      val cOSLoader = new CustomerOrderStatusLoader [Int]
      assert(cOSLoader.createQueryCustomerOrderStatusByID(1).equals("SELECT customerorderstatus.* FROM customerorderstatus WHERE idCustomerOrderStatus = 1"))
    }
  }
  
  createCustomerOrderStatusEntitiesTest
  def createCustomerOrderStatusEntitiesTest {
    "Array" should "produce one result with a valid ID" in {
      val cOSLoader = new CustomerOrderStatusLoader[Int]
      assert(cOSLoader.createCustomerOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOSLoader.createQueryCustomerOrderStatusByID(1)), null).length == 1)
    }
    
    "Array" should "produce a null resultset with an invalid ID" in {
      val cOSLoader = new CustomerOrderStatusLoader[Int]
      assert(cOSLoader.createCustomerOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOSLoader.createQueryCustomerOrderStatusByID(100)), null) == null)
    }
    
    "Array" should "contain a CustomerOrderStatus"in {
      val cOSLoader = new CustomerOrderStatusLoader[Int]
      assert(cOSLoader.createCustomerOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOSLoader.createQueryCustomerOrderStatusByID(1)), null).head.isInstanceOf[CustomerOrderStatus])
    }
    
    "Array" should "contain non-null CustomerOrderStatuses" in{
      val cOSLoader = new CustomerOrderStatusLoader[Int]
      assert(cOSLoader.createCustomerOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOSLoader.createQueryCustomerOrderStatusByID(1)), null).head != null)
    }
  }
  
  queryCustomerOrderStatusTest
  def queryCustomerOrderStatusTest {
    
    "Final Array" should "be equal to entity creation array" in {
      val cOSLoader = new CustomerOrderStatusLoader[Int]
      assert(cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 1).head.isInstanceOf[CustomerOrderStatus])
    }
  }  
}