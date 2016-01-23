import MTree._
import org.scalatest.{FunSuite, Matchers}

class MultiwayTreeSuite extends FunSuite with Matchers {
  test("70 (not mentioned in problem statements) construct example multi-way tree") {
    MTree('a, List(MTree('f, List(MTree('g))), MTree('c), MTree('b, List(MTree('d), MTree('e)))))
  }

  test("70 (omitted) multi-way tree representation allows only well-formed multi-way trees") {
    // Not possible in Scala
  }

  test("70 count nodes of a multi-way tree") {
    MTree('a, List(MTree('f))).nodeCount shouldBe 2
  }

  test("70 convert multi-way trFee to string representation") {
    MTree('a, List(MTree('f, List(MTree('g))), MTree('c), MTree('b, List(MTree('d), MTree('e))))).toString shouldEqual
      "afg^^c^bd^e^^^"
  }

  test("70 convert string representation to multi-way tree") {
    fromString("afg^^c^bd^e^^^") shouldEqual
      MTree("a", List(MTree("f", List(MTree("g"))), MTree("c"), MTree("b", List(MTree("d"), MTree("e")))))
  }

  test("71 determine internal path length of a multi-way tree") {
    "afg^^c^bd^e^^^".internalPathLength shouldBe 9
  }

  test("72 list all nodes of a multi-way tree in postorder sequence") {
    "afg^^c^bd^e^^^".postorder shouldEqual List('g, 'f, 'c, 'd, 'e, 'b, 'a)
  }

  test("73 convert multi-way tree to lispy-string representation") {
    MTree('a, List(MTree('b, List(MTree('c))))).toLispyString shouldEqual "(a (b c))"
  }

  test("73 convert lispy-string representation to multi-way tree") {
    fromLispyString("(a (b c))") shouldEqual MTree("a", List(MTree("b", List(MTree("c")))))
  }
}
