package problem_12

def problem_12(): Option[Int] =
  triangular_numbers(20000, 1, 2)
    .map(n => (n, divisors.fast_divisors(n).length))
    .find((n, d) => d >= 500)
    .map((n, _) => n)

def triangular_numbers(count: Int, prev: Int, idx: Int): List[Int] =
  if count == 0 then Nil
  else prev :: triangular_numbers(count - 1, prev + idx, idx + 1)
