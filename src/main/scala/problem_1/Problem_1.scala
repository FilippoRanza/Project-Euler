package problem_1

def problem_1(): Int =
  (1 to 999).iterator.filter(is_multiple).sum

def is_multiple(n: Int): Boolean = n % 3 == 0 || n % 5 == 0
