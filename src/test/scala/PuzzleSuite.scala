import ArithmeticPuzzle._
import CrosswordPuzzleBoard._
import EightQueens._
import EnglishNumberWords._
import KnightsTour._
import RegularGraphs._
import SudokuBoard._
import VonKochConjecture._
import org.scalatest.FunSuite

class PuzzleSuite extends FunSuite {
  test("90 generate all configurations of the eight queens problem") {
    val result = eightQueens(8)
    assert(result.contains(List(4, 2, 7, 3, 6, 8, 5, 1)))
    // Number of solutions: See https://oeis.org/A000170 (92 distinct, 12 fundamental)
    assert(result.size === 92)
  }

  test("91 valid knight jumps from invalid positions should throw an exception") {
    intercept[IllegalArgumentException] {
      jumps(4, (0, 1))
    }
    intercept[IllegalArgumentException] {
      jumps(4, (5, 1))
    }
    intercept[IllegalArgumentException] {
      jumps(4, (1, 0))
    }
    intercept[IllegalArgumentException] {
      jumps(4, (1, 5))
    }
  }

  test("91 there are no valid knight jumps for small n") {
    assert(jumps(1, (1, 1)).isEmpty)
    assert(jumps(2, (1, 1)).isEmpty)
  }

  test("91 valid knights jumps") {
    assert(jumps(3, (2, 2)).isEmpty)
    assert(jumps(3, (1, 1)) === List((3, 2), (2, 3)))
    assert(jumps(3, (1, 2)) === List((3, 1), (3, 3)))
    assert(jumps(5, (3, 3)) === List((2, 1), (4, 1), (5, 2), (5, 4), (4, 5), (2, 5), (1, 4), (1, 2)))
  }

  private def isKnightMove(n: Int, p1: Position, p2: Position) = jumps(n, p1) contains p2

  private def isKnightsTour(n: Int, t: Tour): Boolean =
    t.size == n * n && // correct length
      ((for (x <- 1 to n; y <- 1 to n) yield (x, y)) diff t).isEmpty && // cover all squares
      t.sliding(2).forall { ps => isKnightMove(n, ps.head, ps(1)) } // every move is a knight move

  private def isClosedKnightsTour(n: Int, t: Tour) = isKnightsTour(n, t) && isKnightMove(n, t.last, t.head)

  test("91 there are no nxn knight's tours for small n (1)") {
    assert(knightsTourComplete(2).isEmpty)
    assert(knightsTourComplete(3).isEmpty)
    assert(knightsTourComplete(4).isEmpty)
  }

  test("91 generate all 5x5 knight's tours") {
    val result = knightsTourComplete(5)
    // An open tour: See http://ibmathsresources.com/2013/11/19/knights-tour/
    val openTour = List(
      (1, 5), (3, 4), (5, 5), (4, 3), (3, 5),
      (5, 4), (4, 2), (2, 1), (1, 3), (2, 5),
      (4, 4), (5, 2), (3, 3), (1, 4), (2, 2),
      (4, 1), (5, 2), (4, 5), (2, 4), (1, 2),
      (3, 1), (2, 3), (1, 1), (3, 2), (5, 1))
    assert(result.contains(openTour))
    assert(result.forall(isKnightsTour(5, _)))
    // Number of solutions: See https://oeis.org/A165134
    assert(result.size === 1728)
  }

  test("91 there are no nxn knight's tours for small n (2)") {
    assert(knightsTour(2).isEmpty)
    assert(knightsTour(3).isEmpty)
    assert(knightsTour(4).isEmpty)
  }

  test("91 generate one 5x5 knight's tour") {
    assert(isKnightsTour(5, knightsTour(5).get))
  }

  test("91 there are no closed nxn knight's tours for small n (1)") {
    assert(knightsTourCompleteClosed(2).isEmpty)
    assert(knightsTourCompleteClosed(4).isEmpty)
  }

  test("91 generate all closed 6x6 knight's tours") {
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
    assert(result.contains(closedTour))
    assert(result.forall(isClosedKnightsTour(6, _)))
    // Number of solutions: See https://oeis.org/A001230
    assert(result.size === 9862)
  }

  test("91 there are no closed nxn knight's tours for small n (2)") {
    assert(knightsTourClosed(2).isEmpty)
    assert(knightsTourClosed(4).isEmpty)
  }

  test("91 generate one closed 6x6 knight's tour") {
    assert(isClosedKnightsTour(6, knightsTourClosed(6).get))
  }

  test("92 von Koch's conjecture") {
    val g = Graph.fromString("[a-b, a-d, a-g, b-c, b-e, e-f]")
    assert(labelGraph(g) === Graph.fromStringLabel("[1-7/6, 2-7/5, 3-5/2, 3-6/3, 3-7/4, 4-5/1]"))
  }

  test("93 generate all equations that solve the arithmetic puzzle") {
    val result = arithmeticPuzzle(List(2, 3, 5, 7, 11))
    assert(result.contains("2-3+5+7=11"))
    assert(result.contains("2=(3*5+7)/11"))
    assert(result.size === 12)
  }

  test("94 generate all K-regular simple graphs with N nodes") {
    // N = 6  K = 3   2 solutions  (38662 inferences)
    val result = regularGraphs(6, 3)
    val g1 = Graph.fromString("[1-2, 1-3, 1-4, 2-3, 2-5, 3-6, 4-5, 4-6, 5-6]")
    val g2 = Graph.fromString("[1-2, 1-3, 1-4, 2-5, 2-6, 3-5, 3-6, 4-5, 4-6]")
    assert(result.head.isIsomophicTo(g1) && result(1).isIsomophicTo(g2) ||
      result(1).isIsomophicTo(g2) && result.head.isIsomophicTo(g1))
    // TODO Add all of these as tests? http://aperiodic.net/phil/scala/s-99/p94.txt
  }

  test("95 convert (non-negative) integer numbers to full english words") {
    assert(fullWords(175) === "one-seven-five")
  }

  test("96 check whether or not a given string is a legal identifier")(pending)

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

    assert(SudokuBoard.solve(board).get === SudokuBoard.string2Board(solution))
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
    assert(Nonograms.solve(topToBottom, leftToRight) === solution)
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

    assert(CrosswordPuzzleBoard.solve(board, words).get === CrosswordPuzzleBoard.string2Board(solution))
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

    assert(CrosswordPuzzleBoard.solve(board, words).isDefined)
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

    assert(CrosswordPuzzleBoard.solve(board, words).isEmpty)
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

    assert(CrosswordPuzzleBoard.solve(board, words).isDefined)
    // TODO Check for correct solution
  }
}
