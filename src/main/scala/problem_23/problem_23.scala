package problem_23

import scala.collection.mutable.HashSet

def problem_23(): Int = non_abundant_sum(28123)

def non_abundant_sum(upper: Int): Int =
  val sum_set = abundant_sums(upper)
  (1 to upper).filter(n => !sum_set.contains(n)).sum

def abundant_sums(upper: Int): HashSet[Int] =
  val abundant = abundant_below(upper)
  var output   = HashSet[Int]()
  for a <- abundant do for b <- abundant do output.add(a + b)

  output

def abundant_below(n: Int): List[Int] = rec_abundant_below(12, n)

def rec_abundant_below(curr: Int, upper: Int): List[Int] =
  if curr == upper then Nil
  else if is_abundant(curr) then curr :: rec_abundant_below(curr + 1, upper)
  else rec_abundant_below(curr + 1, upper)

def is_abundant(n: Int): Boolean = divisors.div_sum(n) > n
