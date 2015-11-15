package com.qa.James.loader

import org.scalatest.FlatSpec
import com.qa.James.entities.PurchaseOrder

/**
 * @author jforster
 */
class PurchaseOrderLoaderTest extends FlatSpec{
  
  createQueryPurchaseOrderByStatusTest
  def createQueryPurchaseOrderByStatusTest {
    "SQL Query" should "be equal to produced string with status" in {
      val pOLoader = new PurchaseOrderLoader[String]
      assert(pOLoader.createQueryPurchaseOrderByStatus("Status").equals("SELECT purchaseorder.*, purchaseorderstatus.*, employee.*, role.*, supplier.*, user.* FROM purchaseorder LEFT JOIN purchaseorderstatus ON purchaseorder.idPurchaseOrderStatus = purchaseorderstatus.idPurchaseOrderStatus LEFT JOIN employee ON purchaseorder.idEmployee = employee.idEmployee LEFT JOIN role ON role.idRole = employee.role_idRole LEFT JOIN user ON user.idUser = employee.idEmployee LEFT JOIN supplier ON supplier.idSupplier = purchaseorder.idSupplier WHERE purchaseorderstatus.status LIKE '%Status%'"))
    }
  }
  
  createQueryPurchaseOrderByEmployeeIDTest
  def createQueryPurchaseOrderByEmployeeIDTest {
    "SQL Query" should "be equal to produced string with employee ID" in {
      val pOLoader = new PurchaseOrderLoader[Int]
      assert(pOLoader.createQueryPurchaseOrderByEmployeeID(1).equals("SELECT purchaseorder.*, purchaseorderstatus.*, employee.*, role.*, supplier.*, user.* FROM purchaseorder LEFT JOIN purchaseorderstatus ON purchaseorder.idPurchaseOrderStatus = purchaseorderstatus.idPurchaseOrderStatus LEFT JOIN employee ON purchaseorder.idEmployee = employee.idEmployee LEFT JOIN role ON role.idRole = employee.role_idRole LEFT JOIN user ON user.idUser = employee.idEmployee LEFT JOIN supplier ON supplier.idSupplier = purchaseorder.idSupplier WHERE purchaseorder.idEmployee = 1"))
    }
  }
  
  createQueryPurchaseOrderByIDTest
  def createQueryPurchaseOrderByIDTest {
    "SQL Query" should "be equal to produced string with order ID" in {
      val pOLoader = new PurchaseOrderLoader[Int]
      assert(pOLoader.createQueryPurchaseOrderByID(1).equals("SELECT purchaseorder.*, purchaseorderstatus.*, employee.*, role.*, supplier.*, user.* FROM purchaseorder LEFT JOIN purchaseorderstatus ON purchaseorder.idPurchaseOrderStatus = purchaseorderstatus.idPurchaseOrderStatus LEFT JOIN employee ON purchaseorder.idEmployee = employee.idEmployee LEFT JOIN role ON role.idRole = employee.role_idRole LEFT JOIN user ON user.idUser = employee.idEmployee LEFT JOIN supplier ON supplier.idSupplier = purchaseorder.idSupplier WHERE purchaseorder.idPurchaseOrder = 1"))
    }
  }
  
  createQueryAllPurchaseOrdersTest
  def createQueryAllPurchaseOrdersTest {
    "SQL Query" should "be equal to produced string" in {
      val pOLoader = new PurchaseOrderLoader[Unit]
      assert(pOLoader.createQueryAllPurchaseOrders(()).equals("SELECT purchaseorder.*, purchaseorderstatus.*, employee.*, role.*, supplier.*, user.* FROM purchaseorder LEFT JOIN purchaseorderstatus ON purchaseorder.idPurchaseOrderStatus = purchaseorderstatus.idPurchaseOrderStatus LEFT JOIN employee ON purchaseorder.idEmployee = employee.idEmployee LEFT JOIN role ON role.idRole = employee.role_idRole LEFT JOIN user ON user.idUser = employee.idEmployee LEFT JOIN supplier ON supplier.idSupplier = purchaseorder.idSupplier"))
    }
  }
  
  createPurchaseOrderEntitiesTest
  def createPurchaseOrderEntitiesTest {
    "Array" should "not be empty" in {
      val pOLoader = new PurchaseOrderLoader[Unit]
      assert(!pOLoader.createPurchaseOrderEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLoader.createQueryAllPurchaseOrders(())), null).isEmpty)
    }
    
    "Array" should "contain one object when queried by ID" in {
      val pOLoader = new PurchaseOrderLoader[Int]
      assert(pOLoader.createPurchaseOrderEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLoader.createQueryPurchaseOrderByID(1)), null).length == 1)
    }
    
    "Array" should "not be empty when queried by status" in {
      val pOLoader = new PurchaseOrderLoader[String]
      assert(!pOLoader.createPurchaseOrderEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLoader.createQueryPurchaseOrderByStatus("Awaiting")), null).isEmpty)
    }
    
    "Array" should "be null when queried by non existent search term" in {
      val pOLoader = new PurchaseOrderLoader[Int]
      assert(pOLoader.createPurchaseOrderEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLoader.createQueryPurchaseOrderByID(100)), null) == null)
    }
    
    "Array" should "contain PurchaseOrders" in {
      val pOLoader = new PurchaseOrderLoader[Int]
      assert(pOLoader.createPurchaseOrderEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLoader.createQueryPurchaseOrderByID(1)), null).head.isInstanceOf[PurchaseOrder])
    }
    
    "Array" should "contain non null PurchaseOrders" in {
      val pOLoader = new PurchaseOrderLoader [Int]
      assert(pOLoader.createPurchaseOrderEntities(JDBCConnector.executeSQL(JDBCConnector.querySQL, pOLoader.createQueryPurchaseOrderByID(1)), null).head != null)
    }
  }
  
  queryPurchaseOrdersTest
  def queryPurchaseOrdersTest {
    "Final Array" should "not be empty" in {
      val pOLoader = new PurchaseOrderLoader[Unit]
      assert(!pOLoader.queryPurchaseOrders(pOLoader.createQueryAllPurchaseOrders, ()).isEmpty)
    }
  }
}