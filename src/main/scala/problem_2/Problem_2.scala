package problem_2

def problem_2(): Int =
  fib_list(1, 2).takeWhile(_ < 4_000_000).filter(_ % 2 == 0).sum

def fib_list(a: Int, b: Int): LazyList[Int] = a #:: fib_list(b, a + b)
