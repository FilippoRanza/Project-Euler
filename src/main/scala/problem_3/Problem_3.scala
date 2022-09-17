package problem_3
 
def problem_3(): Long = factor_list(600851475143, 2).max

def factor_list(n: Long, curr: Long): LazyList[Long] =
  if n == 1 then LazyList.empty
  else if n % curr == 0 then curr #:: factor_list(n / curr, curr + 1)
  else factor_list(n, curr + 1)
