import scala.util.parsing.combinator.RegexParsers

/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#btrees
  */
sealed trait Tree[+T] {
  // P56
  def isMirrorOf[U](other: Tree[U]): Boolean

  def isSymmetric: Boolean

  // P58a
  def addValue[U >: T](x: U)(implicit o: U => Ordered[U]): Tree[U]

  // P59a
  def height: Int

  // P60a
  def nodeCount: Int

  // P61a
  def leafList: List[T]

  // P61b
  def leafCount: Int = leafList.size

  // P62
  def internalList: List[T]

  def atLevel(n: Int): List[T]

  // P64
  def layoutBinaryTreeR(depth: Int, x: Int): Tree[T]

  def layoutBinaryTree: Tree[T] = layoutBinaryTreeR(1, 1)

  // Used for both P65 and P66
  type Envelope = Map[Int, (Int, Int)] // Level boundaries

  def envelope: Envelope

  // P65
  def layoutBinaryTree2R(depth: Int, totalHeight: Int, x: Int): Tree[T]

  def layoutBinaryTree2: Tree[T] = {
    val maxLevel = if (envelope.isEmpty) 0 else envelope.minBy { case (_, (l, _)) => l }._1 + 1
    val h = height + 1
    // For the root node position we sum the x distances from the level of the leftmost element to the root
    layoutBinaryTree2R(1, h, 1 + (h - maxLevel).until(h - 1).map(math.pow(2, _).toInt).sum)
  }

  // P66
  def layoutBinaryTree3R(depth: Int, x: Int): Tree[T]

  def layoutBinaryTree3: Tree[T] =
  // For the root node position we look for the smallest left boundary l of the root envelope (which is <= 0)
  // Then startX = 1 - l
    layoutBinaryTree3R(1, 1 - (if (envelope.isEmpty) 0 else envelope.map { case (_, (l, _)) => l }.min))

  // P68
  def preorder: List[T]

  def inorder: List[T]

  // P69
  def toDotString: String
}

sealed trait NodeLike[+T] extends Tree[T] {
  def value: T

  def left: Tree[T]

  def right: Tree[T]

  // P56
  override def isSymmetric: Boolean = left.isMirrorOf(right)

  // P59a
  override def height: Int = 1 + math.max(left.height, right.height)

  // P60a
  override def nodeCount: Int = 1 + left.nodeCount + right.nodeCount

  // P61a
  override def leafList: List[T] = if (left == End && right == End) List(value) else left.leafList ++ right.leafList

  // P62
  override def internalList: List[T] =
    if (left == End && right == End) List.empty else value +: (left.internalList ++ right.internalList)

  override def atLevel(n: Int): List[T] =
    if (n < 1) List.empty
    else if (n == 1) List(value)
    else left.atLevel(n - 1) ++ right.atLevel(n - 1)

  // P64
  override def layoutBinaryTreeR(depth: Int, x: Int): PositionedNode[T] = {
    val dx = left.nodeCount
    PositionedNode(value,
      left.layoutBinaryTreeR(depth + 1, x),
      right.layoutBinaryTreeR(depth + 1, x + dx + 1),
      x + dx, depth)
  }

  // Used for both P65 and P66
  override lazy val envelope: Envelope = {
    // Merge subtree envelopes
    val merged = (left.envelope.keys ++ right.envelope.keys).map(k => k + 1 ->(left.envelope.get(k), right.envelope.get(k))).toMap
    // We look at each level to check if subtree envelopes overlap, and by how much they need to be shifted
    // The maximum then is the overall amount by which we need to shift
    // Since it is applied to both subtrees, we only need ceil[max/2] (i.e. 1 => 1, 2 => 1, 3 => 2, 4 => 2, ...)
    val shift = math.round(
      if (merged.isEmpty) 0 // Don't need shift for leaf nodes
      else merged.map {
        // Nodes on both sides: Calculate amount by which we need to shift (> 0 means overlap)
        case (_, (Some((_, l)), Some((r, _)))) => l - r + 1
        // Only nodes on one side: Per default need a shift of 1
        case _ => 1
      }.max / 2f)
    // Shift left boundaries to the left, right boundaries to the right
    merged.mapValues {
      case (Some((l, _)), Some((_, r))) => (l - shift, r + shift)
      case (Some((l, r)), None) => (l - shift, r - shift)
      case (None, Some((l, r))) => (l + shift, r + shift)
      case _ => throw new Exception // won't happen
    } + (0 ->(0, 0))
  }

  // P65
  override def layoutBinaryTree2R(depth: Int, totalHeight: Int, x: Int): Tree[T] = {
    val dx = math.pow(2, totalHeight - depth - 1).toInt
    PositionedNode(value,
      left.layoutBinaryTree2R(depth + 1, totalHeight, x - dx),
      right.layoutBinaryTree2R(depth + 1, totalHeight, x + dx),
      x, depth)
  }

  // P66
  override def layoutBinaryTree3R(depth: Int, x: Int): Tree[T] = {
    // The boundaries of the envelope of the next level (key = 1) are exactly the offsets for the subtrees
    val (shiftL, shiftR) = envelope.getOrElse(1, (0, 0)) // Guard for the leaf nodes
    PositionedNode(value, left.layoutBinaryTree3R(depth + 1, x + shiftL), right.layoutBinaryTree3R(depth + 1, x + shiftR), x, depth)
  }

  // P68
  override def preorder: List[T] = value +: (left.preorder ++ right.preorder)

  override def inorder: List[T] = left.inorder ++ (value +: right.inorder)

  // P69
  override def toDotString: String = value + left.toDotString + right.toDotString
}

case class Node[+T](value: T, left: Tree[T], right: Tree[T]) extends NodeLike[T] {
  // P67
  override def toString = (left, right) match {
    case (End, End) => value.toString
    case _ => s"$value($left,$right)"
  }

  // P56
  override def isMirrorOf[U](other: Tree[U]): Boolean = other match {
    case t: Node[U] => left.isMirrorOf(t.right) && right.isMirrorOf(t.left)
    case _ => false
  }

  // P58a
  override def addValue[U >: T](x: U)(implicit o: (U) => Ordered[U]): Tree[U] =
    if (x <= value) Node(value, left.addValue(x), right) else Node(value, left, right.addValue(x))
}

case object End extends Tree[Nothing] {
  // P67
  override def toString = ""

  // P56
  override def isMirrorOf[U](other: Tree[U]): Boolean = other == End

  override def isSymmetric: Boolean = true

  // P58a
  override def addValue[U >: Nothing](x: U)(implicit o: (U) => Ordered[U]): Tree[U] = Node(x)

  // P59a
  override def height: Int = -1

  // P60a
  override def nodeCount: Int = 0

  // P61a
  override def leafList: List[Nothing] = List.empty

  // P62
  override def internalList: List[Nothing] = List.empty

  override def atLevel(n: Int): List[Nothing] = List.empty

  // P64
  override def layoutBinaryTreeR(depth: Int, x: Int): Tree[Nothing] = End

  // Used for both P65 and P66
  override def envelope: End.Envelope = Map.empty

  // P65
  override def layoutBinaryTree2R(depth: Int, totalHeight: Int, x: Int): Tree[Nothing] = End

  // P66
  override def layoutBinaryTree3R(depth: Int, x: Int): Tree[Nothing] = End

  // P68
  override def preorder: List[Nothing] = List.empty

  override def inorder: List[Nothing] = List.empty

  // P69
  override def toDotString: String = "."
}

case class PositionedNode[+T](value: T, left: Tree[T], right: Tree[T], x: Int, y: Int) extends NodeLike[T] {
  override def toString: String = s"T[$x,$y]($value $left $right)"

  // P56
  override def isMirrorOf[U](other: Tree[U]): Boolean = other match {
    case t: PositionedNode[U] => left.isMirrorOf(t.right) && right.isMirrorOf(t.left)
    case _ => false
  }

  // P58a
  override def addValue[U >: T](x: U)(implicit o: (U) => Ordered[U]): Tree[U] =
    throw new UnsupportedOperationException("addValue not supported for positioned trees")
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
  def symmetricBalancedTrees[T](nodes: Int, value: T): List[Tree[T]] =
  completelyBalancedTrees(nodes, value) filter (_.isSymmetric)

  // P58b
  def fromList[T](l: List[T])(implicit o: T => Ordered[T]): Tree[T] = l.foldLeft(End: Tree[T])((t, x) => t.addValue(x))

  // P59b
  def heightBalancedTreesWithHeight[T](height: Int, value: T): List[Tree[T]] =
    if (height < 0) List(End)
    else if (height == 0) List(Node(value))
    else {
      val fullHeight = heightBalancedTreesWithHeight(height - 1, value)
      val smallerHeight = heightBalancedTreesWithHeight(height - 2, value)
      (for (l <- fullHeight; r <- fullHeight) yield Node(value, l, r)) ++
        (for (t1 <- fullHeight; t2 <- smallerHeight) yield List(Node(value, t1, t2), Node(value, t2, t1))).flatten
    }

  // P60b
  def maxHbalNodes(height: Int): Int = math.pow(2, height + 1).toInt - 1

  // P60c
  def minHbalNodes(height: Int): Int =
    if (height <= 0) height + 1 else 1 + minHbalNodes(height - 1) + minHbalNodes(height - 2)

  // P60d
  def maxHbalHeight(nodes: Int): Int = (nodes to 0 by -1).dropWhile(minHbalNodes(_) > nodes).headOption.getOrElse(-1)

  // P60e
  def minHbalHeight(nodes: Int): Int = if (nodes == 0) -1 else 1 + minHbalHeight(nodes / 2)

  // P60f
  def heightBalancedTreesWithNodes[T](nodes: Int, value: T): List[Tree[T]] = (
    for {
      h <- minHbalHeight(nodes) to maxHbalHeight(nodes)
      t <- heightBalancedTreesWithHeight(h, value) if t.nodeCount == nodes
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
  def fromString(s: String): Tree[String] = TreeFromStringParser.parseAll(TreeFromStringParser.tree, s) match {
    case TreeFromStringParser.Success(tree, _) => tree
    case TreeFromStringParser.NoSuccess(msg, _) => throw new IllegalArgumentException(s"Cannot parse tree: $msg")
  }

  // P68
  def preInTree[T](preorder: List[T], inorder: List[T]): Tree[T] = preorder match {
    case Nil => End
    case p :: ps =>
      val (inLeft, inRight) = inorder.span(_ != p)
      val (preLeft, preRight) = ps.splitAt(inLeft.length)
      Node(p, preInTree(preLeft, inLeft), preInTree(preRight, inRight.tail))
  }

  // P69
  def fromDotString(s: String): Tree[String] = TreeFromDotStringParser.parseAll(TreeFromDotStringParser.tree, s) match {
    case TreeFromDotStringParser.Success(tree, _) => tree
    case TreeFromDotStringParser.NoSuccess(msg, _) => throw new IllegalArgumentException(s"Cannot parse tree: $msg")
  }
}

object Node {
  def apply[T](value: T): Node[T] = Node(value, End, End)
}

object PositionedNode {
  def apply[T](value: T, x: Int, y: Int): PositionedNode[T] = PositionedNode(value, End, End, x, y)
}

object TreeFromStringParser extends RegexParsers {
  def tree: Parser[Tree[String]] = node | leaf | end

  def node: Parser[Tree[String]] = (label <~ "(") ~ (tree <~ ",") ~ (tree <~ ")") ^^ { case value ~ left ~ right => Node(value, left, right) }

  def leaf: Parser[Tree[String]] = label ^^ (Node(_))

  def end: Parser[Tree[String]] = "" ^^^ End

  def label: Parser[String] = "\\w+".r
}

object TreeFromDotStringParser extends RegexParsers {
  def tree: Parser[Tree[String]] = node | end

  def node: Parser[Tree[String]] = (label ~ tree ~ tree) ^^ { case value ~ left ~ right => Node(value, left, right) }

  def end: Parser[Tree[String]] = "." ^^^ End

  def label: Parser[String] = "\\w".r
}
