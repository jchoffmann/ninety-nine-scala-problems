/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#math
  */
class S99Int(val i: Int) {
  // P31
  def isPrime: Boolean = ???

  // P33
  def isCoprimeTo(y: Int): Boolean = ???

  // P34
  def totient: Int = ???

  // P35
  def primeFactors: List[Int] = ???

  // P36
  def primeFactorMultiplicity: List[(Int, Int)] = ???

  // P37
  def totientImproved: Int = ???

  // P40
  def goldbach: (Int, Int) = ???
}

object S99Int {
  implicit def int2S99Int(i: Int): S99Int = new S99Int(i)

  // P32
  def gcd(x: Int, y: Int): Int = ???

  // P39
  def listPrimesInRange(fromTo: Range): List[Int] = ???

  // P41
  def goldbachList(fromTo: Range): Map[Int, (Int, Int)] = ???

  def goldbachListLimited(fromTo: Range, limit: Int): Map[Int, (Int, Int)] = ???
}
