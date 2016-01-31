import S99List.{length => s99length, _}
import org.scalatest.{FunSuite, Inspectors, Matchers}

class ListSuite extends FunSuite with Matchers with Inspectors {
  test("01 find the last element of a list") {
    last(List(1, 1, 2, 3, 5, 8)) shouldBe 8
  }

  test("01 finding the last element of an empty list should throw an exception") {
    a[NoSuchElementException] should be thrownBy last(List.empty)
  }

  test("02 find the penultimate element of a list") {
    penultimate(List(1, 1, 2, 3, 5, 8)) shouldBe 5
  }

  test("02 finding the penultimate element of a list should throw an exception if the list is too small") {
    a[NoSuchElementException] should be thrownBy penultimate(List.empty)
    a[NoSuchElementException] should be thrownBy penultimate(List(1))
  }

  test("03 finding the Kth element of a list should throw an exception when K is negative") {
    an[IllegalArgumentException] should be thrownBy nth(-1, List(1, 1, 2, 3, 5, 8))
  }

  test("03 find the Kth element of a list") {
    nth(2, List(1, 1, 2, 3, 5, 8)) shouldBe 2
  }

  test("03 find the Kth element of a list should throw an exception if the list is too small") {
    a[NoSuchElementException] should be thrownBy nth(2, List.empty)
    a[NoSuchElementException] should be thrownBy nth(2, List(1, 1))
  }

  test("04 find the number of elements of a list") {
    s99length(List(1, 1, 2, 3, 5, 8)) shouldBe 6
  }

  test("04 find the number of elements of an empty list") {
    s99length(List.empty) shouldBe 0
  }

  test("05 reverse a list") {
    reverse(List(1, 1, 2, 3, 5, 8)) shouldEqual List(8, 5, 3, 2, 1, 1)
  }

  test("05 reverse an empty list") {
    reverse(List.empty) shouldBe empty
  }

  test("06 find out whether a list is a palindrome") {
    isPalindrome(List(1, 2, 3, 2, 1)) shouldBe true
    isPalindrome(List(1, 2, 2, 1)) shouldBe true
  }

  test("06 find out whether an empty list is a palindrome") {
    isPalindrome(List.empty) shouldBe true
  }

  test("07 flatten a nested list structure") {
    flatten(List(List(1, 1), 2, List(3, List(5, 8)))) shouldEqual List(1, 1, 2, 3, 5, 8)
  }

  test("07 flatten an empty list") {
    flatten(List.empty) shouldBe empty
  }

  test("08 eliminate consecutive duplicates of list elements") {
    compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) shouldEqual List('a, 'b, 'c, 'a, 'd, 'e)
  }

  test("08 eliminate consecutive duplicates of an empty list") {
    compress(List.empty) shouldBe empty
  }

  test("09 pack consecutive duplicates of list elements into sub-lists") {
    pack(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) shouldEqual
      List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e))
  }

  test("09 pack consecutive duplicates of an empty list") {
    pack(List.empty) shouldBe empty
  }

  test("10 run-length encoding of a list") {
    encode(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) shouldEqual
      List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))
  }

  test("10 run-length encoding of an empty list") {
    encode(List.empty) shouldBe empty
  }

  test("11 modified run-length encoding of a list") {
    encodeModified(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) shouldEqual
      List((4, 'a), 'b, (2, 'c), (2, 'a), 'd, (4, 'e))
  }

  test("11 modified run-length encoding of an empty list") {
    encodeModified(List.empty) shouldBe empty
  }

  test("12 decode a run-length encoded list") {
    decode(List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))) shouldEqual
      List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)
  }

  test("12 decode an empty list") {
    decode(List.empty) shouldBe empty
  }

  test("13 run-length encoding of a list (direct solution)") {
    encodeDirect(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) shouldEqual
      List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e))
  }

  test("13 run-length encoding of an empty list (direct solution)") {
    encodeDirect(List.empty) shouldBe empty
  }

  test("14 duplicate the elements of a list") {
    duplicate(List('a, 'b, 'c, 'c, 'd)) shouldEqual List('a, 'a, 'b, 'b, 'c, 'c, 'c, 'c, 'd, 'd)
  }

  test("14 duplicate the elements of an empty list") {
    duplicate(List.empty) shouldBe empty
  }

  test("15 duplicate the elements of a list a given number of times") {
    duplicateN(3, List('a, 'b, 'c, 'c, 'd)) shouldEqual List('a, 'a, 'a, 'b, 'b, 'b, 'c, 'c, 'c, 'c, 'c, 'c, 'd, 'd, 'd)
  }

  test("15 duplicate the elements of an empty list a given number of times") {
    duplicateN(3, List.empty) shouldBe empty
  }

  test("16 drop every Nth element from a list") {
    drop(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual List('a, 'b, 'd, 'e, 'g, 'h, 'j, 'k)
  }

  test("16 drop every Nth element from an empty list") {
    drop(3, List.empty) shouldBe empty
  }

  test("17 split a list into two parts") {
    split(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual
      (List('a, 'b, 'c), List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
  }

  test("17 split a list into two parts with index too low") {
    split(0, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual
      (List.empty, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
  }

  test("17 split a list into two parts with index too high") {
    split(12, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual
      (List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k), List.empty)
  }

  test("17 split an empty list into two parts") {
    split(3, List.empty) shouldBe(List.empty, List.empty)
  }

  test("18 extract a slice from a list") {
    slice(3, 7, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual List('d, 'e, 'f, 'g)
  }

  test("18 extract a slice from a list exceeding the end") {
    slice(3, 17, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k)
  }

  test("18 extract a slice from an empty list") {
    slice(3, 7, List.empty) shouldBe empty
  }

  test("19 rotate a list n places to the left") {
    rotate(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual
      List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k, 'a, 'b, 'c)
  }

  test("19 rotate a list n places to the right") {
    rotate(-2, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual
      List('j, 'k, 'a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i)
  }

  test("19 rotating a list 0 places should leave the list unchanged") {
    rotate(0, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)) shouldEqual
      List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k)
  }

  test("19 rotating an empty list should result in an empty list") {
    rotate(3, List.empty) shouldBe empty
    rotate(-2, List.empty) shouldBe empty
    rotate(0, List.empty) shouldBe empty
  }

  test("20 remove the Kth element from a list") {
    removeAt(0, List('a, 'b, 'c, 'd)) shouldEqual(List('b, 'c, 'd), 'a)
    removeAt(1, List('a, 'b, 'c, 'd)) shouldEqual(List('a, 'c, 'd), 'b)
  }

  test("20 removing the Kth element from a list should throw an Exception if K is negative or too big") {
    a[IllegalArgumentException] should be thrownBy removeAt(-1, List('a, 'b, 'c, 'd))
    a[IllegalArgumentException] should be thrownBy removeAt(4, List('a, 'b, 'c, 'd))
  }

  test("21 insert an element at a given position into a list") {
    insertAt('new, 0, List('a, 'b, 'c, 'd)) shouldEqual List('new, 'a, 'b, 'c, 'd)
    insertAt('new, 1, List('a, 'b, 'c, 'd)) shouldEqual List('a, 'new, 'b, 'c, 'd)
    insertAt('new, 4, List('a, 'b, 'c, 'd)) shouldEqual List('a, 'b, 'c, 'd, 'new)
  }

  test("21 inserting an element at a position too small or too big should still insert at the beginning or end") {
    insertAt('new, -1, List('a, 'b, 'c, 'd)) shouldEqual List('new, 'a, 'b, 'c, 'd)
    insertAt('new, 5, List('a, 'b, 'c, 'd)) shouldEqual List('a, 'b, 'c, 'd, 'new)
  }

  test("22 create a list containing all integers within a given range") {
    range(-1, 9) shouldEqual List(-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    range(1, 1) shouldEqual List(1)
  }

  test("22 create a list containing all integers using an empty range") {
    range(5, 4) shouldBe empty
  }

  test("23 extract a given number of randomly selected elements from a list") {
    val input = List('a, 'b, 'c, 'd, 'f, 'g, 'h)
    val result = randomSelect(3, input)
    result.toSet should have size 3
    forAll(result) { r => input should contain(r) }
  }

  test("23 extracting a number of randomly selected elements from a list should throw an exception if the number is negative") {
    an[IllegalArgumentException] should be thrownBy randomSelect(-1, List('a, 'b, 'c, 'd, 'f, 'g, 'h))
  }

  test("23 extracting zero randomly selected elements from a list should return the empty list") {
    randomSelect(0, List('a, 'b, 'c, 'd, 'f, 'g, 'h)) shouldEqual List.empty
  }

  test("23 extract a given number of randomly selected elements from an empty list should result in an empty list") {
    randomSelect(3, List.empty) shouldEqual List.empty
  }

  test("23 extract a too large number of randomly selected elements from a list") {
    val input = List('a, 'b, 'c, 'd, 'f, 'g, 'h)
    val result = randomSelect(9, input)
    result.toSet should have size 7
    forAll(result) { r => input should contain(r) }
  }

  test("24 draw N different random numbers from the set 1..M") {
    val result = lotto(6, 49)
    result.toSet should have size 6
    forAll(result) { r => r should (be > 0 and be <= 49) }
  }

  test("24 drawing N different random numbers from the set 1..M should throw an exception if N or M <= 0") {
    an[IllegalArgumentException] should be thrownBy lotto(0, 49)
    an[IllegalArgumentException] should be thrownBy lotto(6, 0)
  }

  test("24 draw N different random numbers from the set 1..M should return a permutation if N >= M") {
    lotto(6, 6) should contain theSameElementsAs range(1, 6)
    lotto(7, 6) should contain theSameElementsAs range(1, 6)
  }

  test("25 generate a random permutation of the elements of a list") {
    val input = List('a, 'b, 'c, 'd, 'e, 'f)
    val result = randomPermute(input)
    result should contain theSameElementsAs input
  }

  test("25 generate a random permutation of the empty list should yield the empty list") {
    randomPermute(List.empty) shouldEqual List.empty
  }

  test("26 generate the combinations of K distinct objects chosen from the N elements of a list") {
    val input = List('a, 'b, 'c, 'd, 'e, 'f)
    val result = combinations(3, input)
    result should have size 20
    forAll(result) { comb => comb.toSet should have size 3 }
    forAll(result) { comb => forAll(comb) { elem => input should contain(elem) } }
  }

  test("26 generating the combinations of K distinct objects chosen from the N elements of a list should throw an exception if K <= 0") {
    an[IllegalArgumentException] should be thrownBy combinations(-1, List('a, 'b, 'c, 'd, 'e, 'f))
    an[IllegalArgumentException] should be thrownBy combinations(0, List('a, 'b, 'c, 'd, 'e, 'f))
  }

  test("26 generating the combinations of K distinct objects chosen from an empty list should yield an empty list") {
    combinations(3, List.empty) shouldEqual List.empty
  }

  test("26 generating the combinations of K distinct objects chosen from N elements should yield an empty list if K > N") {
    combinations(7, List('a, 'b, 'c, 'd, 'e, 'f)) shouldEqual List.empty
  }

  test("26 generate the combinations of K distinct objects chosen from the N elements of a list should contain the original for K = N") {
    combinations(6, List('a, 'b, 'c, 'd, 'e, 'f)).head should contain theSameElementsAs List('a, 'b, 'c, 'd, 'e, 'f)
  }

  test("27 group the elements of a set into disjoint subsets") {
    val input = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
    val result = group3(input)
    result should have size 1260
    forAll(result) { r => r map (_.size) shouldEqual List(2, 3, 4) }
    forAll(result) { r => r.flatten should contain theSameElementsAs input }
  }

  test("27 grouping the elements of a an empty set into disjoint subsets should yield an empty set") {
    group3(List.empty) shouldEqual List.empty
  }

  test("27 grouping the elements of a set into disjoint subsets of configurable size should throw an exception if the subset configuration is empty") {
    val input = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
    an[IllegalArgumentException] should be thrownBy group(List.empty, input)
  }

  test("27 grouping the elements of a set into disjoint subsets of configurable size should throw an exception if the subset configuration does not match the list size") {
    val input = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
    an[IllegalArgumentException] should be thrownBy group(List(2, 3, 3), input)
    an[IllegalArgumentException] should be thrownBy group(List(2, 3, 5), input)
    an[IllegalArgumentException] should be thrownBy group(List(2, 3, 4), List.empty)
  }

  test("27 grouping the elements of a set into disjoint subsets of configurable size should throw an exception if the subset configuration contains sizes <= 0") {
    val input = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
    an[IllegalArgumentException] should be thrownBy group(List(0, 3, 6), input)
    an[IllegalArgumentException] should be thrownBy group(List(-1, 3, 7), input)
  }

  test("27 group the elements of a set into disjoint subsets of configurable size") {
    val input = List("Aldo", "Beat", "Carla", "David", "Evi", "Flip", "Gary", "Hugo", "Ida")
    group(List(2, 3, 4), input) shouldEqual group3(input)
    val result = group(List(2, 2, 5), input)
    result should have size 756
    forAll(result) { r => r map (_.size) shouldEqual List(2, 2, 5) }
    forAll(result) { r => r.flatten should contain theSameElementsAs input }
  }

  test("28 sorting a list of lists according to length of sub-lists") {
    lsort(List(List('a, 'b, 'c), List('d, 'e), List('f, 'g, 'h), List('d, 'e), List('i, 'j, 'k, 'l), List('m, 'n), List('o))) shouldEqual
      List(List('o), List('d, 'e), List('d, 'e), List('m, 'n), List('a, 'b, 'c), List('f, 'g, 'h), List('i, 'j, 'k, 'l))
  }

  test("28 sorting an empty list of lists according to length of sub-lists should yield an empty list") {
    lsort(List.empty) shouldEqual List.empty
  }

  test("28 sorting a list of lists according to length frequency of sub-lists") {
    lsortFreq(List(List('a, 'b, 'c), List('d, 'e), List('f, 'g, 'h), List('d, 'e), List('i, 'j, 'k, 'l), List('m, 'n), List('o))) shouldEqual
      List(List('i, 'j, 'k, 'l), List('o), List('a, 'b, 'c), List('f, 'g, 'h), List('d, 'e), List('d, 'e), List('m, 'n))
  }

  test("28 sorting an empty list of lists according to length frequency of sub-lists should yield an empty list") {
    lsortFreq(List.empty) shouldEqual List.empty
  }
}
