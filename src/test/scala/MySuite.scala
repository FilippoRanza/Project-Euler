// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("example test that succeeds") {
    val obtained = 42
    val expected = 42
    assertEquals(obtained, expected)
  }
}

class TestProblem24 extends munit.FunSuite {
  test("Test reverse from index") {
    val array    = Array(1, 2, 3, 4, 5)
    val expected = List(1, 2, 5, 4, 3)
    val actual   = problem_24.reverse_from(array, 2).toList
    assertEquals(actual, expected)
  }

  test("Test reverse on smaller list") {
    var array    = Array(0, 1, 2)
    val expected = List(0, 2, 1)
    val actual   = problem_24.reverse_from(array, 1).toList
    assertEquals(actual, expected)
  }

  test("Test find largest K") {
    var array    = Array(0, 2, 1)
    var expected = Some(0)
    var actual   = problem_24.largest_k_index(None, array)
    assertEquals(actual, expected)
  }

  test("Test find index I") {
    var array    = Array(1, 2, 0)
    var expected = 1
    var k        = problem_24.largest_k_index(None, array).get
    var actual   = problem_24.largest_i_index(k, array)
    assertEquals(actual, expected)

  }

  test("Test simple permutations") {
    assertEquals(problem_24.next_permutation(Array(0, 1, 2)).get.toList, List(0, 2, 1))
    assertEquals(problem_24.next_permutation(Array(0, 2, 1)).get.toList, List(1, 0, 2))
    assertEquals(problem_24.next_permutation(Array(1, 0, 2)).get.toList, List(1, 2, 0))
    assertEquals(problem_24.next_permutation(Array(1, 2, 0)).get.toList, List(2, 0, 1))
    assertEquals(problem_24.next_permutation(Array(2, 0, 1)).get.toList, List(2, 1, 0))
    assertEquals(problem_24.next_permutation(Array(2, 1, 0)), None)
  }

}
