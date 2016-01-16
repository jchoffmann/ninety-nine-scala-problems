import S99Logic._
import org.scalatest.FunSuite

class LogicSuite extends FunSuite {
  test("46 logical and") {
    assert(and(true, true) === true)
    assert(and(true, false) === false)
    assert(and(false, true) === false)
    assert(and(false, false) === false)
  }

  test("46 logical or") {
    assert(or(true, true) === true)
    assert(or(true, false) === true)
    assert(or(false, true) === true)
    assert(or(false, false) === false)
  }

  test("46 logical nand") {
    assert(nand(true, true) === false)
    assert(nand(true, false) === true)
    assert(nand(false, true) === true)
    assert(nand(false, false) === true)
  }

  test("46 logical nor") {
    assert(nor(true, true) === false)
    assert(nor(true, false) === false)
    assert(nor(false, true) === false)
    assert(nor(false, false) === true)
  }

  test("46 logical xor") {
    assert(xor(true, true) === false)
    assert(xor(true, false) === true)
    assert(xor(false, true) === true)
    assert(xor(false, false) === false)
  }

  test("46 logical impl") {
    assert(impl(true, true) === true)
    assert(impl(true, false) === false)
    assert(impl(false, true) === true)
    assert(impl(false, false) === true)
  }

  test("46 logical equ") {
    assert(equ(true, true) === true)
    assert(equ(true, false) === false)
    assert(equ(false, true) === false)
    assert(equ(false, false) === true)
  }

  test("46 truth table") {
    assert(table2((a, b) => and(a, or(a, b))) === Map((true, true) -> true, (true, false) -> true, (false, true) -> false, (false, false) -> false))
  }

  test("47 logical and via operator") {
    assert((true and true) === true)
    assert((true and false) === false)
    assert((false and true) === false)
    assert((false and false) === false)
  }

  test("47 logical or via operator") {
    assert((true or true) === true)
    assert((true or false) === true)
    assert((false or true) === true)
    assert((false or false) === false)
  }

  test("47 logical nand via operator") {
    assert((true nand true) === false)
    assert((true nand false) === true)
    assert((false nand true) === true)
    assert((false nand false) === true)
  }

  test("47 logical nor via operator") {
    assert((true nor true) === false)
    assert((true nor false) === false)
    assert((false nor true) === false)
    assert((false nor false) === true)
  }

  test("47 logical xor via operator") {
    assert((true xor true) === false)
    assert((true xor false) === true)
    assert((false xor true) === true)
    assert((false xor false) === false)
  }

  test("47 logical impl via operator") {
    assert((true impl true) === true)
    assert((true impl false) === false)
    assert((false impl true) === true)
    assert((false impl false) === true)
  }

  test("47 logical equ via operator") {
    assert((true equ true) === true)
    assert((true equ false) === false)
    assert((false equ true) === false)
    assert((false equ false) === true)
  }

  test("47 truth table via operator") {
    assert(table2((a, b) => a and (a or not(b))) === Map((true, true) -> true, (true, false) -> true, (false, true) -> false, (false, false) -> false))
  }

  test("49 gray code") {
    assert(gray(3) === List("000", "001", "011", "010", "110", "111", "101", "100"))
  }

  test("50 huffmann code") {
    assert(huffman(List(('a, 45), ('b, 13), ('c, 12), ('d, 16), ('e, 9), ('f, 5))) ===
      List(('a, "0"), ('b, "101"), ('c, "100"), ('d, "111"), ('e, "1101"), ('f, "1100")))
  }
}
