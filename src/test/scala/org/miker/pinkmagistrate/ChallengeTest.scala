package org.miker.pinkmagistrate

import org.scalatest.FunSuite

class ChallengeTest extends FunSuite {

  test("empty list should return null tree") {
    val test = List[Heading]()
    assert(Challenge.outline(test) == null)
  }

  test("list must start with heading with weight == 1") {
    val test = List(Heading(2, "bad"))
    assertThrows[IllegalArgumentException] {
      Challenge.outline(test)
    }
  }

  test("basic example") {
    val test = List(
      Heading(1, "All About Birds"),
      Heading(2, "Kinds of Birds"),
      Heading(3, "The Finch"),
      Heading(3, "The Swan"),
      Heading(2, "Habitats"),
      Heading(3, "Wetlands")
    )
    val string =
      """|#All About Birds
         |##Kinds of Birds
         |###The Finch
         |###The Swan
         |##Habitats
         |###Wetlands
         |""".stripMargin
    assert(Challenge.outline(test).toString == string)
  }

  test("skip up two levels") {
    val test = List(
      Heading(1, "All About Birds"),
      Heading(2, "Kinds of Birds"),
      Heading(3, "The Finch"),
      Heading(3, "The Swan"),
      Heading(3, "Video Game"),
      Heading(4, "Angry"),
      Heading(4, "Flappy"),
      Heading(4, "Tiny"),
      Heading(2, "Habitats"),
      Heading(3, "Wetlands")
    )
    val string =
      """|#All About Birds
         |##Kinds of Birds
         |###The Finch
         |###The Swan
         |###Video Game
         |####Angry
         |####Flappy
         |####Tiny
         |##Habitats
         |###Wetlands
         |""".stripMargin
    assert(Challenge.outline(test).toString == string)
  }

  test("middle children") {
    val test = List(
      Heading(1, "All About Birds"),
      Heading(2, "Kinds of Birds"),
      Heading(3, "The Finch"),
      Heading(3, "Video Game"),
      Heading(4, "Angry"),
      Heading(4, "Flappy"),
      Heading(4, "Tiny"),
      Heading(3, "The Swan"),
      Heading(2, "Habitats"),
      Heading(3, "Wetlands")
    )
    val string =
      """|#All About Birds
        |##Kinds of Birds
        |###The Finch
        |###Video Game
        |####Angry
        |####Flappy
        |####Tiny
        |###The Swan
        |##Habitats
        |###Wetlands
        |""".stripMargin
    assert(Challenge.outline(test).toString == string)
  }

  test("skip levels") {
    val test = List(
      Heading(1, "All About Birds"),
      Heading(3, "Kinds of Birds")
    )
    assertThrows[IllegalArgumentException] {
      Challenge.outline(test)
    }
  }
}
