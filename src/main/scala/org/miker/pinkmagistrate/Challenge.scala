package org.miker.pinkmagistrate

import scala.collection.mutable.ArrayBuffer

case class Heading(weight: Int, text: String) {
  override def toString: String = ("#" * weight) + text + "\n"
}

case class Node[T](heading: T, children: ArrayBuffer[Node[T]]) {
  /**
    * print out the tree represented by this node, in depth-first left-first order
    */
  override def toString: String = {
    heading + children.map(n => n.toString).mkString
  }
}

object Challenge {

  /**
    * convert a list of headings that is a depth-first, left-first traversal of a tree into the original tree
    */
  def outline(list: List[Heading]): Node[Heading] = {
    list.headOption.foreach(h => {
      if (h.weight != 1) throw new IllegalArgumentException("input list must start with a heading with weight 1")
    })

    // track our current ancestry
    val ancestors = ArrayBuffer[Node[Heading]]()

    list.foreach(h => {
      if (h.weight > ancestors.length + 1) {
        // invalid tree because there are skipped levels
        throw new IllegalArgumentException("input list is missing connecting levels")
      }

      if (h.weight <= ancestors.length) {
        // second-or-later child or
        // skipped back some number of levels

        // trim ancestors back to just our parents
        ancestors.trimEnd(ancestors.length - h.weight + 1)
      }

      val node = Node[Heading](h, ArrayBuffer[Node[Heading]]())

      // add ourselves to ancestor list (in case we have children)
      ancestors += node

      // add ourselves to children of our parent
      ancestors.lift(h.weight - 2).map(h => h.children += node)
    })

    ancestors.headOption.orNull
  }

  def main(args: Array[String]): Unit = {
    val example = List(
      Heading(1, "All About Birds"),
      Heading(2, "Kinds of Birds"),
      Heading(3, "The Finch"),
      Heading(3, "The Swan"),
      Heading(2, "Habitats"),
      Heading(3, "Wetlands")
    )

    println(outline(example))
  }
}
