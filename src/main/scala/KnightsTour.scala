/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#misc
  */
object KnightsTour {
  type Position = (Int, Int)
  type Tour = List[Position]

  // P91
  def jumps(n: Int, p: Position): List[Position] = ???

  def knightsTour(n: Int): Option[Tour] = ???

  def knightsTourClosed(n: Int): Option[Tour] = ???

  def knightsTourComplete(n: Int): List[Tour] = ???

  def knightsTourCompleteClosed(n: Int): List[Tour] = ???
}
