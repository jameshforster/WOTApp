package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class SupplierTest extends FlatSpec{
  "Supplier" should "initialise with correct values" in {
    val supplier = new Supplier(1, "Supplier", null, "supplier@email.com", "01464 425982", 3)
    assert(supplier.idSupplier == 1)
    assert(supplier.supplierName.equals("Supplier"))
    assert(supplier.supplierEmail.equals("supplier@email.com"))
    assert(supplier.supplierTelephone.equals("01464 425982"))
    assert(supplier.deliveryTime == 3)
  }
}