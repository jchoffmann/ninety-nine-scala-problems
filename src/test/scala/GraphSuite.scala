import java.util.NoSuchElementException

import org.scalatest.{FunSuite, Inspectors, Matchers}

class GraphSuite extends FunSuite with Matchers with Inspectors {
  test("80 (not mentioned in problem statements) graphs constructed from equivalent forms are equal") {
    val gt = Graph.term(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c), ('b, 'f), ('c, 'f), ('f, 'k), ('g, 'h)))
    val ga = Graph.adjacent(
      List(('b, List('c, 'f)), ('c, List('b, 'f)), ('d, Nil), ('f, List('b, 'c, 'k)), ('g, List('h)), ('h, List('g)), ('k, List('f))))
    gt shouldEqual ga
  }

  test("80 (not mentioned in problem statement) labeled graphs constructed from equivalent forms are equal") {
    val gt = Graph.termLabel(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c, 1), ('b, 'f, 3), ('c, 'f, 5), ('f, 'k, 7), ('g, 'h, 9)))
    val ga = Graph.adjacentLabel(
      List(('b, List(('c, 1), ('f, 3))), ('c, List(('b, 1), ('f, 5))), ('d, Nil), ('f, List(('b, 3), ('c, 5), ('k, 7))), ('g, List(('h, 9))), ('h, List(('g, 9))), ('k, List(('f, 7)))))
    gt shouldEqual ga
  }

  test("80 (not mentioned in problem statements) digraphs constructed from equivalent forms are equal") {
    val gt = Digraph.term(
      List('r, 's, 't, 'u, 'v),
      List(('s, 'r), ('s, 'u), ('u, 'r), ('u, 's), ('v, 'u)))
    val ga = Digraph.adjacent(
      List(('r, Nil), ('s, List('r, 'u)), ('t, Nil), ('u, List('r, 's)), ('v, List('u))))
    gt shouldEqual ga
  }

  test("80 (not mentioned in problem statement) labeled digraphs constructed from equivalent forms are equal") {
    val gt = Digraph.termLabel(
      List('k, 'm, 'p, 'q),
      List(('m, 'q, 7), ('p, 'm, 5), ('p, 'q, 9)))
    val ga = Digraph.adjacentLabel(
      List(('k, Nil), ('m, List(('q, 7))), ('p, List(('m, 5), ('q, 9))), ('q, Nil)))
    gt shouldEqual ga
  }

  test("80 (not mentioned in problem statement) equivalent empty-labeled and unlabeled graphs are equal") {
    val gt = Graph.term(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c), ('b, 'f), ('c, 'f), ('f, 'k), ('g, 'h)))
    val gtl = Graph.termLabel(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c, ()), ('b, 'f, ()), ('c, 'f, ()), ('f, 'k, ()), ('g, 'h, ())))
    gt shouldEqual gtl
  }

  test("80 (not mentioned in problem statement) equivalent empty-labeled digraphs and unlabeled digraphs are equal") {
    val gt = Digraph.term(
      List('k, 'm, 'p, 'q),
      List(('m, 'q), ('p, 'm), ('p, 'q)))
    val gtl = Digraph.termLabel(
      List('k, 'm, 'p, 'q),
      List(('m, 'q, ()), ('p, 'm, ()), ('p, 'q, ())))
    gt shouldEqual gtl
  }

  test("80 convert graph to graph-term form") {
    val g = Graph.adjacent(
      List(('b, List('c, 'f)), ('c, List('b, 'f)), ('d, Nil), ('f, List('b, 'c, 'k)), ('g, List('h)), ('h, List('g)), ('k, List('f))))
    val (nodes, edges) = g.toTermForm
    nodes should contain theSameElementsAs List('b, 'c, 'd, 'f, 'g, 'h, 'k)
    edges should contain theSameElementsAs List(('b, 'c, ()), ('b, 'f, ()), ('c, 'f, ()), ('f, 'k, ()), ('g, 'h, ()))
  }

  test("80 convert graph to adjacency-list form") {
    val g = Graph.term(
      List('b, 'c, 'd, 'f, 'g, 'h, 'k),
      List(('b, 'c), ('b, 'f), ('c, 'f), ('f, 'k), ('g, 'h)))
    val result = List(('b, List(('c, ()), ('f, ()))), ('c, List(('b, ()), ('f, ()))), ('d, Nil), ('f, List(('b, ()), ('c, ()), ('k, ()))), ('g, List(('h, ()))), ('h, List(('g, ()))), ('k, List(('f, ()))))

    val adjacentForm = g.toAdjacentForm.toMap
    val resultM = result.toMap
    adjacentForm.keys should contain theSameElementsAs resultM.keys
    forAll(adjacentForm.keys) { k => adjacentForm(k) should contain theSameElementsAs resultM(k) }
  }

  test("80 convert digraph to graph-term form") {
    val g = Digraph.adjacent(
      List(('r, Nil), ('s, List('r, 'u)), ('t, Nil), ('u, List('r, 's)), ('v, List('u))))
    val (nodes, edges) = g.toTermForm
    nodes should contain theSameElementsAs List('r, 's, 't, 'u, 'v)
    edges should contain theSameElementsAs List(('s, 'r, ()), ('s, 'u, ()), ('u, 'r, ()), ('u, 's, ()), ('v, 'u, ()))
  }

  test("80 convert digraph to adjacency-list form") {
    val g = Digraph.term(
      List('r, 's, 't, 'u, 'v),
      List(('s, 'r), ('s, 'u), ('u, 'r), ('u, 's), ('v, 'u)))
    val result = List(('r, Nil), ('s, List(('r, ()), ('u, ()))), ('t, Nil), ('u, List(('r, ()), ('s, ()))), ('v, List(('u, ()))))

    val adjacentForm = g.toAdjacentForm.toMap
    val resultM = result.toMap
    adjacentForm.keys should contain theSameElementsAs resultM.keys
    forAll(adjacentForm.keys) { k => adjacentForm(k) should contain theSameElementsAs resultM(k) }
  }

  test("80 convert graph to human-friendly form") {
    val g = Graph.term(List('d', 'k', 'h', 'c', 'f', 'g', 'b'), List(('h', 'g'), ('k', 'f'), ('f', 'b'), ('g', 'h'), ('f', 'c'), ('b', 'c')))
    g.toString shouldEqual "[d, b-c, f-c, g-h, f-b, k-f, h-g]"
  }

  test("80 convert human-friendly form to graph") {
    val g = Graph.term(List("d", "k", "h", "c", "f", "g", "b"), List(("h", "g"), ("k", "f"), ("f", "b"), ("g", "h"), ("f", "c"), ("b", "c")))
    Graph.fromString("[b-c, f-c, g-h, d, f-b, k-f, h-g]") shouldEqual g
  }

  test("80 convert labeled graph to human-friendly form with labels") {
    val g = Graph.termLabel(List('d', 'k', 'h', 'c', 'f', 'g', 'b'), List(('h', 'g', 3), ('k', 'f', ()), ('f', 'b', ()), ('g', 'h', 2), ('f', 'c', ()), ('b', 'c', 1)))
    g.toString shouldEqual "[d, b-c/1, f-c, g-h/2, f-b, k-f, h-g/3]"
  }

  test("80 convert human-friendly form with labels to labeled graph") {
    val g = Graph.termLabel(List("d", "k", "h", "c", "f", "g", "b"), List(("h", "g", "3"), ("k", "f", ()), ("f", "b", ()), ("g", "h", "2"), ("f", "c", ()), ("b", "c", "1")))
    Graph.fromStringLabel("[b-c/1, f-c, g-h/2, d, f-b, k-f, h-g/3]") shouldEqual g
  }

  test("80 convert digraph to human-friendly form") {
    val g = Digraph.adjacent(List(('m', List('q')), ('p', List('m', 'q')), ('k', Nil), ('q', Nil)))
    g.toString shouldEqual "[k, p>q, p>m, m>q]"
  }

  test("80 convert human-friendly form to digraph") {
    val g = Digraph.adjacent(List(("m", List("q")), ("p", List("m", "q")), ("k", Nil), ("q", Nil)))
    Digraph.fromString("[p>q, m>q, k, p>m]") shouldEqual g
  }

  test("80 convert labeled digraph to human-friendly form with labels") {
    val g = Digraph.adjacentLabel(List(('m', List(('q', 7))), ('p', List(('m', 5), ('q', 9))), ('k', Nil), ('q', Nil)))
    g.toString shouldEqual "[k, p>q/9, p>m/5, m>q/7]"
  }

  test("80 convert human-friendly form with labels to labeled digraph") {
    val g = Digraph.adjacentLabel(List(("m", List(("q", "7"))), ("p", List(("m", "5"), ("q", "9"))), ("k", Nil), ("q", Nil)))
    Digraph.fromStringLabel("[p>q/9, m>q/7, k, p>m/5]") shouldEqual g
  }

  test("81 find all acyclic paths from one node to another in a graph") {
    val g = Digraph.fromStringLabel("[p>q/9, m>q/7, k, p>m/5]")
    g.findPaths("p", "p") shouldEqual List(List("p"))
    g.findPaths("p", "k") shouldBe empty
    g.findPaths("p", "q") should contain theSameElementsAs List(List("p", "q"), List("p", "m", "q"))
  }

  test("82 find all cycles starting at a given node in a graph") {
    val g = Graph.fromString("[b-c, f-c, g-h, d, f-b, k-f, h-g]")
    g.findCycles("d") shouldBe empty
    g.findCycles("g") shouldBe empty
    g.findCycles("f") should contain theSameElementsAs List(List("f", "c", "b", "f"), List("f", "b", "c", "f"))
  }

  test("83 find all spanning trees for a given graph") {
    Graph.fromString("[a]").spanningTrees shouldEqual List(Graph.fromString("[a]"))
    Graph.fromString("[a, b-c]").spanningTrees shouldBe empty
    Graph.fromString("[a-b, b-c, a-c]").spanningTrees should contain theSameElementsAs
      List("[a-b, b-c]", "[a-c, b-c]", "[a-b, a-c]").map(Graph.fromString)
  }

  test("83 find all spanning trees for the bigger graph given in the problem statement") {
    val g = Graph.term(
      List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h),
      List(('a, 'b), ('a, 'd), ('b, 'c), ('b, 'e), ('c, 'e), ('d, 'e), ('d, 'f), ('d, 'g), ('e, 'h), ('f, 'g), ('g, 'h)))
    g.spanningTrees.size shouldBe 112
  }

  test("83 check if a graph is a tree") {
    Graph.fromString("[a-b, b-c, a-c]").isTree shouldBe false
    Graph.fromString("[a-b, b-c]").isTree shouldBe true
  }

  test("83 check if a graph is connected") {
    Graph.fromString("[a-b, b-c, a-c]").isConnected shouldBe true
    Graph.fromString("[a-b, b-c, a-c, d]").isConnected shouldBe false
  }

  test("84 finding the minimum spanning tree for an unconnected graph should throw an exception") {
    a[NoSuchElementException] should be thrownBy Graph.termLabel(List('a, 'b, 'c), List(('b, 'c, 1))).minimalSpanningTree
  }

  test("84 find the minimum spanning tree for a given graph") {
    Graph.termLabel(List('a), List.empty[(Symbol, Symbol, Int)]).minimalSpanningTree shouldEqual
      Graph.termLabel(List('a), List.empty[(Symbol, Symbol, Int)])
    Graph.termLabel(List('a, 'b, 'c), List(('a, 'b, 1), ('b, 'c, 2), ('a, 'c, 3))).minimalSpanningTree shouldEqual
      Graph.termLabel(List('a, 'b, 'c), List(('a, 'b, 1), ('b, 'c, 2)))
  }

  test("84 find the minimum spanning tree for the bigger graph given in the problem statement") {
    val g = Graph.termLabel(
      List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h),
      List(('a, 'b, 5), ('a, 'd, 3), ('b, 'c, 2), ('b, 'e, 4), ('c, 'e, 6), ('d, 'e, 7), ('d, 'f, 4), ('d, 'g, 3), ('e, 'h, 5), ('f, 'g, 4), ('g, 'h, 1)))
    val candidates = List(
      Graph.termLabel(
        List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h),
        List(('a, 'b, 5), ('a, 'd, 3), ('b, 'e, 4), ('b, 'c, 2), ('d, 'g, 3), ('f, 'g, 4), ('g, 'h, 1))),
      Graph.termLabel(
        List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h),
        List(('a, 'b, 5), ('a, 'd, 3), ('b, 'e, 4), ('b, 'c, 2), ('d, 'g, 3), ('d, 'f, 4), ('g, 'h, 1))))
    candidates should contain(g.minimalSpanningTree)
  }

  test("85 check if two graphs are isomorphic") {
    val g1 = Graph.fromString("[a-b, c]")
    g1.isIsomorphicTo(Graph.fromString("[8, 5-7]")) shouldBe true
    g1.isIsomorphicTo(Graph.fromString("[a-b, b-c]")) shouldBe false
    Graph.fromString("[a-b, b-c, c-d]").isIsomorphicTo(Graph.fromString("[a-b, b-c, b-d]")) shouldBe false
  }

  test("86 determine the degree of a given node") {
    Graph.fromString("[a-b, b-c, a-c, a-d]").nodes("a").degree shouldBe 3
  }

  test("86 list all nodes of a graph sorted according to decreasing degree") {
    Graph.fromString("[a-b, b-c, a-c, a-d]").nodesByDegree shouldEqual List("a", "c", "b", "d")
  }

  test("86 graph colouring: paint the nodes of a graph in such a way that adjacent nodes have different colours") {
    Graph.fromString("[a-b, b-c, a-c, a-d]").colorNodes should contain theSameElementsAs
      List(("a", 1), ("c", 2), ("b", 3), ("d", 2))
  }

  test("87 generate a depth-first order graph traversal sequence") {
    Graph.fromString("[a]").nodesByDepthFrom("a") shouldEqual List("a")
    Graph.fromString("[a-b, b-c, e, a-c, a-d]").nodesByDepthFrom("e") shouldEqual List("e")
    Graph.fromString("[a-b, b-c, e, a-c, a-d]").nodesByDepthFrom("d") shouldEqual List("c", "b", "a", "d")
  }

  test("88 split a graph into connected components") {
    Graph.fromString("[a]").splitGraph shouldEqual List(Graph.fromString("[a]"))
    Graph.fromString("[a-b, b-c]").splitGraph shouldEqual List(Graph.fromString("[a-b, b-c]"))
    Graph.fromString("[a-b, c]").splitGraph should contain theSameElementsAs List("[a-b]", "[c]").map(Graph.fromString)
  }

  test("89 determine whether a given graph is bipartite") {
    Digraph.fromString("[a>b, c>a, d>b]").isBipartite shouldBe true
    Graph.fromString("[a-b, b-c, c-a]").isBipartite shouldBe false
    Graph.fromString("[a-b, b-c, d]").isBipartite shouldBe true
    Graph.fromString("[a-b, b-c, d, e-f, f-g, g-e, h]").isBipartite shouldBe false
  }
}
