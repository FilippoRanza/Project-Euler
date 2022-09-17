package problem_16

import scala.math.BigInt

def problem_16(): BigInt =
  val p_1000 = pow_of_two(1000)
  sum_digits(p_1000, BigInt(0))

def sum_digits(n: BigInt, acc: BigInt): BigInt =
  if n == 0 then acc
  else sum_digits(n / 10, acc + (n % 10))

def pow_of_two(n: Int): BigInt = BigInt(1) << n
