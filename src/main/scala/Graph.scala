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

  override def equals(o: Any) = o match {
    case g: GraphBase[_, _] =>
      nodes.size == g.nodes.size && edges.size == g.edges.size &&
        (nodes.keys.toList diff g.nodes.keys.toList).isEmpty &&
        (edges.map(_.toTuple) diff g.edges.map(_.toTuple)).isEmpty
    case _ => false
  }

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
  def findPaths(from: T, to: T): List[List[T]] = ???

  // P82
  def findCycles(from: T): List[List[T]] = ???

  // P85
  def isIsomophicTo[R, S](g: GraphBase[R, S]): Boolean = ???

  // P86
  def nodesByDegree: List[Node] = ???

  def colorNodes: List[(Node, Int)] = ???

  // P87
  def nodesByDepthFrom(from: T): List[T] = ???

  // P88
  def splitGraph: List[GraphBase[T, U]] = ???

  // P89
  def isBipartite: Boolean = ???
}

class Graph[T, U] extends GraphBase[T, U] {
  override def equals(o: Any) = o match {
    case g: Graph[_, _] => super.equals(g)
    case _ => false
  }

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
  def spanningTrees: List[Graph[T, U]] = ???

  def isTree: Boolean = ???

  def isConnected: Boolean = ???

  // P84
  def minimalSpanningTree: Graph[T, U] = ???
}

class Digraph[T, U] extends GraphBase[T, U] {
  override def equals(o: Any) = o match {
    case g: Digraph[_, _] => super.equals(g)
    case _ => false
  }

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