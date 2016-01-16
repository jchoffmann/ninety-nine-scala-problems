import S99Int._
import org.scalatest.FunSuite

class ArithmeticSuite extends FunSuite {
  test("31 determine whether a given integer number is prime") {
    assert((1 to 20).map(_.isPrime).toList ===
      List(false, true, true, false, true, false, true, false, false, false, true, false, true, false, false, false, true, false, true, false))
  }

  test("32 determine the greatest common divisor of two positive integer numbers") {
    assert(gcd(36, 63) === 9)
  }

  test("33 determine whether two positive integer numbers are coprime") {
    assert(35.isCoprimeTo(64))
  }

  test("34 calculate Euler's totient function phi[m]") {
    assert(10.totient === 4)
  }

  test("35 determine the prime factors of a given positive integer") {
    assert(315.primeFactors === List(3, 3, 5, 7))
  }

  test("36 determine the prime factors of a given positive integer and their multiplicity") {
    assert(315.primeFactorMultiplicity === List((3, 2), (5, 1), (7, 1)))
  }

  test("37 calculate Euler's totient function phi[m] improved") {
    assert(10.totientImproved === 4)
  }

  test("39 construct a list of all prime numbers in a given range") {
    assert(listPrimesInRange(7 to 31) === List(7, 11, 13, 17, 19, 23, 29, 31))
  }

  test("40 find the two prime numbers that sum up to a given even integer") {
    assert(28.goldbach ===(5, 23))
  }

  test("41 list all even numbers and their Goldbach composition for a given range") {
    assert(goldbachList(9 to 20) === Map(10 ->(3, 7), 12 ->(5, 7), 14 ->(3, 11), 16 ->(3, 13), 18 ->(5, 13), 20 ->(3, 17)))
  }

  test("42 list all even numbers and their Goldbach composition (each part being bigger than a given minimum) for a given range") {
    assert(goldbachListLimited(1 to 2000, 50) === Map(992 ->(73, 919), 1382 ->(61, 1321), 1856 ->(67, 1789), 1928 ->(61, 1867)))
  }
}
