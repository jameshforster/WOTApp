package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class PurchaseOrderStatusTest extends FlatSpec{
  "PurchaseOrderStatus" should "initialise with correct values" in {
    val purchaseOrderStatus = new PurchaseOrderStatus(1, "Awaiting")
    assert(purchaseOrderStatus.idPurchaseOrderStatus == 1)
    assert(purchaseOrderStatus.statusName.equals("Awaiting"))
  }
}