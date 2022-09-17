package problem_20

import scala.math.BigInt

def problem_20(): BigInt =
  digit_sum(factorial(100, BigInt(1)), BigInt(0))

def digit_sum(n: BigInt, acc: BigInt): BigInt =
  if n == 0 then acc
  else digit_sum(n / 10, acc + (n % 10))

def factorial(n: Int, acc: BigInt): BigInt =
  if n == 1 then acc
  else factorial(n - 1, acc * n)
