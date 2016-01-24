// P96
class SyntaxChecker(s: String) {
  def isIdentifier: Boolean = ???
}

object SyntaxChecker {
  implicit def string2SyntaxChecker(s: String): SyntaxChecker = new SyntaxChecker(s)
}
