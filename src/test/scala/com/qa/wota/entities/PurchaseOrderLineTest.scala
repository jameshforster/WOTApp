package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class PurchaseOrderLineTest extends FlatSpec{
  "PurchaseOrderLineTest" should "initialise with correct values" in {
    val purchaseOrderLine = new PurchaseOrderLine(1, null, 10, 4) 
    assert(purchaseOrderLine.idPurchaseOrder == 1)
    assert(purchaseOrderLine.quantity == 10)
    assert(purchaseOrderLine.damagedQuantity == 4)
  }
}