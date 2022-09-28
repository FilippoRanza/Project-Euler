package problem_34

def problem_34(): Int =
  (3 to 10000000).filter(is_dfs).sum

def is_dfs(n: Int): Boolean = n == digit_factorial_sum(n)

def digit_factorial_sum(n: Int): Int = rec_dfs(0, n)

def rec_dfs(acc: Int, n: Int): Int =
  if n == 0 then acc
  else rec_dfs(acc + fact(n % 10), n / 10)

def fact(n: Int) = rfact(n, 1)

def rfact(n: Int, acc: Int): Int =
  if n == 1 || n == 0 then acc
  else rfact(n - 1, acc * n)
