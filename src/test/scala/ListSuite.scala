import S99List._
import org.scalatest.FunSuite

class ListSuite extends FunSuite {
  test("01 find the last element of a list") {
    assert(last(List(1, 1, 2, 3, 5, 8)) === 8)
  }

  test("01 find the last element of an empty list") {
    intercept[NoSuchElementException] {
      last(List.empty)
    }
  }

  test("02 find the last but one element of a list") {
    assert(penultimate(List(1, 1, 2, 3, 5, 8)) === 5)
  }

  test("02 find the last but one element of an empty list") {
    intercept[NoSuchElementException] {
      penultimate(List.empty)
    }
  }

  test("02 find the last but one element of a singleton list") {
    intercept[NoSuchElementException] {
      penultimate(List(1))
    }
  }

  test("03 find the Kth element of a list") {
    assert(nth(2, List(1, 1, 2, 3, 5, 8)) === 2)
  }

  test("03 find the Kth element of an empty list") {
    intercept[NoSuchElementException] {
      nth(2, List.empty)
    }
  }

  test("03 find the Kth element of a list but K too big") {
    intercept[NoSuchElementException] {
      nth(2, List(1, 1))
    }
  }

  test("04 find the number of elements of a list") {
    assert(length(List(1, 1, 2, 3, 5, 8)) === 6)
  }

  test("04 find the number of elements of an empty list") {
    assert(length(List.empty) === 0)
  }

  test("05 reverse a list") {
    assert(reverse(List(1, 1, 2, 3, 5, 8)) === List(8, 5, 3, 2, 1, 1))
  }

  test("05 reverse an empty list") {
    assert(reverse(List.empty) === List.empty)
  }

  test("06 find out whether a list is a palindrome") {
    assert(isPalindrome(List(1, 2, 3, 2, 1)))
  }

  test("06 find out whether an even list is a palindrome") {
    assert(isPalindrome(List(1, 2, 2, 1)))
  }

  test("06 find out whether an empty list is a palindrome") {
    assert(isPalindrome(List.empty))
  }

  test("07 flatten a nested list structure") {
    assert(flatten(List(List(1, 1), 2, List(3, List(5, 8)))) === List(1, 1, 2, 3, 5, 8))
  }

  test("07 flatten an empty list") {
    assert(flatten(List.empty) === List.empty)
  }

  test("08 eliminate consecutive duplicates of list elements") {
    assert(compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) === List('a, 'b, 'c, 'a, 'd, 'e))
  }

  test("08 eliminate consecutive duplicates of an empty list") {
    assert(compress(List.empty) === List.empty)
  }

  test("09 pack consecutive duplicates of list elements into sublists") {
    assert(pack(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) ===
      List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e)))
  }

  test("09 pack consecutive duplicates of an empty list") {
    assert(pack(List.empty) === List.empty)
  }

  test("10 run-length encoding of a list") {
    assert(encode(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) ===
      List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e)))
  }

  test("10 run-length encoding of an empty list") {
    assert(encode(List.empty) === List.empty)
  }

  test("11 modified run-length encoding of a list") {
    assert(encodeModified(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) ===
      List((4, 'a), 'b, (2, 'c), (2, 'a), 'd, (4, 'e)))
  }

  test("11 modified run-length encoding of an empty list") {
    assert(encodeModified(List.empty) === List.empty)
  }

  test("12 decode a run-length encoded list") {
    assert(decode(List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))) ===
      List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))
  }

  test("12 decode an empty list") {
    assert(decode(List.empty) === List.empty)
  }

  test("13 run-length encoding of a list (direct solution)") {
    assert(encodeDirect(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) ===
      List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e)))
  }

  test("13 run-length encoding of an empty list (direct solution)") {
    assert(encodeDirect(List.empty) === List.empty)
  }

  test("14 duplicate the elements of a list") {
    assert(duplicate(List('a, 'b, 'c, 'c, 'd)) === List('a, 'a, 'b, 'b, 'c, 'c, 'c, 'c, 'd, 'd))
  }

  test("14 duplicate the elements of an empty list") {
    assert(duplicate(List.empty) === List.empty)
  }

  test("15 duplicate the elements of a list a given number of times") {
    assert(duplicateN(3, List('a, 'b, 'c, 'c, 'd)) === List('a, 'a, 'a, 'b, 'b, 'b, 'c, 'c, 'c, 'c, 'c, 'c, 'd, 'd, 'd))
  }

  test("15 duplicate the elements of an empty list a given number of times") {
    assert(duplicateN(3, List.empty) === List.empty)
  }

  test("16 drop every Nth element from a list") {
    assert(drop(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) === List('a, 'b, 'd, 'e, 'g, 'h, 'j, 'k))
  }

  test("16 drop every Nth element from an empty list") {
    assert(drop(3, List.empty) === List.empty)
  }

  test("17 split a list into two parts") {
    assert(split(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) ===
      (List('a, 'b, 'c), List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k)))
  }

  test("17 split a list into two parts with index too low") {
    assert(split(0, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) ===
      (List.empty, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)))
  }

  test("17 split a list into two parts with index too high") {
    assert(split(12, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) ===
      (List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k), List.empty))
  }

  test("17 split an empty list into two parts") {
    assert(split(3, List.empty) ===(List.empty, List.empty))
  }

  test("18 extract a slice from a list") {
    assert(slice(3, 7, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) === List('d, 'e, 'f, 'g))
  }

  test("18 extract a slice from a list exceeding the end") {
    assert(slice(3, 17, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) === List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
  }

  test("18 extract a slice from an empty list") {
    assert(slice(3, 7, List.empty) === List.empty)
  }

  test("19 rotate a list N places to the left") {
    assert(rotate(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) ===
      List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k, 'a, 'b, 'c))
  }

  test("19 rotate a list N places to the right") {
    assert(rotate(-2, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) ===
      List('j, 'k, 'a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i))
  }

  test("19 rotate an empty list N places to the right") {
    assert(rotate(-2, List.empty) === List.empty)
  }

  test("20 remove the Kth element from a list") {
    assert(removeAt(1, List('a, 'b, 'c, 'd)) ===(List('a, 'c, 'd), 'b))
  }

  test("21 insert an element at a given position into a list") {
    assert(insertAt('new, 1, List('a, 'b, 'c, 'd)) === List('a, 'new, 'b, 'c, 'd))
  }

  test("21 insert an element at a given position into an empty list") {
    assert(insertAt('new, 1, List.empty) === List('new))
  }

  test("21 insert an element at first position into a list") {
    assert(insertAt('new, 0, List('a, 'b, 'c, 'd)) === List('new, 'a, 'b, 'c, 'd))
  }

  test("21 insert an element at last position into a list") {
    assert(insertAt('new, 21, List('a, 'b, 'c, 'd)) === List('a, 'b, 'c, 'd, 'new))
  }

  test("22 create a list containing all integers within a given range") {
    assert(range(4, 9) === List(4, 5, 6, 7, 8, 9))
  }

  test("22 create a list containing all integers using an empty range") {
    assert(range(5, 4) === List.empty)
  }

  test("23 extract a given number of randomly selected elements from a list") {
    val input = List('a, 'b, 'c, 'd, 'f, 'g, 'h)
    val result = randomSelect(3, input)
    assert(result.size === 3)
    assert(result forall (input contains _))
  }

  test("23 extract a too large number of randomly selected elements from a list") {
    val input = List('a, 'b, 'c, 'd, 'f, 'g, 'h)
    val result = randomSelect(9, input)
    assert(result.size === 7)
    assert(result forall (input contains _))
  }

  test("24 draw N different random numbers from the set 1..M") {
    assert(lotto(6, 49).size === 6)
    assert(lotto(6, 49) forall (_ <= 49))
  }

  test("25 generate a random permutation of the elements of a list") {
    val input = List('a, 'b, 'c, 'd, 'e, 'f)
    val result = randomPermute(input)
    assert(result.size === 6)
    assert(result forall (input contains _))
  }

  test("26 generate the combinations of K distinct objects chosen from the N elements of a list") {
    val input = List('a, 'b, 'c, 'd, 'e, 'f)
    val result = combinations(3, input)
    assert(result.size === 20)
    assert(result forall (_.size == 3))
    assert(result forall (_.toSet subsetOf input.toSet))
  }

  test("27 group the elements of a set into disjoint subsets") {
    val input = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
    val result = group3(input)
    assert(result.size === 1260)
    assert((result map (_.map(_.size))).forall(_ == List(2, 3, 4)))
    assert((result map (_.flatten.toSet)).forall(_ == input.toSet))
  }

  test("27 group the elements of a set into disjoint subsets of configurable size") {
    val input = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
    assert(group(List(2, 3, 4), input) === group3(input))
    val result = group(List(2, 2, 5), input)
    assert(result.size === 756)
    assert(result.map(_.map(_.size)).forall(_ == List(2, 2, 5)))
  }

  test("28 sorting a list of lists according to length of sublists") {
    assert(lsort(List(List('a, 'b, 'c), List('d, 'e), List('f, 'g, 'h), List('d, 'e), List('i, 'j, 'k, 'l), List('m, 'n), List('o))) ===
      List(List('o), List('d, 'e), List('d, 'e), List('m, 'n), List('a, 'b, 'c), List('f, 'g, 'h), List('i, 'j, 'k, 'l)))
  }

  test("28 sorting a list of lists according to length frequency of sublists") {
    assert(lsortFreq(List(List('a, 'b, 'c), List('d, 'e), List('f, 'g, 'h), List('d, 'e), List('i, 'j, 'k, 'l), List('m, 'n), List('o))) ===
      List(List('i, 'j, 'k, 'l), List('o), List('a, 'b, 'c), List('f, 'g, 'h), List('d, 'e), List('d, 'e), List('m, 'n)))
  }
}
