import ArithmeticPuzzle._
import CrosswordPuzzleBoard._
import EightQueens._
import EnglishNumberWords._
import KnightsTour._
import RegularGraphs._
import SudokuBoard._
import SyntaxChecker._
import VonKochConjecture._
import org.scalatest.{FunSuite, Inspectors, Matchers}

class PuzzleSuite extends FunSuite with Matchers with Inspectors {
  test("90 generate all configurations of the eight queens problem") {
    val result = eightQueens(8)
    result should contain(List(4, 2, 7, 3, 6, 8, 5, 1))
    // Number of solutions: See https://oeis.org/A000170 (92 distinct, 12 fundamental)
    result should have size 92
  }

  test("91 knight jumps from invalid positions should throw exceptions") {
    an[IllegalArgumentException] should be thrownBy jumps(4, (0, 1))
    an[IllegalArgumentException] should be thrownBy jumps(4, (5, 1))
    an[IllegalArgumentException] should be thrownBy jumps(4, (1, 0))
    an[IllegalArgumentException] should be thrownBy jumps(4, (1, 5))
  }

  test("91 there are no valid knight jumps on NxN boards where N=1,2") {
    jumps(1, (1, 1)) shouldBe empty
    jumps(2, (1, 1)) shouldBe empty
  }

  test("91 knight jumps on a 3x3 board") {
    jumps(3, (2, 2)) shouldBe empty
    jumps(3, (1, 1)) should contain theSameElementsAs List((3, 2), (2, 3))
    jumps(3, (1, 2)) should contain theSameElementsAs List((3, 1), (3, 3))
  }

  test("91 knight jumps on a 5x5 board") {
    jumps(5, (3, 3)) should contain theSameElementsAs List((2, 1), (4, 1), (5, 2), (5, 4), (4, 5), (2, 5), (1, 4), (1, 2))
  }

  private def isKnightMove(n: Int, p1: Position, p2: Position) = jumps(n, p1) contains p2

  private def isKnightsTour(n: Int, t: Tour): Boolean =
    t.size == n * n && // correct length
      ((for (x <- 1 to n; y <- 1 to n) yield (x, y)) diff t).isEmpty && // cover all squares
      t.sliding(2).forall { ps => isKnightMove(n, ps.head, ps(1)) } // every move is a knight move

  private def isClosedKnightsTour(n: Int, t: Tour) = isKnightsTour(n, t) && isKnightMove(n, t.last, t.head)

  test("91 there are no knight's tours on NxN boards where N=1,2,3,4") {
    knightsTourComplete(1) shouldBe empty
    knightsTourComplete(2) shouldBe empty
    knightsTourComplete(3) shouldBe empty
    knightsTourComplete(4) shouldBe empty
    knightsTour(1) shouldBe empty
    knightsTour(2) shouldBe empty
    knightsTour(3) shouldBe empty
    knightsTour(4) shouldBe empty
  }

  test("91 generate all knight's tours on a 5x5 board") {
    val result = knightsTourComplete(5)
    // An open tour: See http://ibmathsresources.com/2013/11/19/knights-tour/
    val openTour = List(
      (1, 5), (3, 4), (5, 5), (4, 3), (3, 5),
      (5, 4), (4, 2), (2, 1), (1, 3), (2, 5),
      (4, 4), (5, 2), (3, 3), (1, 4), (2, 2),
      (4, 1), (5, 2), (4, 5), (2, 4), (1, 2),
      (3, 1), (2, 3), (1, 1), (3, 2), (5, 1))
    result should contain(openTour)
    forAll(result) { r => isKnightsTour(5, r) shouldBe true }
    // Number of solutions: See https://oeis.org/A165134
    result should have size 1728
  }

  test("91 generate one knight's tour on a 5x5 board") {
    isKnightsTour(5, knightsTour(5).get) shouldBe true
  }

  test("91 there are no closed knight's tours on NxN boards where N=1,2,3,4") {
    knightsTourCompleteClosed(1) shouldBe empty
    knightsTourCompleteClosed(2) shouldBe empty
    knightsTourCompleteClosed(3) shouldBe empty
    knightsTourCompleteClosed(4) shouldBe empty
    knightsTourClosed(1) shouldBe empty
    knightsTourClosed(2) shouldBe empty
    knightsTourClosed(3) shouldBe empty
    knightsTourClosed(4) shouldBe empty
  }

  test("91 generate all closed knight's tours on a 6x6 board") {
    val result = knightsTourCompleteClosed(6)
    // A closed tour: See http://www.mayhematics.com/t/6a.htm
    val closedTour = List(
      (1, 1), (3, 2), (5, 1), (4, 3), (6, 2), (4, 1),
      (2, 2), (1, 4), (3, 5), (1, 6), (2, 4), (1, 2),
      (3, 3), (2, 1), (1, 3), (2, 5), (4, 6), (5, 4),
      (6, 6), (4, 5), (2, 6), (3, 4), (1, 5), (3, 6),
      (5, 5), (6, 3), (4, 2), (6, 1), (5, 3), (6, 5),
      (4, 4), (5, 6), (6, 4), (5, 2), (3, 1), (2, 3)
    )
    result should contain(closedTour)
    forAll(result) { r => isClosedKnightsTour(6, r) shouldBe true }
    // Number of solutions: See https://oeis.org/A001230
    result should have size 9862
  }

  test("91 generate one closed knight's tour on a 6x6 board") {
    isClosedKnightsTour(6, knightsTourClosed(6).get) shouldBe true
  }

  test("92 label a graph according to the rules stated in von Koch's conjecture") {
    val g = Graph.fromString("[a-b, a-d, a-g, b-c, b-e, e-f]")
    labelGraph(g) shouldEqual Graph.fromStringLabel("[1-7/6, 2-7/5, 3-5/2, 3-6/3, 3-7/4, 4-5/1]")
  }

  test("93 generate all equations that solve the arithmetic puzzle") {
    val result = arithmeticPuzzle(List(2, 3, 5, 7, 11))
    result should contain("2-3+5+7=11")
    result should contain("2=(3*5+7)/11")
    result should have size 12
  }

  test("94 generate all 3-regular simple graphs with 6 nodes") {
    // N = 6  K = 3   2 solutions  (38662 inferences)
    val result = regularGraphs(6, 3)
    val g1 = Graph.fromString("[1-2, 1-3, 1-4, 2-3, 2-5, 3-6, 4-5, 4-6, 5-6]")
    val g2 = Graph.fromString("[1-2, 1-3, 1-4, 2-5, 2-6, 3-5, 3-6, 4-5, 4-6]")

    (result.head.isIsomorphicTo(g1) && result(1).isIsomorphicTo(g2) ||
      result(1).isIsomorphicTo(g2) && result.head.isIsomorphicTo(g1)) shouldBe true

    // TODO Add all of these as tests? http://aperiodic.net/phil/scala/s-99/p94.txt
  }

  test("95 convert (non-negative) integer numbers to full english words") {
    fullWords(175) shouldEqual "one-seven-five"
  }

  test("96 list of strings that are legal identifiers") {
    "".isIdentifier shouldBe false
    "1".isIdentifier shouldBe false
    "_".isIdentifier shouldBe false
    "_1".isIdentifier shouldBe false
    "_a".isIdentifier shouldBe false
    "a_".isIdentifier shouldBe false
  }

  test("96 list of strings that are not legal identifiers") {
    "a".isIdentifier shouldBe true
    "a1b2".isIdentifier shouldBe true
    "a_b".isIdentifier shouldBe true
    "a_1".isIdentifier shouldBe true
    "a12_3bcd_e4".isIdentifier shouldBe true
  }

  test("97 find the solution for an (incomplete) starting sudoku board") {
    val board =
      """.  .  4 | 8  .  . | .  1  7
        |6  7  . | 9  .  . | .  .  .
        |5  .  8 | .  3  . | .  .  4
        |--------+---------+--------
        |3  .  . | 7  4  . | 1  .  .
        |.  6  9 | .  .  . | 7  8  .
        |.  .  1 | .  6  9 | .  .  5
        |--------+---------+--------
        |1  .  . | .  8  . | 3  .  6
        |.  .  . | .  .  6 | .  9  1
        |2  4  . | .  .  1 | 5  .  .""".stripMargin

    val solution =
      """9  3  4 | 8  2  5 | 6  1  7
        |6  7  2 | 9  1  4 | 8  5  3
        |5  1  8 | 6  3  7 | 9  2  4
        |--------+---------+--------
        |3  2  5 | 7  4  8 | 1  6  9
        |4  6  9 | 1  5  3 | 7  8  2
        |7  8  1 | 2  6  9 | 4  3  5
        |--------+---------+--------
        |1  9  7 | 5  8  2 | 3  4  6
        |8  5  3 | 4  7  6 | 2  9  1
        |2  4  6 | 3  9  1 | 5  7  8""".stripMargin

    SudokuBoard.solve(board).get shouldEqual SudokuBoard.string2Board(solution)
  }

  test("98 find the solution for a given Nonogram puzzle") {
    /*
        |_|X|X|X|_|_|_|_| 3
        |X|X|_|X|_|_|_|_| 2 1
        |_|X|X|X|_|_|X|X| 3 2
        |_|_|X|X|_|_|X|X| 2 2
        |_|_|X|X|X|X|X|X| 6
        |X|_|X|X|X|X|X|_| 1 5
        |X|X|X|X|X|X|_|_| 6
        |_|_|_|_|X|_|_|_| 1
        |_|_|_|X|X|_|_|_| 2
         1 3 1 7 5 3 4 3
         2 1 5 1
     */
    def topToBottom = List(List(3), List(2, 1), List(3, 2), List(2, 2), List(6), List(1, 5), List(6), List(1), List(2))
    def leftToRight = List(List(1, 2), List(3, 1), List(1, 5), List(7, 1), List(5), List(3), List(4), List(3))
    def solution = List(List(2, 3, 4), List(1, 2, 4), List(2, 3, 4, 7, 8), List(3, 4, 7, 8), List(3, 4, 5, 6, 7, 8), List(1, 3, 4, 5, 6, 7), List(1, 2, 3, 4, 5, 6), List(5), List(4, 5))
    Nonograms.solve(topToBottom, leftToRight) shouldEqual solution
  }

  test("99 find the solution for a given crossword puzzle (a)") {
    val words =
      """LINUX
        |PROLOG
        |PERL
        |ONLINE
        |GNU
        |XML
        |NFS
        |SQL
        |EMACS
        |WEB
        |MAC""".stripMargin.split('\n').toList

    val board =
      """......  .
        |. .  .  .
        |. ..... .
        |. . . ...
        |  . ... .
        | ...     """.stripMargin

    val solution =
      """PROLOG  E
        |E N  N  M
        |R LINUX A
        |L I F MAC
        |  N SQL S
        | WEB     """.stripMargin

    CrosswordPuzzleBoard.solve(board, words).get shouldEqual CrosswordPuzzleBoard.string2Board(solution)
  }

  test("99 find the solution for a given crossword puzzle (b)") {
    val words =
      """AAL
        |DER
        |TAL
        |TAT
        |ISEL
        |TELL
        |ZANK
        |ZEUS
        |ALSEN
        |BLASE
        |EOSIN
        |ETTAL
        |KARRE
        |LIANE
        |NEEFS
        |NONNE
        |OSTEN
        |STUHL
        |TIARA
        |ANKARA
        |EGERIA
        |GRANAT
        |HIRTEN
        |MISERE
        |SAMPAN
        |TILSIT
        |WAGGON
        |FORTUNA
        |ITALIEN
        |MADONNA
        |MELASSE
        |REAUMUR
        |RIVIERA
        |SEKUNDE
        |SERBIEN
        |SKELETT
        |SKRUPEL
        |STETTIN
        |STOIKER
        |HANNIBAL
        |REGISTER
        |RELIGION
        |STANNIOL
        |TRUEFFEL
        |UNTERTAN
        |USAMBARA
        |VENDETTA
        |TUEBINGEN
        |TURKMENEN
        |ALLENSTEIN
        |ATTRAKTION
        |BRIEFTAUBE
        |TATTERSALL
        |PROTEKTORAT
        |TEMPERAMENT
        |KRANKENKASSE
        |CHRONOGRAPHIE
        |TRAUBENZUCKER
        |WALZER""".stripMargin.split('\n').toList

    val board =
      """. ......... .............
        |. .       . .         . .
        |. ...........   ....... .
        |. .       .       .   . .
        |...... .... . ......  . .
        |. .         . .   .   . .
        |. . ......  ..... .......
        |. . .  .  ... .   .   .
        |........  .   .   . .....
        |. . .  .  .   . .   . .
        |.   .     . ....... . .
        | ......   .   . .  .....
        |  .     . .     .   .   .
        |  .  ......... ........ .
        |  . . . . .     .     . .
        | .... . .  . .......  . .
        |. . . . .  .   .    ... .
        |. . . . . ..........  . .
        |..... . .  .   .    . . .
        |.     .    . ... .  . . .
        |. ..........  .  .  . . .
        |. .    .      .  .  . . .
        |.....  ........ ....... .
        |  .    .      .  .  .   .
        |........   .......  .....""".stripMargin

    CrosswordPuzzleBoard.solve(board, words).isEmpty shouldBe false
    // TODO Check for correct solution
  }

  test("99 find the solution for a given crossword puzzle (c)") {
    val words =
      """AAL
        |DER
        |TAL
        |TAT
        |ISEL
        |TELL
        |ZANK
        |ZEUS
        |ALSEN
        |BLASE
        |EOSIN
        |ETTAL
        |KARREN
        |LIANE
        |NEEFS
        |NONNE
        |OSTEN
        |STUHL
        |TIARA
        |ANKARA
        |EGERIA
        |GRANAT
        |HIRTEN
        |MISERE
        |SAMPAN
        |TILSIT
        |WAGGON
        |FORTUNA
        |ITALIEN
        |MADONNA
        |MELASSE
        |REAUMUR
        |RIVIERA
        |SEKUNDE
        |SERBIEN
        |SKELETT
        |SKRUPEL
        |STETTIN
        |STOIKER
        |HANNIBAL
        |REGISTER
        |RELIGION
        |STANNIOL
        |TRUEFFEL
        |UNTERTAN
        |USAMBARA
        |VENDETTA
        |TUEBINGEN
        |TURKMENEN
        |ALLENSTEIN
        |ATTRAKTION
        |BRIEFTAUBE
        |TATTERSALL
        |PROTEKTORAT
        |TEMPERAMENT
        |KRANKENKASSE
        |CHRONOGRAPHIE
        |TRAUBENZUCKER
        |WALZE""".stripMargin.split('\n').toList

    val board =
      """. ......... .............
        |. .       . .         . .
        |. ...........   ....... .
        |. .       .       .   . .
        |...... .... . ......  . .
        |. .         . .   .   . .
        |. . ......  ..... .......
        |. . .  .  ... .   .   .
        |........  .   .   . .....
        |. . .  .  .   . .   . .
        |.   .     . ....... . .
        | ......   .   . .  .....
        |  .     . .     .   .   .
        |  .  ......... ........ .
        |  . . . . .     .     . .
        | .... . .  . .......  . .
        |. . . . .  .   .    ... .
        |. . . . . ..........  . .
        |..... . .  .   .    . . .
        |.     .    . ... .  . . .
        |. ..........  .  .  . . .
        |. .    .      .  .  . . .
        |.....  ........ ....... .
        |  .    .      .  .  .   .
        |........   .......  .....""".stripMargin

    CrosswordPuzzleBoard.solve(board, words).isEmpty shouldBe true
  }

  test("99 find the solution for a given crossword puzzle (d)") {
    val words =
      """BANI
        |HAUS
        |NETZ
        |LENA
        |ANKER
        |ARIEL
        |GASSE
        |INNEN
        |ORADE
        |SESAM
        |SIGEL
        |ANGOLA
        |AZETAT
        |EKARTE
        |NATTER
        |NENNER
        |NESSEL
        |RITTER
        |SOMMER
        |TAUNUS
        |TRANIG
        |AGENTUR
        |ERRATEN
        |ERREGER
        |GELEISE
        |HAENDEL
        |KAROSSE
        |MANAGER
        |OSTEREI
        |SIDERIT
        |TERRIER
        |ANATOMIE
        |ANPASSEN
        |BARKASSE
        |BEDANKEN
        |DEKADENT
        |EINLADEN
        |ERLASSEN
        |FRAGMENT
        |GARANTIE
        |KRAWATTE
        |MEISTERN
        |REAKTION
        |TENTAKEL
        |TRIANGEL
        |UEBERALL
        |VERGEBEN
        |AFRIKANER
        |BESTELLEN
        |BULLAUGEN
        |SANTANDER
        |VERBERGEN
        |ALLENSTEIN
        |AUSTRALIEN
        |BETEILIGEN
        |NATALITAET
        |OBERHAUSEN
        |UNTERSTAND
        |LEUMUND""".stripMargin.split('\n').toList

    val board =
      """........ ........ .......
        |.   .    .   .    . .   .
        |. . . ..........  . .   .
        |.......  .   . . ........
        |. . . .  . . . .  . . . .
        |. . . . ...... .    . . .
        |. . . .    . ........ .
        |. . ...... . . . .  . . .
        |. .  .  .  .   . .    . .
        |......  ...... . . ......
        |     .  .  . . . . .  . .
        |....... .  . . .......  .
        |.    .  .    .     .    .
        |. .  ....... ........   .
        |. .     .    .    .     .
        |...... . ....... ........
        |. .    . .        . .   .
        |. . .........   . . .
        |. .    . .  .   . . .....
        |  .    .  ....... . .   .
        |..........  .   .    .  .
        |. .    .  .  .........  .
        |.  ......... .  .    .  .
        |.      .  .  .  .    .  .
        |........  ......... .....""".stripMargin

    CrosswordPuzzleBoard.solve(board, words).isEmpty shouldBe false
    // TODO Check for correct solution
  }
}
