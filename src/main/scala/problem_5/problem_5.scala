package problem_5

import scala.math

def problem_5(): Long =
  smallest_multiple(20)

def smallest_multiple(upper: Int): Long = combinate_lcm(long_list(2, upper))

def combinate_lcm(list: List[Long]): Long =
  list.iterator.foldLeft(None)(compute_lcm) getOrElse { 0 }

def compute_lcm(a: Option[Long], b: Long): Option[Long] =
  a match
    case Some(x) => Some(lcm(x, b))
    case None    => Some(b)

def lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

def gcd(a: Long, b: Long): Long = rgcd(math.max(a, b), math.min(a, b))

def rgcd(a: Long, b: Long): Long =
  if b == 0 then a
  else rgcd(b, a % b)

def long_list(a: Long, b: Long): List[Long] =
  if a > b then Nil
  else a :: long_list(a + 1, b)
