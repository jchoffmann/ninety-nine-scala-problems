import S99Int._
import S99List.encode

import scala.annotation.tailrec

/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#math
  */
class S99Int(val i: Int) {
  // P31
  def isPrime: Boolean = i > 1 && primes.takeWhile(j => j * j <= i).forall(k => i % k > 0)

  // (i > 1) && ((2 until i) forall (i % _ > 0))

  // P33
  def isCoprimeTo(y: Int): Boolean = gcd(i, y) == 1

  // P34
  def totient: Int = (1 to i) count isCoprimeTo

  // P35
  def primeFactors: List[Int] =
    (2 to i) filter (_.isPrime) find (i % _ == 0) map (p => p +: (i / p).primeFactors) getOrElse List.empty

  // P36
  def primeFactorMultiplicity: List[(Int, Int)] = encode(i.primeFactors) map (_.swap)

  // P37
  def totientImproved: Int = primeFactorMultiplicity.foldLeft(1) {
    case (accu, (p, m)) => accu * (p - 1) * Math.pow(p, m - 1).toInt
  }

  // P40
  def goldbach: (Int, Int) = {
    require(i > 2 && i % 2 == 0)
    listPrimesInRange(2 to i).find(p => (i - p).isPrime).map(p => (p, i - p))
      .getOrElse(throw new IllegalArgumentException)
  }
}

object S99Int {
  implicit def int2S99Int(i: Int): S99Int = new S99Int(i)

  val primes: Stream[Int] = 2 #:: Stream.from(3, 2).filter(_.isPrime)

  // P32
  @tailrec
  def gcd(x: Int, y: Int): Int = if (y == 0) x else gcd(y, x % y)

  // P39
  def listPrimesInRange(fromTo: Range): List[Int] = fromTo.filter(_.isPrime).toList

  // P41
  def goldbachList(fromTo: Range): Map[Int, (Int, Int)] = goldbachListLimited(fromTo, 0)

  def goldbachListLimited(fromTo: Range, lower: Int): Map[Int, (Int, Int)] =
    fromTo.filter(i => i > 2 && i % 2 == 0).map(x => (x, x.goldbach)).collect {
      case (x, (a, b)) if a >= lower && b >= lower => x ->(a, b)
    }.toMap
}
