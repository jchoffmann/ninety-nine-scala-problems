import scala.util.parsing.combinator.RegexParsers

/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#mtrees
  */
case class MTree[+T](value: T, children: List[MTree[T]]) {
  // P70
  override def toString: String = value + children.mkString + "^"

  def nodeCount: Int = 1 + children.map(_.nodeCount).sum

  // P71
  def internalPathLength: Int = ???

  // P72
  def postorder: List[T] = ???

  // P73
  def toLispyString: String = ???
}

object MTree {
  def apply[T](value: T) = new MTree(value, List())

  // P70
  implicit def string2MTree(s: String): MTree[String] = MTreeParser.parseAll(MTreeParser.tree, s) match {
    case MTreeParser.Success(tree, _) => tree
    case MTreeParser.NoSuccess(msg, _) => throw new IllegalArgumentException(s"Cannot parse tree: $msg")
  }

  def fromString(s: String): MTree[String] = string2MTree(s)

  // P73
  def fromLispyString(s: String): MTree[String] = ???
}

object MTreeParser extends RegexParsers {
  def tree: Parser[MTree[String]] = label ~ rep(tree) ~ "^" ^^ { case value ~ trees ~ "^" => MTree(value, trees) }

  def label: Parser[String] = "\\w".r
}