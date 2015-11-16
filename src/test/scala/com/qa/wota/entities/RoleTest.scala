package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class RoleTest extends FlatSpec{
  "Role" should "initialise with correct values" in {
    val role = new Role(1, "Operative")
    assert(role.idRole == 1)
    assert(role.roleName.equals("Operative"))
  }
}