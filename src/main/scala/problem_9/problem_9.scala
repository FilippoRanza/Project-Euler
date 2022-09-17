package problem_9

def problem_9(): List[Int] =
  solve_mn_equation(1000 / 2).map(make_pythagorean_triple).map(triplet_product)

def triplet_product(t: (Int, Int, Int)): Int =
  val (a, b, c) = t
  a * b * c

def make_pythagorean_triple(m: Int, n: Int): (Int, Int, Int) =
  (m * m - n * n, 2 * m * n, m * m + n * n)

def solve_mn_equation(coeff: Int): List[(Int, Int)] =
  val divs = divisors.divisors(coeff, 1)
  divs.map(m => (m, eval_equation(coeff, m))).filter((m, n) => n > 0).filter((m, n) => m > n)

def eval_equation(coeff: Int, m: Int): Int = (coeff / m) - m
