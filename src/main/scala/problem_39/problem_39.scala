package problem_39

import scala.collection.immutable.HashMap

type Triple = List[Int]

def problem_39(): Int =
  val mn_list = find_suitable_triples(250, 250, 1000).map(perimeter)
  val map     = count_in_list(mn_list, HashMap())
  find_max_by_index(map)

def find_max_by_index(map: HashMap[Int, Int]): Int =
  map.maxBy(_._2)._1

def count_in_list(ls: LazyList[Int], map: HashMap[Int, Int]): HashMap[Int, Int] =
  ls match
    case c #:: rest => count_in_list(rest, map.updatedWith(c)(count_next))
    case _          => map

def count_next(v: Option[Int]): Option[Int] =
  v match
    case Some(v) => Some(v + 1)
    case None    => Some(1)

def find_suitable_triples(upper_m: Int, upper_n: Int, upper: Int): LazyList[Triple] =
  find_suitable_mn(upper_m, upper_n).flatMap((m, n) => make_triples(m, n, 1, upper))

def make_triples(m: Int, n: Int, k: Int, upper: Int): LazyList[Triple] =
  val curr = pythagorean_triple(m, n, k)
  if perimeter(curr) <= upper then curr #:: make_triples(m, n, k + 1, upper)
  else LazyList.empty

def perimeter(t: Triple): Int = t.sum

def pythagorean_triple(m: Int, n: Int, k: Int): List[Int] =
  primitive_pythagorean_triple(m, n).map(_ * k)

def primitive_pythagorean_triple(m: Int, n: Int): List[Int] =
  val m2 = m * m
  val n2 = n * n
  val a  = m2 - n2
  val b  = 2 * m * n
  val c  = m2 + n2
  List(a, b, c)

def find_suitable_mn(upper_m: Int, upper_n: Int): LazyList[(Int, Int)] =
  double_range(1, upper_m, 1, 1, upper_n)
    .filter(one_odd)
    .filter((m, n) => m > n)
    .filter(coprimes)

def perimeter(m: Int, n: Int): Int = 2 * ((m * m) + (m * n))

def double_range(ba: Int, ea: Int, bb: Int, cb: Int, eb: Int): LazyList[(Int, Int)] =
  if ba == ea then LazyList.empty
  else if cb == eb then double_range(ba + 1, ea, bb, bb, eb)
  else (ba, cb) #:: double_range(ba, ea, bb, cb + 1, eb)

def one_odd(a: Int, b: Int): Boolean =
  (even(a) && !even(b)) || (even(b) && !even(a))

def even(a: Int): Boolean = a % 2 == 0

def coprimes(a: Int, b: Int): Boolean = gcd(a, b) == 1

def gcd(a: Int, b: Int): Int =
  if a > b then rgcd(a, b)
  else rgcd(b, a)

def rgcd(a: Int, b: Int): Int =
  if b == 0 then a
  else rgcd(b, a % b)
