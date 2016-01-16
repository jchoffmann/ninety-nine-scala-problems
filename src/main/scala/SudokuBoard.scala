// P97
class SudokuBoard[T]

object SudokuBoard {
  implicit def string2Board(s: String): SudokuBoard[Int] = ???

  def solve[T](b: SudokuBoard[T]): Option[SudokuBoard[T]] = ???
}
