import Tree._
import org.scalatest.{FunSuite, Matchers}

class TreeSuite extends FunSuite with Matchers {
  test("51 (not mentioned in problem statements) construct empty tree") {
    End
  }

  test("52 (not mentioned in problem statements) construct tree with one root node") {
    Node('a)
  }

  test("53 (not mentioned in problem statements) construct example tree") {
    Node('a, Node('b, Node('d), Node('e)), Node('c, End, Node('f, Node('g), End)))
  }

  test("54 (omitted) tree representation allows only well-formed trees") {
    // Not possible in Scala
  }

  test("55 construct all completely balanced binary trees") {
    completelyBalancedTrees(0, 'x) shouldEqual List(End)
    completelyBalancedTrees(1, 'x) shouldEqual List(Node('x))
    completelyBalancedTrees(2, 'x) should contain theSameElementsAs List(
      Node('x, Node('x), End), Node('x, End, Node('x))
    )
    completelyBalancedTrees(3, 'x) shouldEqual List(Node('x, Node('x), Node('x)))
    completelyBalancedTrees(4, 'x) should contain theSameElementsAs List(
      Node('x, Node('x), Node('x, End, Node('x))),
      Node('x, Node('x), Node('x, Node('x), End)),
      Node('x, Node('x, End, Node('x)), Node('x)),
      Node('x, Node('x, Node('x), End), Node('x)))
  }

  test("56 check whether a given binary tree is symmetric") {
    End.isSymmetric shouldBe true
    Node('x).isSymmetric shouldBe true
    Node('x, Node('y), End).isSymmetric shouldBe false
    Node('a, Node('a1, End, Node('b1)), Node('a2, Node('b2), End)).isSymmetric shouldBe true
    Node('a, Node('a1, Node('b1), End), Node('a2, Node('b2), End)).isSymmetric shouldBe false
  }

  test("57 add a value to a binary search tree") {
    End.addValue(2) shouldEqual Node(2)
    End.addValue(2).addValue(3) shouldEqual Node(2, End, Node(3))
    End.addValue(2).addValue(3).addValue(0) shouldEqual Node(2, Node(0), Node(3))
  }

  test("57 construct a binary search tree from a list of integers") {
    fromList(List(3, 2, 5, 7, 1)) shouldEqual Node(3, Node(2, Node(1), End), Node(5, End, Node(7)))
  }

  test("57 check whether a given binary search tree is symmetric") {
    fromList(List(5, 3, 18, 1, 4, 12, 21)).isSymmetric shouldBe true
    fromList(List(3, 2, 5, 7, 4)).isSymmetric shouldBe false
  }

  test("58 construct all symmetric, completely balanced binary trees") {
    symmetricBalancedTrees(0, 'x) shouldEqual List(End)
    symmetricBalancedTrees(1, 'x) shouldEqual List(Node('x))
    symmetricBalancedTrees(2, 'x) shouldBe empty
    symmetricBalancedTrees(3, 'x) shouldEqual List(Node('x, Node('x), Node('x)))
    symmetricBalancedTrees(4, 'x) shouldBe empty
    symmetricBalancedTrees(5, 'x) should contain theSameElementsAs List(
      Node('x, Node('x, End, Node('x)), Node('x, Node('x), End)),
      Node('x, Node('x, Node('x), End), Node('x, End, Node('x))))
  }

  test("59 height of a binary tree") {
    End.height shouldBe 0
    Node('x).height shouldBe 1
    Node('x, End, Node('x)).height shouldBe 2
    Node('x, Node('x), End).height shouldBe 2
    Node('x, Node('x), Node('x)).height shouldBe 2
    Node('x, Node('x, Node('x), End), Node('x)).height shouldBe 3
  }

  test("59 construct height-balanced binary trees") {
    heightBalancedTrees(0, 'x) shouldEqual List(End)
    heightBalancedTrees(1, 'x) shouldEqual List(Node('x))
    heightBalancedTrees(2, 'x) should contain theSameElementsAs
      List(Node('x, Node('x), End), Node('x, End, Node('x)), Node('x, Node('x), Node('x)))
    heightBalancedTrees(3, 'x) should have size 15
    heightBalancedTrees(4, 'x) should have size 315
    heightBalancedTrees(5, 'x) should have size 108675
  }

  test("60 count nodes of a binary tree") {
    End.nodeCount shouldBe 0
    Node('x).nodeCount shouldBe 1
    Node('x, End, Node('x)).nodeCount shouldBe 2
    Node('x, Node('x), End).nodeCount shouldBe 2
    Node('x, Node('x), Node('x)).nodeCount shouldBe 3
  }

  test("60 minimum number of nodes a height-balanced binary tree can contain for a given height") {
    (0 to 4).map(minHbalNodes) shouldEqual List(0, 1, 2, 4, 7)
  }

  test("60 maximum number of nodes a height-balanced binary tree can contain for a given height") {
    (0 to 4).map(maxHbalNodes) shouldEqual List(0, 1, 3, 7, 15)
  }

  test("60 minimum height a height-balanced binary tree can have given a number of nodes ") {
    (0 to 9).map(minHbalHeight) shouldEqual List(0, 1, 2, 2, 3, 3, 3, 3, 4, 4)
  }

  test("60 maximum height a height-balanced binary tree can have given a number of nodes ") {
    (0 to 9).map(maxHbalHeight) shouldEqual List(0, 1, 2, 2, 3, 3, 3, 4, 4, 4)
  }

  test("60 construct height-balanced binary trees with a given number of nodes") {
    heightBalancedTreesWithNodes(0, 'x) shouldEqual List(End)
    heightBalancedTreesWithNodes(1, 'x) shouldEqual List(Node('x))
    heightBalancedTreesWithNodes(2, 'x) should contain theSameElementsAs
      List(Node('x, Node('x), End), Node('x, End, Node('x)))
    heightBalancedTreesWithNodes(3, 'x) shouldEqual List(Node('x, Node('x), Node('x)))
    heightBalancedTreesWithNodes(4, 'x) should contain theSameElementsAs List(
      Node('x, Node('x), Node('x, End, Node('x))),
      Node('x, Node('x, End, Node('x)), Node('x)),
      Node('x, Node('x), Node('x, Node('x), End)),
      Node('x, Node('x, Node('x), End), Node('x)))
    heightBalancedTreesWithNodes(15, 'x) should have size 1553
  }

  test("61 count leaves of a binary tree") {
    End.leafCount shouldBe 0
    Node('x).leafCount shouldBe 1
    Node('x, End, Node('x)).leafCount shouldBe 1
    Node('x, Node('x), End).leafCount shouldBe 1
    Node('x, Node('x), Node('x)).leafCount shouldBe 2
  }

  test("61 list leaves of a binary tree") {
    End.leafList shouldBe empty
    Node('x).leafList shouldEqual List('x)
    Node('a, Node('b), Node('c, Node('d), Node('e))).leafList should contain theSameElementsAs List('b, 'd, 'e)
  }

  test("62 list internal nodes of a binary tree") {
    End.internalList shouldBe empty
    Node('x).internalList shouldBe empty
    Node('a, Node('b), Node('c, Node('d), Node('e))).internalList should contain theSameElementsAs List('a, 'c)
  }

  test("62 list all nodes of a binary tree at a given level") {
    Node('a, Node('b), Node('c, Node('d), Node('e))).atLevel(0) shouldBe empty
    Node('a, Node('b), Node('c, Node('d), Node('e))).atLevel(1) shouldEqual List('a)
    Node('a, Node('b), Node('c, Node('d), Node('e))).atLevel(2) should contain theSameElementsAs List('b, 'c)
  }

  test("63 construct a complete binary tree") {
    completeBinaryTree(0, 'x) shouldEqual End
    completeBinaryTree(1, 'x) shouldEqual Node('x)
    completeBinaryTree(2, 'x) shouldEqual Node('x, Node('x), End)
    completeBinaryTree(3, 'x) shouldEqual Node('x, Node('x), Node('x))
    completeBinaryTree(4, 'x) shouldEqual Node('x, Node('x, Node('x), End), Node('x))
    completeBinaryTree(5, 'x) shouldEqual Node('x, Node('x, Node('x), Node('x)), Node('x))
    completeBinaryTree(6, 'x) shouldEqual Node('x, Node('x, Node('x), Node('x)), Node('x, Node('x), End))
  }

  test("64 layout a binary tree (1)") {
    End.layoutBinaryTree shouldEqual End
    Node('a).layoutBinaryTree shouldEqual PositionedNode('a, 1, 1)
    Node('a, Node('b, End, Node('c)), Node('d)).layoutBinaryTree shouldEqual
      PositionedNode('a, PositionedNode('b, End, PositionedNode('c, 2, 3), 1, 2), PositionedNode('d, 4, 2), 3, 1)
    fromList(List('n', 'k', 'm', 'c', 'a', 'h', 'g', 'e', 'u', 'p', 's', 'q')).layoutBinaryTree shouldEqual
      PositionedNode('n', PositionedNode('k', PositionedNode('c', PositionedNode('a', 1, 4), PositionedNode('h', PositionedNode('g', PositionedNode('e', 3, 6), End, 4, 5), End, 5, 4), 2, 3), PositionedNode('m', 7, 3), 6, 2), PositionedNode('u', PositionedNode('p', End, PositionedNode('s', PositionedNode('q', 10, 5), End, 11, 4), 9, 3), End, 12, 2), 8, 1)
  }

  test("65 layout a binary tree (2)") {
    End.layoutBinaryTree2 shouldEqual End
    Node('a).layoutBinaryTree2 shouldEqual PositionedNode('a, 1, 1)
    Node('a, Node('b, End, Node('c)), Node('d)).layoutBinaryTree2 shouldEqual
      PositionedNode('a, PositionedNode('b, End, PositionedNode('c, 2, 3), 1, 2), PositionedNode('d, 5, 2), 3, 1)
    fromList(List('n', 'k', 'm', 'c', 'a', 'e', 'd', 'g', 'u', 'p', 'q')).layoutBinaryTree2 shouldEqual
      PositionedNode('n', PositionedNode('k', PositionedNode('c', PositionedNode('a', 1, 4), PositionedNode('e', PositionedNode('d', 4, 5), PositionedNode('g', 6, 5), 5, 4), 3, 3), PositionedNode('m', 11, 3), 7, 2), PositionedNode('u', PositionedNode('p', End, PositionedNode('q', 21, 4), 19, 3), End, 23, 2), 15, 1)
  }

  test("66 layout a binary tree (3)") {
    End.layoutBinaryTree3 shouldEqual End
    Node('a).layoutBinaryTree3 shouldEqual PositionedNode('a, 1, 1)
    Node('a, Node('b, End, Node('c)), Node('d)).layoutBinaryTree3 shouldEqual
      PositionedNode('a, PositionedNode('b, End, PositionedNode('c, 2, 3), 1, 2), PositionedNode('d, 3, 2), 2, 1)
    fromList(List('n', 'k', 'm', 'c', 'a', 'e', 'd', 'g', 'u', 'p', 'q')).layoutBinaryTree3 shouldEqual
      PositionedNode('n', PositionedNode('k', PositionedNode('c', PositionedNode('a', 1, 4), PositionedNode('e', PositionedNode('d', 2, 5), PositionedNode('g', 4, 5), 3, 4), 2, 3), PositionedNode('m', 4, 3), 3, 2), PositionedNode('u', PositionedNode('p', End, PositionedNode('q', 7, 4), 6, 3), End, 7, 2), 5, 1)
  }

  test("67 convert binary tree to string representation") {
    End.toString shouldEqual ""
    Node('a').toString shouldEqual "a"
    Node('a', Node('b', Node('d'), Node('e')), Node('c', End, Node('f', Node('g'), End))).toString shouldEqual
      "a(b(d,e),c(,f(g,)))"
  }

  test("67 converting an illegal string representation to a binary tree should throw an Exception") {
    an[IllegalArgumentException] should be thrownBy fromString("(")
    an[IllegalArgumentException] should be thrownBy fromString("a(")
    an[IllegalArgumentException] should be thrownBy fromString("a(b)")
  }

  test("67 convert string representation to a binary tree") {
    fromString("") shouldEqual End
    fromString("a") shouldEqual Node("a")
    fromString("a(b(d,e),c(,f(g,)))") shouldEqual
      Node("a", Node("b", Node("d"), Node("e")), Node("c", End, Node("f", Node("g"), End)))
  }

  test("68 list all nodes of a binary tree in pre-order sequence") {
    fromString("").preorder shouldEqual List.empty
    fromString("a").preorder shouldEqual List("a")
    fromString("a(b(d,e),c(,f(g,)))").preorder shouldEqual List("a", "b", "d", "e", "c", "f", "g")
  }

  test("68 list all nodes of a binary tree in in-order sequence") {
    fromString("").inorder shouldEqual List.empty
    fromString("a").inorder shouldEqual List("a")
    fromString("a(b(d,e),c(,f(g,)))").inorder shouldEqual List("d", "b", "e", "a", "c", "g", "f")
  }

  test("68 construct a binary tree from pre-order and in-order representation") {
    preInTree(List.empty, List.empty) shouldEqual End
    preInTree(List('a), List('a)) shouldEqual Node('a)
    preInTree(List('a, 'b, 'd, 'e, 'c, 'f, 'g), List('d, 'b, 'e, 'a, 'c, 'g, 'f)) shouldEqual
      Node('a, Node('b, Node('d), Node('e)), Node('c, End, Node('f, Node('g), End)))
  }

  test("69 convert binary tree to dot-string representation") {
    fromString("").toDotString shouldEqual "."
    fromString("a").toDotString shouldEqual "a.."
    fromString("a(b(d,e),c(,f(g,)))").toDotString shouldEqual "abd..e..c.fg..."
  }

  test("69 convert dot-string representation to a binary tree") {
    fromDotString(".") shouldEqual End
    fromDotString("a..") shouldEqual Node("a")
    fromDotString("abd..e..c.fg...") shouldEqual
      Node("a", Node("b", Node("d"), Node("e")), Node("c", End, Node("f", Node("g"), End)))
  }
}
