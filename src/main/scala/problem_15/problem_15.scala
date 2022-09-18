package problem_15

import scala.math.BigInt

def problem_15(): BigInt = binomial(40, 20)

def binomial(n: Int, k: Int): BigInt =
  val a = factorial.factorial(n)
  val b = factorial.factorial(k)
  val c = factorial.factorial(n - k)
  a / (b * c)
