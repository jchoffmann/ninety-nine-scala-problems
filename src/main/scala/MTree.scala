case class MTree[+T](value: T, children: List[MTree[T]]) {
  override def toString: String = "M(" + value.toString + " {" + children.map(_.toString).mkString(",") + "})"

  // P70
  def nodeCount: Int = ???

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
  implicit def string2MTree(s: String): MTree[String] = ???

  def fromString(s: String): MTree[String] = string2MTree(s)

  // P73
  def fromLispyString(s: String): MTree[String] = ???
}