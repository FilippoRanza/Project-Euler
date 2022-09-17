package problem_21

import scala.collection.mutable.HashMap

def problem_21(): Int = amicable_upto(1, 10000, HashMap()).sum

def amicable_upto(curr: Int, upper: Int, map: HashMap[Int, Int]): List[Int] =
  if curr == upper then Nil
  else
    check_amicable(curr, map) match
      case Some((a, b)) => a :: b :: amicable_upto(curr + 1, upper, map)
      case None         => amicable_upto(curr + 1, upper, map)

def check_amicable(curr: Int, map: HashMap[Int, Int]): Option[(Int, Int)] =
  val ds = div_sum(curr)
  val output = map.get(ds) match
    case Some(other) =>
      if other == curr then Some((curr, ds))
      else None
    case None => None

  map.put(curr, ds)
  output

def div_sum(n: Int): Int =
  divisors.fast_divisors(n).sum - n
