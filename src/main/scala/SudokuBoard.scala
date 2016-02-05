/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#misc
  */
class SudokuBoard[T]

object SudokuBoard {
  implicit def string2Board(s: String): SudokuBoard[Int] = ???

  // P97
  def solve[T](b: SudokuBoard[T]): Option[SudokuBoard[T]] = ???
}
