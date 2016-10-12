package org.miker.pinkmagistrate

import org.scalatest.FunSuite

class HeadingTest extends FunSuite {
  test("toString") {
    assert(Heading(5, "Foo").toString == "#####Foo\n")
  }
}
