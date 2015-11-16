package com.qa.wota.loader

import org.scalatest.FlatSpec

import com.qa.wota.entities.PurchaseOrderStatus
import com.qa.wota.loader.JDBCConnector;

/**
 * @author jforster
 */
class PurchaseOrderStatusLoaderTest extends FlatSpec{
  
  createQueryPurchaseOrderStatusByIDTest
  def createQueryPurchaseOrderStatusByIDTest {
    "SQL Query" should "be equal to produced string with ID" in {
      val pOSLoader = new PurchaseOrderStatusLoader[Int]
      assert(pOSLoader.createQueryPurchaseOrderStatusByID(1).equals("SELECT purchaseorderstatus.* FROM purchaseorderstatus WHERE idPurchaseOrderStatus = 1"))
    }
  }
  
  createPurchaseOrderStatusEntitiesTest
  def createPurchaseOrderStatusEntitiesTest {
    "Array" should "not be empty" in {
      val pOSLoader = new PurchaseOrderStatusLoader[Int]
      assert(!pOSLoader.createPurchaseOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOSLoader.createQueryPurchaseOrderStatusByID(1)), null).isEmpty)
    }
    
    "Array" should "contain one object when queried by ID" in {
      val pOSLoader = new PurchaseOrderStatusLoader[Int]
      assert(pOSLoader.createPurchaseOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOSLoader.createQueryPurchaseOrderStatusByID(1)), null).length == 1)
    }
    
    "Array" should "equal null when an invalid query ID is made" in {
      val pOSLoader = new PurchaseOrderStatusLoader[Int]
      assert(pOSLoader.createPurchaseOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOSLoader.createQueryPurchaseOrderStatusByID(100)), null) == null)
    }
    
    "Array" should "contain PurchaseOrderStatus Objects" in {
       val pOSLoader = new PurchaseOrderStatusLoader[Int]
       assert(pOSLoader.createPurchaseOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOSLoader.createQueryPurchaseOrderStatusByID(1)), null).head.isInstanceOf[PurchaseOrderStatus])
    }
    
    "Array" should "contain non-null PurchaseOrderStatus Objects" in {
      val pOSLoader = new PurchaseOrderStatusLoader[Int]
      assert(pOSLoader.createPurchaseOrderStatusEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOSLoader.createQueryPurchaseOrderStatusByID(1)), null).head != null)
      
    }
  }
  
  queryPurchaseOrderStatusTest
  def queryPurchaseOrderStatusTest {
    "Final Array" should "not be empty" in {
      val pOSLoader = new PurchaseOrderStatusLoader[Int]
      assert(!pOSLoader.queryPurchaseOrderStatus(pOSLoader.createQueryPurchaseOrderStatusByID, 1).isEmpty)
      
    }
  }
}