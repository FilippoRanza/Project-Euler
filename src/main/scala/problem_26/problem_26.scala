package problem_26

import scala.math
import scala.math.BigInt

def problem_26(): Int = repetend_upto(2, 1000).maxBy(_._2)._1

def repetend_upto(curr: Int, upper: Int): List[(Int, Int)] =
  if curr == upper then Nil
  else
    repetend(curr) match
      case Some(rep) => (curr, rep) :: repetend_upto(curr + 1, upper)
      case None      => repetend_upto(curr + 1, upper)

def repetend(n: Int): Option[Int] =
  if is_not_repetend(n) then None
  else
    val rn  = to_coprime(n, 10)
    val res = bf_multiplicative_order(10, rn)
    Some(res)

def to_coprime(n: Int, m: Int): Int =
  val tmp = gcd(n, m)
  if tmp == 1 then n
  else to_coprime(n / tmp, m)

def is_not_repetend(n: Int): Boolean =
  val tmp = repeat_divs(n, 2)
  repeat_divs(tmp, 5) == 1

def repeat_divs(n: Int, d: Int): Int =
  if n % d != 0 then n
  else repeat_divs(n / d, d)

def bf_multiplicative_order(a: Int, n: Int): Int =
  rec_bf_multiplicative_order(1, BigInt(a), BigInt(a), n)

def rec_bf_multiplicative_order(k: Int, acc: BigInt, a: BigInt, n: Int): Int =
  if acc % n == 1 then k
  else rec_bf_multiplicative_order(k + 1, acc * a, a, n)

def gcd(a: Int, b: Int): Int = rec_gcd(math.max(a, b), math.min(a, b))

def rec_gcd(a: Int, b: Int): Int =
  if b == 0 then a
  else rec_gcd(b, a % b)
