package com.qa.James.loader

import org.scalatest.FlatSpec
import com.qa.James.entities.CustomerOrderStatus

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
    
    "Array" should "produce an empty resultset with an invalid ID" in {
      val cOSLoader = new CustomerOrderStatusLoader[Int]
      assert(cOSLoader.createCustomerOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOSLoader.createQueryCustomerOrderStatusByID(100)), null).isEmpty)
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
  
  def queryCustomerOrderStatusTest {
    
    "Final Array" should "be equal to entity creation array"
    val cOSLoader = new CustomerOrderStatusLoader[Int]
    assert(cOSLoader.queryCustomerOrderStatus(cOSLoader.createQueryCustomerOrderStatusByID, 1).equals(cOSLoader.createCustomerOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, cOSLoader.createQueryCustomerOrderStatusByID(1)), null).length == 1))
  }
  
  
}