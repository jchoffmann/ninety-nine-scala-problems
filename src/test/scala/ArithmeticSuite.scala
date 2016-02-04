import S99Int._
import org.scalatest.{FunSuite, Matchers}

class ArithmeticSuite extends FunSuite with Matchers {
  test("31 determine whether a given integer number is prime") {
    (1 to 20).filter(_.isPrime) shouldEqual List(2, 3, 5, 7, 11, 13, 17, 19)
  }

  test("32 determine the greatest common divisor of two positive integer numbers") {
    gcd(36, 63) shouldBe 9
  }

  test("33 determine whether two positive integer numbers are coprime") {
    35.isCoprimeTo(64) shouldBe true
  }

  test("34 calculate Euler's totient function phi[m]") {
    10.totient shouldBe 4
  }

  test("35 determine the prime factors of a given positive integer") {
    19.primeFactors shouldEqual List(19)
    315.primeFactors shouldEqual List(3, 3, 5, 7)
  }

  test("36 determine the prime factors of a given positive integer and their multiplicity") {
    315.primeFactorMultiplicity shouldEqual List((3, 2), (5, 1), (7, 1))
  }

  test("37 calculate Euler's totient function phi[m] improved") {
    10.totientImproved shouldBe 4
  }

  test("39 construct a list of all prime numbers in a given range") {
    listPrimesInRange(7 to 31) shouldEqual List(7, 11, 13, 17, 19, 23, 29, 31)
  }

  test("40 finding the two prime numbers that sum up to a given odd integer should throw an exception") {
    an[IllegalArgumentException] should be thrownBy 1.goldbach
    an[IllegalArgumentException] should be thrownBy 2.goldbach
    an[IllegalArgumentException] should be thrownBy 3.goldbach
  }

  test("40 find the two prime numbers that sum up to a given even integer") {
    4.goldbach shouldEqual(2, 2)
    28.goldbach shouldEqual(5, 23)
  }

  test("41 listing all even numbers and their Goldbach composition for an empty range should yield an empty result") {
    goldbachList(1 until 1) shouldBe empty
    goldbachList(1 to 3) shouldBe empty
  }

  test("41 list all even numbers and their Goldbach composition for a given range") {
    goldbachList(9 to 20) shouldEqual
      Map(10 ->(3, 7), 12 ->(5, 7), 14 ->(3, 11), 16 ->(3, 13), 18 ->(5, 13), 20 ->(3, 17))
  }

  test("42 listing all even numbers and their Goldbach composition (each part being bigger than a given minimum) for an empty range should yield an empty result") {
    goldbachListLimited(1 until 1, 0) shouldBe empty
    goldbachListLimited(1 to 3, 0) shouldBe empty
    goldbachListLimited(1 to 100, 100) shouldBe empty
  }

  test("42 list all even numbers and their Goldbach composition (each part being bigger than a given minimum) for a given range") {
    goldbachListLimited(1 to 2000, 50) shouldEqual
      Map(992 ->(73, 919), 1382 ->(61, 1321), 1856 ->(67, 1789), 1928 ->(61, 1867))
  }
}
