object S99List {
  // P01
  def last[T](l: List[T]): T = ???

  // P02
  def penultimate[T](l: List[T]): T = ???

  // P03
  def nth[T](pos: Int, l: List[T]): T = ???

  // P04
  def length[T](l: List[T]): Int = ???

  // P05
  def reverse[T](l: List[T]): List[T] = ???

  // P06
  def isPalindrome[T](l: List[T]): Boolean = ???

  // P07
  def flatten(l: List[Any]): List[Any] = ???

  // P08
  def compress[T](l: List[T]): List[T] = ???

  // P09
  def pack[T](l: List[T]): List[List[T]] = ???

  // P10
  def encode[T](l: List[T]): List[(Int, T)] = ???

  // P11
  def encodeModified[T](l: List[T]): List[Either[(Int, T), T]] = ???

  // P12
  def decode[T](l: List[(Int, T)]): List[T] = ???

  // P13
  def encodeDirect[T](l: List[T]): List[(Int, T)] = ???

  // P14
  def duplicate[T](l: List[T]): List[T] = ???

  // P15
  def duplicateN[T](n: Int, l: List[T]): List[T] = ???

  // P16
  def drop[T](n: Int, l: List[T]): List[T] = ???

  // P17
  def split[T](n: Int, l: List[T]): List[List[T]] = ???

  // P18
  def slice[T](from: Int, to: Int, l: List[T]): List[T] = ???

  // P19
  def rotate[T](n: Int, l: List[T]): List[T] = ???

  // P20
  def removeAt[T](n: Int, l: List[T]): (List[T], T) = ???

  // P21
  def insertAt[T](elem: T, n: Int, l: List[T]): List[T] = ???

  // P22
  def range(from: Int, to: Int): List[Int] = ???

  // P23
  def randomSelect[T](n: Int, l: List[T]): List[T] = ???

  // P24
  def lotto(howMany: Int, outOf: Int): List[Int] = ???

  // P25
  def randomPermute[T](l: List[T]): List[T] = ???

  // P26
  def combinations[T](n: Int, l: List[T]): List[List[T]] = ???

  // P27
  def group3[T](l: List[T]): List[List[List[T]]] = ???

  def group[T](groupSizes: List[Int], l: List[T]): List[List[List[T]]] = ???

  // P28
  def lsort[T](l: List[List[T]]): List[List[T]] = ???

  def lsortFreq[T](l: List[List[T]]): List[List[T]] = ???
}
