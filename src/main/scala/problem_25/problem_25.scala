package problem_25

import scala.math.BigInt

def problem_25(): Int = add_series(BigInt(0), BigInt(1)).zipWithIndex
  .find((n, i) => int_log10(n, 0) >= 1000)
  .map((n, i) => i)
  .get

def add_series(a: BigInt, b: BigInt): LazyList[BigInt] =
  a #:: add_series(b, a + b)

def int_log10(n: BigInt, acc: Int): Int =
  if n == 0 then acc
  else int_log10(n / 10, acc + 1)
