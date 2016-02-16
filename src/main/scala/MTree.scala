import scala.util.parsing.combinator.RegexParsers

/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#mtrees
  */
case class MTree[+T](value: T, children: List[MTree[T]]) {
  // P70
  override def toString: String = value + children.mkString + "^"

  def nodeCount: Int = 1 + children.map(_.nodeCount).sum

  // P71
  def internalPathLength: Int = nodeCount - 1 + children.map(_.internalPathLength).sum

  // P72
  def postorder: List[T] = children.flatMap(_.postorder) ++ List(value)

  // P73
  def toLispyString: String =
    if (children.isEmpty) value.toString
    else s"($value ${children.map(_.toLispyString).mkString(" ")})"
}

object MTree {
  def apply[T](value: T) = new MTree(value, List())

  // P70
  implicit def string2MTree(s: String): MTree[String] = MTreeFromStringParser.parseAll(MTreeFromStringParser.tree, s) match {
    case MTreeFromStringParser.Success(tree, _) => tree
    case MTreeFromStringParser.NoSuccess(msg, _) => throw new IllegalArgumentException(s"Cannot parse tree: $msg")
  }

  def fromString(s: String): MTree[String] = string2MTree(s)

  // P73
  def fromLispyString(s: String): MTree[String] = MTreeFromLispyStringParser.parseAll(MTreeFromLispyStringParser.tree, s) match {
    case MTreeFromLispyStringParser.Success(tree, _) => tree
    case MTreeFromLispyStringParser.NoSuccess(msg, _) => throw new IllegalArgumentException(s"Cannot parse tree: $msg")
  }
}

object MTreeFromStringParser extends RegexParsers {
  def tree: Parser[MTree[String]] = label ~ rep(tree) <~ "^" ^^ { case value ~ children => MTree(value, children) }

  def label: Parser[String] = "\\w".r
}

object MTreeFromLispyStringParser extends RegexParsers {
  def tree: Parser[MTree[String]] = node | leaf

  def node: Parser[MTree[String]] = ("(" ~> label) ~ (rep1(tree) <~ ")") ^^ { case value ~ children => MTree(value, children) }

  def leaf: Parser[MTree[String]] = label ^^ (MTree(_))

  def label: Parser[String] = "\\w+".r
}