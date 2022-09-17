package problem_13

import scala.io.Source
import scala.math.BigInt

def problem_13(): BigInt =
  val sum_val = load_numbers("data/problem_13/numbers.txt").sum
  first_n_digits(sum_val, 10)

def first_n_digits(n: BigInt, d: Int): BigInt =
  if int_log10(n) > d then first_n_digits(n / 10, d)
  else n

def int_log10(n: BigInt): Int =
  def rec_int_log10(n: BigInt, curr: Int): Int =
    if n == 0 then curr
    else rec_int_log10(n / 10, curr + 1)
  rec_int_log10(n, 0)

def load_numbers(filename: String): List[BigInt] =
  Source.fromFile(filename).getLines.map(BigInt(_)).toList
