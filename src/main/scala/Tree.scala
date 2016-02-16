/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#btrees
  */
sealed abstract class Tree[+T] {
  // P56
  def isSymmetric: Boolean = ???

  // P57
  def addValue[U >: T](x: U)(implicit o: U => Ordered[U]): Tree[U] = ???

  // P59
  def height: Int = ???

  // P60
  def nodeCount: Int = ???

  // P61
  def leafCount: Int = ???

  def leafList: List[T] = ???

  // P62
  def internalList: List[T] = ???

  def atLevel(n: Int): List[T] = ???

  // P64
  def layoutBinaryTree: Tree[T] = ???

  // P65
  def layoutBinaryTree2: Tree[T] = ???

  // P66
  def layoutBinaryTree3: Tree[T] = ???

  // P68
  def preorder: List[T] = ???

  def inorder: List[T] = ???

  // P69
  def toDotString: String = ???
}

case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {
  override def toString = s"T($value $left $right)"
}

case object End extends Tree[Nothing] {
  override def toString = "."
}

case class PositionedNode[+T](value: T, left: Tree[T], right: Tree[T], x: Int, y: Int) extends Tree[T] {
  override def toString: String = s"T[$x,$y]($value $left $right)"
}

object Tree {
  // P55
  def completelyBalancedTrees[T](nodes: Int, value: T): List[Tree[T]] = ???

  // P57
  def fromList[T](l: List[T])(implicit o: T => Ordered[T]): Tree[T] = ???

  // P58
  def symmetricBalancedTrees[T](nodes: Int, value: T): List[Tree[T]] = ???

  // P59
  def heightBalancedTrees[T](height: Int, value: T): List[Tree[T]] = ???

  // P60
  def minHbalNodes(height: Int): Int = ???

  def maxHbalNodes(height: Int): Int = ???

  def minHbalHeight(nodes: Int): Int = ???

  def maxHbalHeight(nodes: Int): Int = ???

  def heightBalancedTreesWithNodes[T](nodes: Int, value: T): List[Tree[T]] = ???

  // P63
  def completeBinaryTree[T](nodes: Int, value: T): Tree[T] = ???

  // P67
  def fromString(s: String): Tree[String] = ???

  // P68
  def preInTree[T](preorder: List[T], inorder: List[T]): Tree[T] = ???

  // P69
  def fromDotString(s: String): Tree[String] = ???
}

object Node {
  def apply[T](value: T): Node[T] = Node(value, End, End)
}

object PositionedNode {
  def apply[T](value: T, x: Int, y: Int): PositionedNode[T] = PositionedNode(value, End, End, x, y)
}

