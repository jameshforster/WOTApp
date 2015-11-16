package com.qa.James.loader

import org.scalatest.FlatSpec
import com.qa.James.entities.PurchaseOrderLine

/**
 * @author jforster
 */
class PurchaseOrderLineLoaderTest extends FlatSpec{
  
  createQueryPurchaseOrderLinesByOrderIDTest
  def createQueryPurchaseOrderLinesByOrderIDTest {
    "SQL String" should "be equal to input query" in {
      val pOLLoader = new PurchaseOrderLineLoader[Int]
      assert(pOLLoader.createQueryPurchaseOrderLinesByOrderID(1).equals("SELECT purchaseorderline.* FROM purchaseorderline WHERE purchaseorderline.idPurchaseOrder = 1"))
    }
  }
  
  createUpdatePurchaseOrderLinesDamagedStockTest
  def createUpdatePurchaseOrderLinesDamagedStockTest{
    "SQL Update" should "be equal to input query" in {
      val pOLLoader = new PurchaseOrderLineLoader[Int]
      val pOL = pOLLoader.queryPurchaseOrderLines(pOLLoader.createQueryPurchaseOrderLinesByOrderID, 1).head
      assert(pOLLoader.createUpdatePurchaseOrderLinesDamagedStock(pOL).equals("UPDATE purchaseorderline SET quantityDamaged = 0 WHERE idPurchaseOrder = 1 AND idItem = 1"))
    }
  }
  
  updatePurchaseOrderLineTest
  def updatePurchaseOrderLineTest{
    "The database PurchaseOrderLine" should "be equal to the input one" in {
      val pOLLoader = new PurchaseOrderLineLoader[Int]
      val pOL = pOLLoader.queryPurchaseOrderLines(pOLLoader.createQueryPurchaseOrderLinesByOrderID, 1).head
      pOL.damagedQuantity = 5
      pOLLoader.updatePurchaseOrderLine(pOLLoader.createUpdatePurchaseOrderLinesDamagedStock, pOL)
      assert(pOLLoader.queryPurchaseOrderLines(pOLLoader.createQueryPurchaseOrderLinesByOrderID, 1).head.damagedQuantity.equals(pOL.damagedQuantity))
    }
  }
  
  createPurchaseOrderLineEntities
  def createPurchaseOrderLineEntities {
     "Array[PurchaseOrderLine] " should "not be empty" in {
       val pOLLoader = new PurchaseOrderLineLoader[Int]
       assert(!pOLLoader.createPurchaseOrderLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLLoader.createQueryPurchaseOrderLinesByOrderID(1)), null).isEmpty)
     }
     
     "Array[PurchaseOrderLine] " should "contain PurchaseOrderLines" in {
       val pOLLoader = new PurchaseOrderLineLoader[Int]
       assert(pOLLoader.createPurchaseOrderLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLLoader.createQueryPurchaseOrderLinesByOrderID(1)), null).head.isInstanceOf[PurchaseOrderLine])
     }
     
     "Array[PurchaseOrderLine" should "contain non-null PurchaseOrderLines" in {
       val pOLLoader = new PurchaseOrderLineLoader[Int]
       assert(pOLLoader.createPurchaseOrderLineEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLLoader.createQueryPurchaseOrderLinesByOrderID(1)), null).head != null)
     }
  }
  
  
  queryPurchaseOrderLinesTest
  def queryPurchaseOrderLinesTest {
    
    "FinalArray[PurchaseOrderLine" should "not be empty" in {
      val pOLLoader = new PurchaseOrderLineLoader[Int]
      assert(!pOLLoader.queryPurchaseOrderLines(pOLLoader.createQueryPurchaseOrderLinesByOrderID, 1).isEmpty)
    }
  }
}