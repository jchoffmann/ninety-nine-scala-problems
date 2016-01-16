import Tree._
import org.scalatest.FunSuite

class TreeSuite extends FunSuite {
  test("51 (not mentioned in problem statements) construct empty tree") {
    End
  }

  test("52 (not mentioned in problem statements) construct tree consisting of root node only") {
    Node('a)
  }

  test("53 (not mentioned in problem statements) construct example tree") {
    Node('a, Node('b, Node('d), Node('e)), Node('c, End, Node('f, Node('g), End)))
  }

  test("54 (omitted) tree representation allows only well-formed trees") {
    // Not possible in Scala
  }

  test("55 construct all completely balanced binary trees") {
    assert(cBalanced(3, 'x) == List(Node('x, Node('x), Node('x))))
    assert(cBalanced(4, 'x) == List(
      Node('x, Node('x), Node('x, End, Node('x))),
      Node('x, Node('x), Node('x, Node('x), End)),
      Node('x, Node('x, End, Node('x)), Node('x)),
      Node('x, Node('x, Node('x), End), Node('x))))
  }

  test("56 check whether a given binary tree is symmetric") {
    assert(End.isSymmetric)
    assert(Node('x).isSymmetric)
    assert(!Node('x, Node('y), End).isSymmetric)
    assert(Node('a, Node('a1, End, Node('b1)), Node('a2, Node('b2), End)).isSymmetric)
    assert(!Node('a, Node('a1, Node('b1), End), Node('a2, Node('b2), End)).isSymmetric)
  }

  test("57 add a value to a binary search tree") {
    assert(End.addValue(2) === Node(2))
    assert(End.addValue(2).addValue(3) === Node(2, Node(3)))
    assert(End.addValue(2).addValue(3).addValue(0) === Node(2, Node(0), Node(3)))
  }

  test("57 construct a binary search tree from a list of integers") {
    assert(fromList(List(3, 2, 5, 7, 1)) === Node(3, Node(2, Node(1), End), Node(5, End, Node(7))))
    assert(fromList(List(5, 3, 18, 1, 4, 12, 21)).isSymmetric)
    assert(!fromList(List(3, 2, 5, 7, 4)).isSymmetric)
  }

  test("58 construct all symmetric, completely balanced binary trees") {
    assert(symmetricBalancedTrees(5, 'x) === List(
      Node('x, Node('x, End, Node('x)), Node('x, Node('x), End)),
      Node('x, Node('x, Node('x), End), Node('x, End, Node('x)))))
  }

  test("59 construct height-balanced binary trees") {
    assert(hBalanced(2, 'x).size === 3)
    assert(hBalanced(3, 'x).size === 15)
    assert(hBalanced(4, 'x).size === 315)
    assert(hBalanced(5, 'x).size === 108675)
  }

  test("60 minimum number of nodes of a height-balanced binary tree") {
    assert(minHbalNodes(3) === 4)
    assert(minHbalNodes(4) === 7)
    assert(minHbalNodes(5) === 12)
  }

  test("60 maximum height of a height-balanced binary tree") {
    assert(maxHbalHeight(4) === 3)
  }

  test("60 construct height-balanced binary trees with a given number of nodes") {
    assert(hbalTreesWithNodes(4, 'x) === List(
      Node('x, Node('x), Node('x, End, Node('x))),
      Node('x, Node('x, End, Node('x)), Node('x)),
      Node('x, Node('x), Node('x, Node('x), End)),
      Node('x, Node('x, Node('x), End), Node('x))))
    assert(hbalTreesWithNodes(15, 'x).size === 1553)
  }

  test("61 count leaves of a binary tree") {
    assert(Node('x, Node('x), End).leafCount === 1)
  }

  test("62 list leaves of a binary tree") {
    assert(Node('a, Node('b), Node('c, Node('d), Node('e))).leafList === List('b, 'd, 'e))
  }

  test("62 list internal nodes of a binary tree") {
    assert(Node('a, Node('b), Node('c, Node('d), Node('e))).leafList === List('b, 'd, 'e))
  }

  test("62 list all nodes of a binary tree at a given level") {
    assert(Node('a, Node('b), Node('c, Node('d), Node('e))).atLevel(2) === List('b, 'c))
  }

  test("63 construct a complete binary tree") {
    assert(completeBinaryTree(6, 'x) === Node('x, Node('x, Node('x), Node('x)), Node('x, Node('x), End)))
  }

  test("64 layout a binary tree (1)") {
    assert(Node('a, Node('b, End, Node('c)), Node('d)).layoutBinaryTree ===
      PositionedNode('a, PositionedNode('b, End, PositionedNode('c, 2, 3), 1, 2), PositionedNode('d, 4, 2), 3, 1))
    assert(fromList(List('n, 'k, 'm, 'c, 'a, 'h, 'g, 'e, 'u, 'p, 's, 'q)) ===
      PositionedNode('n, PositionedNode('k, PositionedNode('c, PositionedNode('a, 1, 5), PositionedNode('h, PositionedNode('g, PositionedNode('e, 3, 6), End, 4, 5), End, 5, 4), 2, 3), PositionedNode('m, 7, 3), 6, 2), PositionedNode('u, PositionedNode('p, End, PositionedNode('s, PositionedNode('q, 10, 5), End, 11, 4), 9, 3), End, 12, 2), 8, 1))
  }

  test("65 layout a binary tree (2)") {
    assert(Node('a, Node('b, End, Node('c)), Node('d)).layoutBinaryTree2 ===
      PositionedNode('a, PositionedNode('b, End, PositionedNode('c, 2, 3), 1, 2), PositionedNode('d, 5, 2), 3, 1))
    assert(fromList(List('n, 'k, 'm, 'c, 'a, 'e, 'd, 'g, 'u, 'p, 'q)) ===
      PositionedNode('n, PositionedNode('k, PositionedNode('c, PositionedNode('a, 1, 4), PositionedNode('e, PositionedNode('d, 4, 5), PositionedNode('g, 6, 5), 5, 4), 3, 3), PositionedNode('m, 11, 3), 6, 2), PositionedNode('u, PositionedNode('p, End, PositionedNode('q, 21, 4), 19, 3), End, 23, 2), 15, 1))
  }

  test("66 layout a binary tree (3)") {
    assert(Node('a, Node('b, End, Node('c)), Node('d)).layoutBinaryTree3 ===
      PositionedNode('a, PositionedNode('b, End, PositionedNode('c, 2, 3), 1, 2), PositionedNode('d, 3, 2), 2, 1))
    assert(fromList(List('n, 'k, 'm, 'c, 'a, 'e, 'd, 'g, 'u, 'p, 'q)) ===
      PositionedNode('n, PositionedNode('k, PositionedNode('c, PositionedNode('a, 1, 4), PositionedNode('e, PositionedNode('d, 2, 5), PositionedNode('g, 4, 5), 3, 4), 2, 3), PositionedNode('m, 4, 3), 3, 2), PositionedNode('u, PositionedNode('p, End, PositionedNode('q, 7, 4), 6, 3), End, 7, 2), 5, 1))
  }

  test("67 convert binary tree to string representation") {
    assert(Node('a, Node('b, Node('d), Node('e)), Node('c, End, Node('f, Node('g), End))).toString ===
      "a(b(d,e),c(,f(g,)))")
  }

  test("67 convert string representation to binary tree") {
    assert(fromString("a(b(d,e),c(,f(g,)))") ===
      Node("a", Node("b", Node("d"), Node("e")), Node("c", End, Node("f", Node("g"), End))))
  }

  test("68 list all nodes of a binary tree in preorder sequence") {
    assert(fromString("a(b(d,e),c(,f(g,)))").preorder === List("a", "b", "d", "e", "c", "f", "g"))
  }

  test("68 list all nodes of a binary tree in inorder sequence") {
    assert(fromString("a(b(d,e),c(,f(g,)))").inorder === List("d", "b", "e", "a", "c", "g", "f"))
  }

  test("68 construct a binary tree from preorder and inorder representation") {
    assert(preInTree(List('a, 'b, 'd, 'e, 'c, 'f, 'g), List('d, 'b, 'e, 'a, 'c, 'g, 'f)) ===
      Node('a, Node('b, Node('d), Node('e)), Node('c, End, Node('f, Node('g), End))))
  }

  test("69 convert binary tree to dot-string representation") {
    assert(fromString("a(b(d,e),c(,f(g,)))").toDotString === "abd..e..c.fg...")
  }

  test("69 convert dot-string representation to binary tree") {
    assert(fromDotString("abd..e..c.fg...") ===
      Node("a", Node("b", Node("d"), Node("e")), Node("c", End, Node("f", Node("g"), End))))
  }
}
