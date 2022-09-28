package problem_32

import scala.collection.mutable.HashSet

def problem_32(): Int =
  get_all_pandigitals()

def get_all_pandigitals(): Int =
  var set: HashSet[Int] = HashSet()
  for a <- (1 to 999) do
    for b <- (a to 9999) do
      val c = a * b
      if is_pandigital(a, b, c) then set += c
  set.sum

def is_pandigital(a: Int, b: Int, c: Int): Boolean =
  var count = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
  for n <- List(a, b, c) do for i <- get_digits(n) do count(i) += 1

  if count(0) > 0 then false
  else count.slice(1, 10).map(_ == 1).fold(true)((acc, curr) => acc && curr)

def get_digits(n: Int): List[Int] =
  if n == 0 then Nil
  else (n % 10) :: get_digits(n / 10)
