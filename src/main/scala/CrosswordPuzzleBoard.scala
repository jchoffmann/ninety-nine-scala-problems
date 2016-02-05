/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#misc
  */
class CrosswordPuzzleBoard

object CrosswordPuzzleBoard {
  implicit def string2Board(s: String): CrosswordPuzzleBoard = ???

  // P99
  def solve(b: CrosswordPuzzleBoard, words: List[String]): Option[CrosswordPuzzleBoard] = ???
}
