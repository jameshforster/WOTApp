package com.qa.wota.entities

import org.scalatest.FlatSpec
import java.text.SimpleDateFormat

/**
 * @author jforster
 */
class PurchaseOrderTest extends FlatSpec{
  "PurchaseOrder" should "initialise with correct values" in {
    val df = new SimpleDateFormat("dd/MM/yy")
    val purchaseOrder = new PurchaseOrder(1, null, null, null, null, df.parse("15/10/15"), df.parse("14/10/14"))
    assert(purchaseOrder.idPurchaseOrder == 1)
    assert(df.format(purchaseOrder.dateExpected).equals("15/10/15"))
    assert(df.format(purchaseOrder.datePlaced).equals("14/10/14"))
  }
}