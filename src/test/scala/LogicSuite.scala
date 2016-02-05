import S99Logic.{not => s99not, _}
import org.scalatest.{FunSuite, Matchers}

class LogicSuite extends FunSuite with Matchers {
  test("46 logical and") {
    and(true, true) shouldBe true
    and(true, false) shouldBe false
    and(false, true) shouldBe false
    and(false, false) shouldBe false
  }

  test("46 logical or") {
    or(true, true) shouldBe true
    or(true, false) shouldBe true
    or(false, true) shouldBe true
    or(false, false) shouldBe false
  }

  test("46 logical nand") {
    nand(true, true) shouldBe false
    nand(true, false) shouldBe true
    nand(false, true) shouldBe true
    nand(false, false) shouldBe true
  }

  test("46 logical nor") {
    nor(true, true) shouldBe false
    nor(true, false) shouldBe false
    nor(false, true) shouldBe false
    nor(false, false) shouldBe true
  }

  test("46 logical xor") {
    xor(true, true) shouldBe false
    xor(true, false) shouldBe true
    xor(false, true) shouldBe true
    xor(false, false) shouldBe false
  }

  test("46 logical impl") {
    impl(true, true) shouldBe true
    impl(true, false) shouldBe false
    impl(false, true) shouldBe true
    impl(false, false) shouldBe true
  }

  test("46 logical equ") {
    equ(true, true) shouldBe true
    equ(true, false) shouldBe false
    equ(false, true) shouldBe false
    equ(false, false) shouldBe true
  }

  test("46 truth table") {
    table2((a, b) => and(a, or(a, b))) should contain theSameElementsAs
      Map((true, true) -> true, (true, false) -> true, (false, true) -> false, (false, false) -> false)
  }

  test("47 logical and via operator") {
    (true and true) shouldBe true
    (true and false) shouldBe false
    (false and true) shouldBe false
    (false and false) shouldBe false
  }

  test("47 logical or via operator") {
    (true or true) shouldBe true
    (true or false) shouldBe true
    (false or true) shouldBe true
    (false or false) shouldBe false
  }

  test("47 logical nand via operator") {
    (true nand true) shouldBe false
    (true nand false) shouldBe true
    (false nand true) shouldBe true
    (false nand false) shouldBe true
  }

  test("47 logical nor via operator") {
    (true nor true) shouldBe false
    (true nor false) shouldBe false
    (false nor true) shouldBe false
    (false nor false) shouldBe true
  }

  test("47 logical xor via operator") {
    (true xor true) shouldBe false
    (true xor false) shouldBe true
    (false xor true) shouldBe true
    (false xor false) shouldBe false
  }

  test("47 logical impl via operator") {
    (true impl true) shouldBe true
    (true impl false) shouldBe false
    (false impl true) shouldBe true
    (false impl false) shouldBe true
  }

  test("47 logical equ via operator") {
    (true equ true) shouldBe true
    (true equ false) shouldBe false
    (false equ true) shouldBe false
    (false equ false) shouldBe true
  }

  test("47 truth table via operator") {
    table2((a, b) => a and (a or s99not(b))) should contain theSameElementsAs
      Map((true, true) -> true, (true, false) -> true, (false, true) -> false, (false, false) -> false)
  }

  test("49 n-bit gray code should throw an exception if n < 1") {
    an[IllegalArgumentException] should be thrownBy gray(-1)
    an[IllegalArgumentException] should be thrownBy gray(0)
  }

  test("49 n-bit gray code") {
    gray(1) should contain theSameElementsAs List("0", "1")
    gray(2) should contain theSameElementsAs List("00", "01", "11", "10")
    gray(3) should contain theSameElementsAs List("000", "001", "011", "010", "110", "111", "101", "100")
  }

  test("50 constructing the huffman code for a too small list of symbol frequencies should throw an exception") {
    an[IllegalArgumentException] should be thrownBy huffman(List.empty)
    an[IllegalArgumentException] should be thrownBy huffman(List(('a, 100)))
  }

  test("50 construct the huffman code for a list of symbol frequencies") {
    huffman(List(('a, 45), ('b, 13), ('c, 12), ('d, 16), ('e, 9), ('f, 5))) should contain theSameElementsAs
      List(('a, "0"), ('b, "101"), ('c, "100"), ('d, "111"), ('e, "1101"), ('f, "1100"))
  }
}
