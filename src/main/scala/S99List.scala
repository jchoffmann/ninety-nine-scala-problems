import scala.annotation.tailrec
import scala.util.Random

/**
  * Exercises: http://aperiodic.net/phil/scala/s-99/#lists
  *
  * For the list exercises, avoid using builtin functions such as length, slice, ...
  */
object S99List {
  // P01
  @tailrec
  def last[T](l: List[T]): T = l match {
    case Nil => throw new NoSuchElementException
    case x :: Nil => x
    case x :: xs => last(xs)
  }

  // P02
  @tailrec
  def penultimate[T](l: List[T]): T = l match {
    case Nil | List(_) => throw new NoSuchElementException
    case List(x, _) => x
    case _ => penultimate(l.tail)
  }

  // P03
  @tailrec
  def nth[T](pos: Int, l: List[T]): T = {
    require(pos >= 0)
    if (l.isEmpty) throw new NoSuchElementException
    else if (pos == 0) l.head
    else nth(pos - 1, l.tail)
  }

  // P04
  def length[T](l: List[T]): Int = l.foldLeft(0)((x, _) => x + 1)

  // P05
  def reverse[T](l: List[T]): List[T] = {
    @tailrec
    def reverseR(ls: List[T], accu: List[T]): List[T] = ls match {
      case Nil => accu
      case x +: xs => reverseR(xs, x +: accu)
    }
    reverseR(l, List.empty)
  }

  // P06
  def isPalindrome[T](l: List[T]): Boolean = l == reverse(l)

  // P07
  def flatten(l: List[Any]): List[Any] = l flatMap {
    case xs: List[_] => flatten(xs)
    case xs => List(xs)
  }

  // P08
  def compress[T](l: List[T]): List[T] =
    l.foldRight(List.empty[T])((x: T, accu: List[T]) => accu match {
      case `x` +: _ => accu
      case _ => x +: accu
    })

  // P09
  def pack[T](l: List[T]): List[List[T]] =
    l.foldRight(List.empty[List[T]])((x: T, accu: List[List[T]]) => accu match {
      case (a@(`x` +: xs)) +: as => (x +: a) +: as
      case _ => List(x) +: accu
    })

  // P10
  def encode[T](l: List[T]): List[(Int, T)] = pack(l) map (ls => (length(ls), ls.head))

  // P11
  def encodeModified[T](l: List[T]): List[Any] = encode(l) map {
    case (1, x) => x
    case ls => ls
  }

  // P12
  def decode[T](l: List[(Int, T)]): List[T] = l flatMap { case (i, x) => List.fill(i)(x) }

  // P13
  def encodeDirect[T](l: List[T]): List[(Int, T)] =
    l.foldRight(List.empty[(Int, T)])((x: T, accu: List[(Int, T)]) => accu match {
      case (i, `x`) +: xs => (i + 1, x) +: xs
      case _ => (1, x) +: accu
    })

  // P14
  def duplicate[T](l: List[T]): List[T] = l.foldRight(List.empty[T])((x: T, accu: List[T]) => x +: x +: accu)

  // P15
  def duplicateN[T](n: Int, l: List[T]): List[T] =
    l.foldRight(List.empty[T])((x: T, accu: List[T]) => List.fill(n)(x) ++ accu)

  // P16
  def drop[T](n: Int, l: List[T]): List[T] =
    l.zipWithIndex filter { case (x, i) => (i + 1) % n != 0 } map { case (x, i) => x }

  // P17
  def split[T](n: Int, l: List[T]): (List[T], List[T]) = (l take n, l drop n)

  //    l.foldRight((l.size, (List.empty[T], List.empty[T]))) {
  //      case (x, (i, (xs, ys))) =>
  //        if (i > n) (i - 1, (xs, x +: ys))
  //        else (0, (x +: xs, ys))
  //    }._2

  // P18
  def slice[T](from: Int, to: Int, l: List[T]): List[T] = (l drop from) take (to - from)

  //    l.foldRight((l.size, List.empty[T])) {
  //      case (x, (i, xs)) =>
  //        if (from < i && i <= to) (i - 1, x +: xs)
  //        else (i - 1, xs)
  //    }._2

  // P19
  def rotate[T](n: Int, l: List[T]): List[T] =
    if (l.isEmpty) List.empty
    else {
      val from = if (n >= 0) n % l.size else n % l.size + l.size
      slice(from, from + l.size, l ++ l)
    }

  // P20
  def removeAt[T](n: Int, l: List[T]): (List[T], T) = {
    require(n >= 0 && n < l.size)
    ((l take n) ++ (l drop n + 1), l(n))
  }

  // P21
  def insertAt[T](elem: T, n: Int, l: List[T]): List[T] = (l take n) ++ (elem +: (l drop n))

  // P22
  def range(from: Int, to: Int): List[Int] = {
    @tailrec
    def rangeR(n: Int, accu: List[Int]): List[Int] =
      if (n < from) accu
      else rangeR(n - 1, n +: accu)
    rangeR(to, List.empty)
  }

  // P23
  def randomSelect[T](n: Int, l: List[T]): List[T] = {
    require(n >= 0)
    @tailrec
    def randomSelectR(i: Int, ls: List[T], accu: List[T]): List[T] =
      if (i == 0 || ls.isEmpty) accu
      else {
        val (rest, elem) = removeAt(Random.nextInt(ls.size), ls)
        randomSelectR(i - 1, rest, elem +: accu)
      }
    randomSelectR(n, l, List.empty)
  }

  // P24
  def lotto(howMany: Int, outOf: Int): List[Int] = {
    require(howMany > 0 && outOf > 0)
    randomSelect(howMany, range(1, outOf))
  }

  // P25
  def randomPermute[T](l: List[T]): List[T] = randomSelect(l.size, l)

  // P26
  def combinations[T](n: Int, l: List[T]): List[List[T]] = {
    require(n >= 1)
    if (n > l.size) List.empty
    else if (n == l.size) List(l)
    else if (n == 1) l map (List(_))
    else (
      for {
        i <- l.indices
        c <- combinations(n - 1, l drop (i + 1))
      } yield l(i) +: c
      ) (collection.breakOut)
  }

  // P27a
  def group3[T](l: List[T]): List[List[List[T]]] = for {
    twos <- combinations(2, l)
    noTwos = l diff twos
    threes <- combinations(3, noTwos)
  } yield List(twos, threes, noTwos diff threes)

  // P27b
  def group[T](groupSizes: List[Int], l: List[T]): List[List[List[T]]] = {
    require(groupSizes.sum == l.size)
    if (l.isEmpty) List(List())
    else for {
      comb <- combinations(groupSizes.head, l)
      rest = l diff comb
      next <- group(groupSizes.tail, rest)
    } yield comb +: next
  }

  // P28
  def lsort[T](l: List[List[T]]): List[List[T]] = l.sortBy(_.size)

  def lsortFreq[T](l: List[List[T]]): List[List[T]] = l.sortBy(ls => l.count(_.size == ls.size))
}
