import MTree._
import org.scalatest.{FunSuite, Matchers}

class MultiwayTreeSuite extends FunSuite with Matchers {
  test("70 (not mentioned in problem statements) construct example multi-way tree") {
    MTree('a)
    MTree('a, List(MTree('f, List(MTree('g))), MTree('c), MTree('b, List(MTree('d), MTree('e)))))
  }

  test("70 (omitted) multi-way tree representation allows only well-formed multi-way trees") {
    // Not possible in Scala
  }

  test("70 count nodes of a multi-way tree") {
    MTree('a).nodeCount shouldBe 1
    MTree('a, List(MTree('f))).nodeCount shouldBe 2
  }

  test("70 convert multi-way tree to string representation") {
    MTree('a').toString shouldEqual "a^"
    MTree('a', List(MTree('f', List(MTree('g'))), MTree('c'), MTree('b', List(MTree('d'), MTree('e'))))).toString shouldEqual
      "afg^^c^bd^e^^^"
  }

  test("70 converting an illegal string representation to a multi-way tree should throw an Exception") {
    an[IllegalArgumentException] should be thrownBy fromString("")
    an[IllegalArgumentException] should be thrownBy fromString("^")
    an[IllegalArgumentException] should be thrownBy fromString("a")
    an[IllegalArgumentException] should be thrownBy fromString("ab^")
  }

  test("70 convert string representation to a multi-way tree") {
    fromString("a^") shouldEqual MTree("a")
    fromString("afg^^c^bd^e^^^") shouldEqual
      MTree("a", List(MTree("f", List(MTree("g"))), MTree("c"), MTree("b", List(MTree("d"), MTree("e")))))
  }

  test("71 determine internal path length of a multi-way tree") {
    "a^".internalPathLength shouldBe 0
    "afg^^c^bd^e^^^".internalPathLength shouldBe 9
  }

  test("72 list all nodes of a multi-way tree in post-order sequence") {
    "a^".postorder shouldBe List("a")
    "afg^^c^bd^e^^^".postorder shouldEqual List("g", "f", "c", "d", "e", "b", "a")
  }

  test("73 convert multi-way tree to lispy-string representation") {
    "a^".toLispyString shouldEqual "a"
    "ab^^".toLispyString shouldEqual "(a b)"
    "abc^^^".toLispyString shouldEqual "(a (b c))"
    "bd^e^^".toLispyString shouldEqual "(b d e)"
    "afg^^c^bd^e^^^".toLispyString shouldEqual "(a (f g) c (b d e))"
  }

  test("73 converting an illegal lispy-string representation to a multi-way tree should throw an Exception") {
    an[IllegalArgumentException] should be thrownBy fromLispyString("")
    an[IllegalArgumentException] should be thrownBy fromLispyString("(a)")
    an[IllegalArgumentException] should be thrownBy fromLispyString("(a b))")
  }

  test("73 convert lispy-string representation to a multi-way tree") {
    fromLispyString("a") shouldEqual fromString("a^")
    fromLispyString("(a b)") shouldEqual fromString("ab^^")
    fromLispyString("(a (b c))") shouldEqual fromString("abc^^^")
    fromLispyString("(b d e)") shouldEqual fromString("bd^e^^")
    fromLispyString("(a (f g) c (b d e))") shouldEqual fromString("afg^^c^bd^e^^^")
  }
}
