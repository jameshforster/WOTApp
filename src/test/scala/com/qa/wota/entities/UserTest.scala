package com.qa.wota.entities

import org.scalatest.FlatSpec

/**
 * @author jforster
 */
class UserTest extends FlatSpec{
  "User" should "initialise with correct values" in {
    val user = new User(1, "password", "Name", "Surname", "name@email.com", false)
    assert(user.idUser == 1)
    assert(user.userPassword.equals("password"))
    assert(user.foreName.equals("Name"))
    assert(user.surname.equals("Surname"))
    assert(user.email.equals("name@email.com"))
    assert(!user.isEmployee)
  }
}