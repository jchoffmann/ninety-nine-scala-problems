/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#misc
  */
class SyntaxChecker(s: String) {
  // P96
  def isIdentifier: Boolean = ???
}

object SyntaxChecker {
  implicit def string2SyntaxChecker(s: String): SyntaxChecker = new SyntaxChecker(s)
}
