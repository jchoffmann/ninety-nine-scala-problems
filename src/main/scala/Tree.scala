/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#btrees
  */
sealed abstract class Tree[+T] {
  // P56
  def isMirrorOf[U](other: Tree[U]): Boolean

  def isSymmetric: Boolean

  // P57
  def addValue[U >: T](x: U)(implicit o: U => Ordered[U]): Tree[U]

  // P59
  def height: Int

  // P60
  def nodeCount: Int

  // P61
  def leafCount: Int = leafList.size

  def leafList: List[T]

  // P62
  def internalList: List[T]

  def atLevel(n: Int): List[T]

  // P64
  def layoutBinaryTreeR(depth: Int, startX: Int): Tree[T]

  def layoutBinaryTree: Tree[T] = layoutBinaryTreeR(1, 0)

  // P65
  def layoutBinaryTree2R(depth: Int, height: Int, startX: Int): Tree[T]

  def layoutBinaryTree2: Tree[T] = layoutBinaryTree2R(1, height, 0)

  // P66
  def layoutBinaryTree3: Tree[T] = ???

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

  // P59
  override def height: Int = 1 + math.max(left.height, right.height)

  // P57
  override def addValue[U >: T](x: U)(implicit o: (U) => Ordered[U]): Tree[U] =
    if (x <= value) Node(value, left.addValue(x), right) else Node(value, left, right.addValue(x))

  // P60
  override def nodeCount: Int = 1 + left.nodeCount + right.nodeCount

  // P61
  override def leafList: List[T] = if (left == End && right == End) List(value) else left.leafList ++ right.leafList

  // P62
  override def internalList: List[T] =
    if (left == End && right == End) List.empty else value +: (left.internalList ++ right.internalList)

  override def atLevel(n: Int): List[T] =
    if (n < 1) List.empty
    else if (n == 1) List(value)
    else left.atLevel(n - 1) ++ right.atLevel(n - 1)

  // P64
  override def layoutBinaryTreeR(depth: Int, startX: Int): PositionedNode[T] = {
    val newStartX = startX + left.nodeCount + 1
    PositionedNode(value, left.layoutBinaryTreeR(depth + 1, startX), right.layoutBinaryTreeR(depth + 1, newStartX),
      newStartX, depth)
  }

  // P65
  override def layoutBinaryTree2R(depth: Int, height: Int, startX: Int): PositionedNode[T] = {
    val newStartX = startX + math.pow(2, height - 1).toInt - 1
    PositionedNode(value, left.layoutBinaryTree2R(depth + 1, height - 1, startX), right.layoutBinaryTree2R(depth + 1, height - 1, newStartX + 1),
      newStartX, depth)
  }
}

case object End extends Tree[Nothing] {
  override def toString = "."

  // P56
  override def isMirrorOf[U](other: Tree[U]): Boolean = other == End

  override def isSymmetric: Boolean = true

  // P57
  override def addValue[U >: Nothing](x: U)(implicit o: (U) => Ordered[U]): Tree[U] = Node(x)

  // P59
  override def height: Int = 0

  // P60
  override def nodeCount: Int = 0

  // P61
  override def leafList: List[Nothing] = List.empty

  // P62
  override def internalList: List[Nothing] = List.empty

  override def atLevel(n: Int): List[Nothing] = List.empty

  // P64
  override def layoutBinaryTreeR(depth: Int, startX: Int): Tree[Nothing] = this

  // P65
  override def layoutBinaryTree2R(depth: Int, height: Int, startX: Int): Tree[Nothing] = this
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

  // P59
  override def height: Int = 1 + math.max(left.height, right.height)

  // P60
  override def nodeCount: Int = 1 + left.nodeCount + right.nodeCount

  // P61
  override def leafList: List[T] = if (left == End && right == End) List(value) else left.leafList ++ right.leafList

  // P62
  override def internalList: List[T] =
    if (left == End && right == End) List.empty else value +: (left.leafList ++ right.leafList)

  override def atLevel(n: Int): List[T] = if (n < 1) List.empty else if (n == 1) List(value) else atLevel(n - 1)

  // P64
  override def layoutBinaryTreeR(depth: Int, startX: Int): PositionedNode[T] = {
    val newStartX = startX + left.nodeCount + 1
    PositionedNode(value, left.layoutBinaryTreeR(depth + 1, startX), right.layoutBinaryTreeR(depth + 1, newStartX),
      newStartX, depth)
  }

  // P65
  override def layoutBinaryTree2R(depth: Int, height: Int, startX: Int): PositionedNode[T] = {
    val newStartX = startX + math.pow(2, height - 1).toInt - 1
    PositionedNode(value, left.layoutBinaryTree2R(depth + 1, height - 1, startX), right.layoutBinaryTree2R(depth + 1, height - 1, newStartX + 1),
      newStartX, depth)
  }
}

object Tree {
  // P55
  def completelyBalancedTrees[T](nodes: Int, value: T): List[Tree[T]] =
    if (nodes < 1) List(End)
    else if (nodes % 2 == 1) {
      val ts = completelyBalancedTrees(nodes / 2, value)
      for (l <- ts; r <- ts) yield Node(value, l, r)
    }
    else {
      for {
        smaller <- completelyBalancedTrees((nodes - 1) / 2, value)
        bigger <- completelyBalancedTrees((nodes - 1) / 2 + 1, value)
      } yield List(Node(value, smaller, bigger), Node(value, bigger, smaller))
    }.flatten

  // P57
  def fromList[T](l: List[T])(implicit o: T => Ordered[T]): Tree[T] = l.foldLeft(End: Tree[T])((t, x) => t.addValue(x))

  // P58
  def symmetricBalancedTrees[T](nodes: Int, value: T): List[Tree[T]] =
    completelyBalancedTrees(nodes, value) filter (_.isSymmetric)

  // P59
  def heightBalancedTrees[T](height: Int, value: T): List[Tree[T]] =
    if (height < 1) List(End)
    else if (height == 1) List(Node(value))
    else {
      val fullHeight = heightBalancedTrees(height - 1, value)
      val smallerHeight = heightBalancedTrees(height - 2, value)
      (for (l <- fullHeight; r <- fullHeight) yield Node(value, l, r)) ++
        (for (t1 <- fullHeight; t2 <- smallerHeight) yield List(Node(value, t1, t2), Node(value, t2, t1))).flatten
    }

  // P60
  def minHbalNodes(height: Int): Int =
    if (height <= 1) height else 1 + minHbalNodes(height - 1) + minHbalNodes(height - 2)

  def maxHbalNodes(height: Int): Int = math.pow(2, height).toInt - 1

  def minHbalHeight(nodes: Int): Int = if (nodes == 0) 0 else 1 + minHbalHeight(nodes / 2)

  def maxHbalHeight(nodes: Int): Int = (nodes to 0 by -1).dropWhile(minHbalNodes(_) > nodes).head

  def heightBalancedTreesWithNodes[T](nodes: Int, value: T): List[Tree[T]] = (
    for {
      h <- minHbalHeight(nodes) to maxHbalHeight(nodes)
      t <- heightBalancedTrees(h, value) if t.nodeCount == nodes
    } yield t
    ) (collection.breakOut)

  // P63
  def completeBinaryTree[T](nodes: Int, value: T): Tree[T] = {
    def generate[V <: T](address: Int): Tree[T] =
      if (address > nodes) End
      else Node(value, generate(2 * address), generate(2 * address + 1))
    generate(1)
  }

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

