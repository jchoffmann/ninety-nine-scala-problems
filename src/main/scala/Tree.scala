/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#btrees
  */
sealed abstract class Tree[+T] {
  // P56
  def isMirrorOf[U](other: Tree[U]): Boolean

  def isSymmetric: Boolean

  // P57
  def addValue[U >: T](x: U)(implicit o: U => Ordered[U]): Tree[U]

  // P61
  def leafCount: Int = ???

  // P62
  def leafList: List[T] = ???

  def atLevel(n: Int): List[T] = ???

  // P64
  def layoutBinaryTree: PositionedNode[T] = ???

  // P65
  def layoutBinaryTree2: PositionedNode[T] = ???

  // P66
  def layoutBinaryTree3: PositionedNode[T] = ???

  // P68
  def preorder: List[T] = ???

  def inorder: List[T] = ???

  // P69
  def toDotString: String = ???
}

case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends Tree[T] {
  override def toString = "T(" + value.toString + " " + left.toString + " " + right.toString + ")"

  // P56
  override def isMirrorOf[U](other: Tree[U]): Boolean = other match {
    case t: Node[U] => left.isMirrorOf(t.right) && right.isMirrorOf(t.left)
    case _ => false
  }

  override def isSymmetric: Boolean = left.isMirrorOf(right)

  // P57
  override def addValue[U >: T](x: U)(implicit o: (U) => Ordered[U]): Tree[U] =
    if (x <= value) Node(value, left.addValue(x), right) else Node(value, left, right.addValue(x))
}

case object End extends Tree[Nothing] {
  override def toString = "."

  // P56
  override def isMirrorOf[U](other: Tree[U]): Boolean = other == End

  override def isSymmetric: Boolean = true

  // P57
  override def addValue[U >: Nothing](x: U)(implicit o: (U) => Ordered[U]): Tree[U] = Node(x)
}

case class PositionedNode[+T](value: T, left: Tree[T], right: Tree[T], x: Int, y: Int) extends Tree[T] {
  override def toString: String = "T[" + x.toString + "," + y.toString + "](" + value.toString + " " + left.toString + " " + right.toString + ")"

  // P56
  override def isMirrorOf[U](other: Tree[U]): Boolean = other match {
    case t: PositionedNode[U] => left.isMirrorOf(t.right) && right.isMirrorOf(t.left)
    case _ => false
  }

  override def isSymmetric: Boolean = left.isMirrorOf(right)

  // P57
  override def addValue[U >: T](x: U)(implicit o: (U) => Ordered[U]): Tree[U] =
    if (x <= value) Node(value, left.addValue(x), right) else Node(value, left, right.addValue(x))
}

object Tree {
  // P55
  def cBalanced[T](n: Int, value: T): List[Tree[T]] =
    if (n < 1) List(End)
    else if (n % 2 == 1) {
      val t = cBalanced(n / 2, value)
      for (l <- t; r <- t) yield Node(value, l, r)
    }
    else {
      for {
        t1 <- cBalanced((n - 1) / 2, value)
        t2 <- cBalanced((n - 1) / 2 + 1, value)
      } yield List(Node(value, t1, t2), Node(value, t2, t1))
    }.flatten

  // P57
  def fromList[T](l: List[T])(implicit o: T => Ordered[T]): Tree[T] = l.foldLeft(End: Tree[T])((t, x) => t.addValue(x))

  // P58
  def symmetricBalancedTrees[T](n: Int, value: T): List[Tree[T]] = cBalanced(n, value) filter (_.isSymmetric)

  // P59
  def hBalanced[T](n: Int, value: T): List[Tree[T]] = ???

  // P60
  def minHbalNodes(n: Int): Int = ???

  def maxHbalHeight(n: Int): Int = ???

  def hbalTreesWithNodes[T](n: Int, value: T): List[Tree[T]] = ???

  // P63
  def completeBinaryTree[T](n: Int, value: T): Tree[T] = ???

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

