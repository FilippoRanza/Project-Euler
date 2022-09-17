package divisors

import scala.math.BigInt

def fast_divisors(n: Int): List[Int] = rec_fast_divisors(n, n, 1)

def rec_fast_divisors(n: Int, stop: Int, curr: Int): List[Int] =
  if curr < stop then
    if n % curr == 0 then
      val div = n / curr
      if div == curr then curr :: rec_fast_divisors(n, div, curr + 1)
      else curr :: div :: rec_fast_divisors(n, div, curr + 1)
    else rec_fast_divisors(n, stop, curr + 1)
  else Nil

def divisors(n: Int, curr: Int): List[Int] =
  if curr <= n then
    if n % curr == 0 then curr :: divisors(n, curr + 1)
    else divisors(n, curr + 1)
  else Nil

def big_int_divisors(n: BigInt, curr: BigInt): List[BigInt] =
  if curr < n then
    if n % curr == 0 then curr :: big_int_divisors(n, curr + BigInt(1))
    else big_int_divisors(n, curr + BigInt(1))
  else Nil
