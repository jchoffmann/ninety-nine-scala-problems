import org.scalatest.FunSuite

class GraphSuite extends FunSuite {
  test("80 (not mentioned in problem statements) graphs constructed from equivalent forms are equal") {
    val gt = Graph.term(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c), ('b, 'f), ('c, 'f), ('f, 'k), ('g, 'h)))
    val ga = Graph.adjacent(
      List(('b, List('c, 'f)), ('c, List('b, 'f)), ('d, Nil), ('f, List('b, 'c, 'k)), ('g, List('h)), ('h, List('g)), ('k, List('f))))
    assert(gt === ga)
  }

  test("80 (not mentioned in problem statement) labeled graphs constructed from equivalent forms are equal") {
    val gt = Graph.termLabel(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c, 1), ('b, 'f, 3), ('c, 'f, 5), ('f, 'k, 7), ('g, 'h, 9)))
    val ga = Graph.adjacentLabel(
      List(('b, List(('c, 1), ('f, 3))), ('c, List(('b, 1), ('f, 5))), ('d, Nil), ('f, List(('b, 3), ('c, 5), ('k, 7))), ('g, List(('h, 9))), ('h, List(('g, 9))), ('k, List(('f, 7)))))
    assert(gt === ga)
  }

  test("80 (not mentioned in problem statements) digraphs constructed from equivalent forms are equal") {
    val gt = Digraph.term(
      List('r, 's, 't, 'u, 'v),
      List(('s, 'r), ('s, 'u), ('u, 'r), ('u, 's), ('v, 'u)))
    val ga = Digraph.adjacent(
      List(('r, Nil), ('s, List('r, 'u)), ('t, Nil), ('u, List('r, 's)), ('v, List('u))))
    assert(gt === ga)
  }

  test("80 (not mentioned in problem statement) labeled digraphs constructed from equivalent forms are equal") {
    val gt = Digraph.termLabel(
      List('k, 'm, 'p, 'q),
      List(('m, 'q, 7), ('p, 'm, 5), ('p, 'q, 9)))
    val ga = Digraph.adjacentLabel(
      List(('k, Nil), ('m, List(('q, 7))), ('p, List(('m, 5), ('q, 9))), ('q, Nil)))
    assert(gt === ga)
  }

  test("80 (not mentioned in problem statement) equivalent empty-labeled and unlabeled graphs are equal") {
    val gt = Graph.term(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c), ('b, 'f), ('c, 'f), ('f, 'k), ('g, 'h)))
    val gtl = Graph.termLabel(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c, ()), ('b, 'f, ()), ('c, 'f, ()), ('f, 'k, ()), ('g, 'h, ())))
    assert(gt === gtl)
  }

  test("80 (not mentioned in problem statement) equivalent empty-labeled digraphs and unlabeled digraphs are equal") {
    val gt = Digraph.term(
      List('k, 'm, 'p, 'q),
      List(('m, 'q), ('p, 'm), ('p, 'q)))
    val gtl = Digraph.termLabel(
      List('k, 'm, 'p, 'q),
      List(('m, 'q, ()), ('p, 'm, ()), ('p, 'q, ())))
    assert(gt === gtl)
  }

  test("80 convert graph to graph-term form") {
    val g = Graph.adjacent(
      List(('b, List('c, 'f)), ('c, List('b, 'f)), ('d, Nil), ('f, List('b, 'c, 'k)), ('g, List('h)), ('h, List('g)), ('k, List('f))))
    val termForm = (
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c, ()), ('b, 'f, ()), ('c, 'f, ()), ('f, 'k, ()), ('g, 'h, ())))
    assert(g.toTermForm === termForm)
  }

  test("80 convert graph to adjacency-list form") {
    val g = Graph.term(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c), ('b, 'f), ('c, 'f), ('f, 'k), ('g, 'h)))
    val adjacentForm = List(('b, List(('c, ()), ('f, ()))), ('c, List(('b, ()), ('f, ()))), ('d, Nil), ('f, List(('b, ()), ('c, ()), ('k, ()))), ('g, List(('h, ()))), ('h, List(('g, ()))), ('k, List(('f, ()))))
    assert(g.toAdjacentForm === adjacentForm)
  }

  test("80 convert digraph to graph-term form") {
    val g = Digraph.adjacent(
      List(('b, List('c, 'f)), ('c, List('b, 'f)), ('d, Nil), ('f, List('b, 'c, 'k)), ('g, List('h)), ('h, List('g)), ('k, List('f))))
    val termForm = (
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c, ()), ('b, 'f, ()), ('c, 'f, ()), ('f, 'k, ()), ('g, 'h, ())))
    assert(g.toTermForm === termForm)
  }

  test("80 convert digraph to adjacency-list form") {
    val g = Digraph.term(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c), ('b, 'f), ('c, 'f), ('f, 'k), ('g, 'h)))
    val adjacentForm = List(('b, List(('c, ()), ('f, ()))), ('c, List(('b, ()), ('f, ()))), ('d, Nil), ('f, List(('b, ()), ('c, ()), ('k, ()))), ('g, List(('h, ()))), ('h, List(('g, ()))), ('k, List(('f, ()))))
    assert(g.toAdjacentForm === adjacentForm)

  }

  test("80 convert graph to human-friendly form") {
    val g = Graph.term(List('d, 'k, 'h, 'c, 'f, 'g, 'b), List(('h, 'g), ('k, 'f), ('f, 'b), ('g, 'h), ('f, 'c), ('b, 'c)))
    assert(g.toString === "[b-c, f-c, g-h, d, f-b, k-f, h-g]")
  }

  test("80 convert human-friendly form to graph") {
    val g = Graph.term(List("d", "k", "h", "c", "f", "g", "b"), List(("h", "g"), ("k", "f"), ("f", "b"), ("g", "h"), ("f", "c"), ("b", "c")))
    assert(Graph.fromString("[b-c, f-c, g-h, d, f-b, k-f, h-g]") === g)
  }

  test("80 convert labeled graph to human-friendly form with labels") {
    val g = Graph.termLabel(List('d, 'k, 'h, 'c, 'f, 'g, 'b), List(('h, 'g, 3), ('k, 'f, ()), ('f, 'b, ()), ('g, 'h, 2), ('f, 'c, ()), ('b, 'c, 1)))
    assert(g.toString === "[b-c/1, f-c, g-h/2, d, f-b, k-f, h-g/3]")
  }

  test("80 convert human-friendly form with labels to labeled graph") {
    val g = Graph.termLabel(List("d", "k", "h", "c", "f", "g", "b"), List(("h", "g", 3), ("k", "f", ()), ("f", "b", ()), ("g", "h", 2), ("f", "c", ()), ("b", "c", 1)))
    assert(Graph.fromStringLabel("[b-c/1, f-c, g-h/2, d, f-b, k-f, h-g/3]") === g)
  }

  test("80 convert digraph to human-friendly form") {
    val g = Digraph.adjacent(List(('m, List('q)), ('p, List('m, 'q)), ('k, Nil), ('q, Nil)))
    assert(g.toString === "[p>q, m>q, k, p>m]")
  }

  test("80 convert human-friendly form to digraph") {
    val g = Digraph.adjacent(List(("m", List("q")), ("p", List("m", "q")), ("k", Nil), ("q", Nil)))
    assert(Digraph.fromString("[p>q, m>q, k, p>m]") === g)
  }

  test("80 convert labeled digraph to human-friendly form with labels") {
    val g = Digraph.adjacentLabel(List(('m, List(('q, 7))), ('p, List(('m, 5), ('q, 9))), ('k, Nil), ('q, Nil)))
    assert(g.toString === "[p>q/9, m>q/7, k, p>m/5]")
  }

  test("80 convert human-friendly form with bales to labeled digraph") {
    val g = Digraph.adjacentLabel(List(("m", List(("q", 7))), ("p", List(("m", 5), ("q", 9))), ("k", Nil), ("q", Nil)))
    assert(Digraph.fromStringLabel("[p>q/9, m>q/7, k, p>m/5]") === g)
  }

  test("81 find all acyclic paths from one node to another in a graph") {
    val g = Digraph.fromStringLabel("[p>q/9, m>q/7, k, p>m/5]")
    assert(g.findPaths("p", "k").isEmpty)
    assert(g.findPaths("p", "q") === List(List("p", "q"), List("p", "m", "q")))
  }

  test("82 find all cycles starting at a given node in a graph") {
    val g = Graph.fromString("[b-c, f-c, g-h, d, f-b, k-f, h-g]")
    assert(g.findCycles("d") == List.empty)
    assert(g.findCycles("g") == List.empty)
    assert(g.findCycles("f") === List(List("f", "c", "b", "f"), List("f", "b", "c", "f")))
  }

  test("83 find all spanning trees for a given graph") {
    val g1 = Graph.fromString("[a-b, b-c, a-c]")
    val g2 = Graph.term(
      List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h),
      List(('a, 'b), ('a, 'd), ('b, 'c), ('b, 'e), ('c, 'e), ('d, 'e), ('d, 'f), ('d, 'g), ('e, 'h), ('f, 'g), ('g, 'h)))
    assert(g1.spanningTrees === List("[a-b, b-c]", "[a-c, b-c]", "[a-b, a-c]").map(Graph.fromString))
    // TODO Check g2
  }

  test("83 check if a graph is a tree") {
    assert(!Graph.fromString("[a-b, b-c, a-c]").isTree)
    assert(Graph.fromString("[a-b, b-c]").isTree)
  }

  test("83 check if a graph is connected") {
    assert(Graph.fromString("[a-b, b-c, a-c]").isConnected)
    assert(!Graph.fromString("[a-b, b-c, a-c, d]").isConnected)
  }

  test("84 find the minimum spanning tree for a given graph") {
    val g1 = Graph.fromStringLabel("[a-b/1, b-c/2, a-c/3]")
    val g2 = Graph.termLabel(
      List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h),
      List(('a, 'b, 5), ('a, 'd, 3), ('b, 'c, 2), ('b, 'e, 4), ('c, 'e, 6), ('d, 'e, 7), ('d, 'f, 4), ('d, 'g, 3), ('e, 'h, 5), ('f, 'g, 4), ('g, 'h, 1)))
    assert(g1.minimalSpanningTree === Graph.fromStringLabel("[a-b/1, b-c/2]"))
    // TODO check g2
  }

  test("85 check if two graphs are isomorphic") {
    val g1 = Graph.fromString("[a-b, c]")
    val g2 = Graph.fromString("[8, 5-7]")
    val g3 = Graph.fromString("[a-b, b-c]")
    assert(g1.isIsomophicTo(g2))
    assert(!g1.isIsomophicTo(g3))
  }

  test("86 determine the degree of a given node") {
    val g = Graph.fromString("[a-b, b-c, a-c, a-d]")
    assert(g.nodes("a").degree === 3)
  }

  test("86 list all nodes of a graph sorted according to decreasing degree") {
    val g = Graph.fromString("[a-b, b-c, a-c, a-d]")
    assert(g.nodesByDegree === List(Node("a"), Node("c"), Node("b"), Node("d")))
  }

  test("86 graph colouring: paint the nodes of a graph in such a way that adjacent nodes have different colours") {
    val g = Graph.fromString("[a-b, b-c, a-c, a-d]")
    assert(g.colorNodes === List((Node("a"), 1), (Node("b"), 2), (Node("c"), 3), (Node("d"), 2)))
  }

  test("87 generate a depth-first order graph traversal sequence") {
    val g = Graph.fromString("[a-b, b-c, e, a-c, a-d]")
    assert(g.nodesByDepthFrom("d") === List("c", "b", "a", "d"))
  }

  test("88 split a graph into connected components") {
    val g = Graph.fromString("[a-b, c]")
    assert(g.splitGraph === List("[a-b]", "[c]").map(Graph.fromString))
  }

  test("89 determine whether a given graph is bipartite") {
    assert(Digraph.fromString("[a>b, c>a, d>b]").isBipartite)
    assert(!Graph.fromString("[a-b, b-c, c-a]").isBipartite)
    assert(Graph.fromString("[a-b, b-c, d]").isBipartite)
    assert(!Graph.fromString("[a-b, b-c, d, e-f, f-g, g-e, h]").isBipartite)
  }
}
