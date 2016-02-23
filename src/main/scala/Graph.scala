/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#graphs
  */
abstract class GraphBase[T, U] {

  case class Edge(n1: Node, n2: Node, value: U) {
    def toTuple = (n1.value, n2.value, value)

    // P80
    override def toString: String = {
      val label = value match {
        case () => ""
        case _ => labelSep + value
      }
      s"$n1$edgeSep$n2$label"
    }
  }

  case class Node(value: T) {
    var adj: List[Edge] = Nil

    // neighbors are all nodes adjacent to this node.
    def neighbors: List[Node] = adj.map(edgeTarget(_, this).get)

    // P80
    override def toString: String = value.toString

    // P86
    def degree: Int = ???
  }

  var nodes: Map[T, Node] = Map()
  var edges: List[Edge] = Nil

  // If the edge E connects N to another node, returns the other node,
  // otherwise returns None.
  def edgeTarget(e: Edge, n: Node): Option[Node]

  def canEqual(other: Any): Boolean

  override def equals(o: Any) = o match {
    case g: GraphBase[_, _] =>
      (g canEqual this) &&
        (nodes.keys.groupBy(identity) == g.nodes.keys.groupBy(identity)) &&
        (edges.map(_.toTuple).groupBy(identity) == g.edges.map(_.toTuple).groupBy(identity))
    case _ => false
  }

  override def hashCode(): Int =
    41 * (41 + nodes.keys.groupBy(identity).hashCode) + edges.map(_.toTuple).groupBy(identity).hashCode

  def addNode(value: T): Unit = {
    val n = new Node(value)
    nodes = Map(value -> n) ++ nodes
  }

  // P80
  def toTermForm: (List[T], List[(T, T, U)]) = (nodes.keys.toList, edges.map(_.toTuple))

  def toAdjacentForm: List[(T, List[(T, U)])] =
    nodes.values.map(n => (n.value, n.adj.map(e => (edgeTarget(e, n).get.value, e.value))))(collection.breakOut)

  val labelSep = "/"

  val edgeSep: String

  override def toString: String = {
    val edgeNodes = edges.flatMap(e => List(e.n1, e.n2))
    val isolated = nodes.values.filter(!edgeNodes.contains(_))
    s"[${(isolated ++ edges).mkString(", ")}]"
  }

  // P81
  def findPaths(from: T, to: T): List[List[T]] = {
    def findPathsR(current: Node, path: List[T]): List[List[T]] = {
      if (current.value == to) List(path.reverse)
      else (for (n <- current.neighbors if !path.contains(n.value)) yield findPathsR(n, n.value +: path)).flatten
    }
    findPathsR(nodes(from), List(from))
  }

  // P82
  def findCycles(from: T): List[List[T]] =
    nodes(from).neighbors.flatMap(n => findPaths(n.value, from)).filter(_.size >= 3).map(from +: _)

  // P85
  def isIsomorphicTo[R, S](g: GraphBase[R, S]): Boolean = ???

  // P86
  def nodesByDegree: List[T] = ???

  def colorNodes: List[(T, Int)] = ???

  // P87
  def nodesByDepthFrom(from: T): List[T] = ???

  // P88
  def splitGraph: List[GraphBase[T, U]] = ???

  // P89
  def isBipartite: Boolean = ???
}

class Graph[T, U] extends GraphBase[T, U] {
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Graph[_, _]]

  def edgeTarget(e: Edge, n: Node): Option[Node] =
    if (e.n1 == n) Some(e.n2)
    else if (e.n2 == n) Some(e.n1)
    else None

  def addEdge(n1: T, n2: T, value: U): Unit = {
    val e = new Edge(nodes(n1), nodes(n2), value)
    edges = e :: edges
    nodes(n1).adj = e :: nodes(n1).adj
    nodes(n2).adj = e :: nodes(n2).adj
  }

  // P80
  override val edgeSep: String = "-"

  // P83
  private def cut(from: List[Node]): List[(Node, Edge)] = for {
    n <- from
    e <- n.adj
    n2 = edgeTarget(e, n).get if !from.contains(n2)
  } yield (n2, e)

  def spanningTrees: List[Graph[T, U]] = {
    def spanningTreesR(treeNodes: List[Node], treeEdges: List[Edge]): List[Graph[T, U]] = {
      if (nodes.size == treeNodes.size) List(Graph.termLabel(nodes.keys.toList, treeEdges.map(_.toTuple)))
      else cut(treeNodes).flatMap {
        case (n, e) => spanningTreesR(n +: treeNodes, e +: treeEdges)
      }
    }
    spanningTreesR(List(nodes.values.head), List.empty).distinct
  }

  def isTree: Boolean = spanningTrees.size == 1

  def isConnected: Boolean = spanningTrees.nonEmpty

  // P84
  def minimalSpanningTree(implicit o: U => Ordered[U]): Graph[T, U] = {
    def minimalSpanningTreeR(treeNodes: List[Node], treeEdges: List[Edge]): Graph[T, U] = {
      if (nodes.size == treeNodes.size) Graph.termLabel(nodes.keys.toList, treeEdges.map(_.toTuple))
      else {
        val (nMin, eMin) = cut(treeNodes).sortBy { case (_, e) => e.value }.head
        minimalSpanningTreeR(nMin +: treeNodes, eMin +: treeEdges)
      }
    }
    minimalSpanningTreeR(List(nodes.values.head), List.empty)
  }
}

class Digraph[T, U] extends GraphBase[T, U] {
  override def canEqual(other: Any): Boolean = other.isInstanceOf[Digraph[_, _]]

  def edgeTarget(e: Edge, n: Node): Option[Node] =
    if (e.n1 == n) Some(e.n2)
    else None

  def addArc(source: T, dest: T, value: U): Unit = {
    val e = new Edge(nodes(source), nodes(dest), value)
    edges = e :: edges
    nodes(source).adj = e :: nodes(source).adj
  }

  // P80
  override val edgeSep: String = ">"
}

abstract class GraphObjBase {
  type GraphClass[T, U]

  def addLabel[T](edges: List[(T, T)]) =
    edges.map(v => (v._1, v._2, ()))

  def term[T](nodes: List[T], edges: List[(T, T)]) =
    termLabel(nodes, addLabel(edges))

  def termLabel[T, U](nodes: List[T], edges: List[(T, T, U)]): GraphClass[T, U]

  def addAdjacentLabel[T](nodes: List[(T, List[T])]) =
    nodes.map(a => (a._1, a._2.map((_, ()))))

  def adjacent[T](nodes: List[(T, List[T])]) =
    adjacentLabel(addAdjacentLabel(nodes))

  def adjacentLabel[T, U](nodes: List[(T, List[(T, U)])]): GraphClass[T, U]

  // P80
  def fromString(s: String): GraphClass[String, Unit] = {
    val edgesR = """(\w+)[->](\w+)""".r
    val edges = edgesR.findAllMatchIn(s).map { case edgesR(n1, n2) => (n1, n2) }.toList
    val nodesR = """(\w+)""".r
    val nodes = nodesR.findAllMatchIn(s).map { case nodesR(n) => n }.toSet.toList
    term(nodes, edges)
  }

  def fromStringLabel(s: String): GraphClass[String, Any] = {
    val tokens = s.replaceAll("""[\[\]]""", "").split(""",\s*""").map(_.split("[-/>]").toList)
    val (nodes, edges) = tokens.foldLeft(List.empty[String], List.empty[(String, String, Any)]) {
      case ((ns, es), n :: Nil) => (n +: ns, es)
      case ((ns, es), n1 :: n2 :: Nil) => (n1 +: n2 +: ns, (n1, n2, ()) +: es)
      case ((ns, es), n1 :: n2 :: label :: _) => (n1 +: n2 +: ns, (n1, n2, label) +: es)
      case _ => throw new Exception // Won't happen
    }
    termLabel(nodes, edges)
  }
}

object Graph extends GraphObjBase {
  type GraphClass[T, U] = Graph[T, U]

  def termLabel[T, U](nodes: List[T], edges: List[(T, T, U)]) = {
    val g = new Graph[T, U]
    nodes.foreach(g.addNode)
    edges.foreach((g.addEdge _).tupled)
    g
  }

  def adjacentLabel[T, U](nodes: List[(T, List[(T, U)])]) = {
    val g = new Graph[T, U]
    for ((v, a) <- nodes) g.addNode(v)
    for ((n1, a) <- nodes; (n2, l) <- a) {
      if (!g.nodes(n1).neighbors.contains(g.nodes(n2)))
        g.addEdge(n1, n2, l)
    }
    g
  }
}

object Digraph extends GraphObjBase {
  type GraphClass[T, U] = Digraph[T, U]

  def termLabel[T, U](nodes: List[T], edges: List[(T, T, U)]) = {
    val g = new Digraph[T, U]
    nodes.foreach(g.addNode)
    edges.foreach((g.addArc _).tupled)
    g
  }

  def adjacentLabel[T, U](nodes: List[(T, List[(T, U)])]) = {
    val g = new Digraph[T, U]
    for ((n, a) <- nodes) g.addNode(n)
    for ((s, a) <- nodes; (d, l) <- a) g.addArc(s, d, l)
    g
  }
}