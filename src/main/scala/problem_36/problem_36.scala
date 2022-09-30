package problem_36

def problem_36(): Int =
  (1 to 1_000_000).filter(n => is_double_base_palindrome(n, 10, 2)).sum

def is_double_base_palindrome(n: Int, b1: Int, b2: Int): Boolean =
  is_palindrome_base(n, b1) && is_palindrome_base(n, b2)

def is_palindrome_base(n: Int, base: Int): Boolean =
  n == invert_number(n, base, 0)

def invert_number(n: Int, base: Int, acc: Int): Int =
  if n == 0 then acc
  else invert_number(n / base, base, (acc * base) + (n % base))
