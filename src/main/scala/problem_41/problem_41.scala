package problem_41

import scala.math;

def problem_41(): Int =
  find_larger_pandigital_prime()

def find_larger_pandigital_prime(): Int =
  val start = Array(7, 6, 5, 4, 3, 2, 1)
  rec_find(start)

def rec_find(l: Array[Int]): Int =
  val n = join_number(l)
  if brute_force_prime(n) then n
  else
    make_permutations(l) match
      case Some(next) => rec_find(next)
      case None       => -1

def join_number(l: Array[Int]): Int =
  l.fold(0)((acc, curr) => (acc * 10) + curr)

def make_permutations(list: Array[Int]): Option[Array[Int]] =
  find_last_k(list.toList, None, 0)
    .map(k => (k, find_largest_l(list, k)))
    .map((k, l) => (k, swap(list, k, l)))
    .map(reverse_tail)

def find_last_k(list: List[Int], k: Option[Int], curr: Int): Option[Int] =
  list match
    case h1 :: h2 :: tail => find_last_k(h2 :: tail, next_opt(k, curr, h1, h2), curr + 1)
    case _                => k

def find_largest_l(list: Array[Int], k: Int): Int =
  rec_find_largest_l(list, k, k + 1, k + 1)

def rec_find_largest_l(list: Array[Int], k: Int, l: Int, curr: Int): Int =
  if l == list.length then curr
  else rec_find_largest_l(list, k, l + 1, get_next_l(list, k, l, curr))

def get_next_l(list: Array[Int], k: Int, l: Int, curr: Int) =
  if list(k) > list(l) then l
  else curr

def swap(list: Array[Int], a: Int, b: Int): Array[Int] =
  val tmp = list(a)
  list(a) = list(b)
  list(b) = tmp
  list

def reverse_tail(k: Int, list: Array[Int]): Array[Int] =
  rec_reverse_tail(k + 1, list.length - 1, list)

def rec_reverse_tail(a: Int, b: Int, list: Array[Int]): Array[Int] =
  if a >= b then list
  else rec_reverse_tail(a + 1, b - 1, swap(list, a, b))

def next_opt(curr: Option[Int], idx: Int, v1: Int, v2: Int): Option[Int] =
  if v1 > v2 then Some(idx)
  else curr

def brute_force_prime(n: Int): Boolean =
  if possible_prime(n) then search_prime(n)
  else false

def search_prime(n: Int): Boolean =
  val upper = math.ceil(math.sqrt(n)).toInt
  rec_search_prime(n, 3, upper)

def rec_search_prime(n: Int, curr: Int, upper: Int): Boolean =
  if curr == upper then true
  else if n % curr == 0 then false
  else rec_search_prime(n, curr + 2, upper)

def possible_prime(n: Int): Boolean =
  n % 6 match
    case 1 | 5 => true
    case _     => false
