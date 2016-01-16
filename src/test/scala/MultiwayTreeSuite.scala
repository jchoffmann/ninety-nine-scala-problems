import MTree._
import org.scalatest.FunSuite

class MultiwayTreeSuite extends FunSuite {
  test("70 (not mentioned in problem statements) construct example multi-way tree") {
    MTree('a, List(MTree('f, List(MTree('g))), MTree('c), MTree('b, List(MTree('d), MTree('e)))))
  }

  test("70 (omitted) multi-way tree representation allows only well-formed multi-way trees") {
    // Not possible in Scala
  }

  test("70 count nodes of a multi-way tree") {
    assert(MTree('a, List(MTree('f))).nodeCount === 2)
  }

  test("70 convert multi-way tree to string representation") {
    assert(MTree('a, List(MTree('f, List(MTree('g))), MTree('c), MTree('b, List(MTree('d), MTree('e))))).toString ===
      "afg^^c^bd^e^^^")
  }

  test("70 convert string representation to multi-way tree") {
    assert(fromString("afg^^c^bd^e^^^") ===
      MTree("a", List(MTree("f", List(MTree("g"))), MTree("c"), MTree("b", List(MTree("d"), MTree("e"))))))
  }

  test("71 determine internal path length of a multi-way tree") {
    assert("afg^^c^bd^e^^^".internalPathLength === 9)
  }

  test("72 list all nodes of a multi-way tree in postorder sequence") {
    assert("afg^^c^bd^e^^^".postorder === List('g, 'f, 'c, 'd, 'e, 'b, 'a))
  }

  test("73 convert multi-way tree to lispy-string representation") {
    assert(MTree('a, List(MTree('b, List(MTree('c))))).toLispyString === "(a (b c))")
  }

  test("73 convert lispy-string representation to multi-way tree") {
    assert(fromLispyString("(a (b c))") === MTree("a", List(MTree("b", List(MTree("c"))))))
  }
}
