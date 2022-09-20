package problem_30

def problem_30(): Int =
  val pows = make_number_pow_table(5, 0, 10)
  list_pow_sums(999, 1000000, pows).sum

def list_pow_sums(start: Int, end: Int, pows: List[Int]): LazyList[Int] =
  if start == end then LazyList.empty
  else if is_pow_sum(start, pows) then start #:: list_pow_sums(start + 1, end, pows)
  else list_pow_sums(start + 1, end, pows)

def is_pow_sum(n: Int, pows: List[Int]): Boolean =
  n == get_pow_sum(0, n, pows)

def get_pow_sum(acc: Int, n: Int, pows: List[Int]): Int =
  if n == 0 then acc
  else
    val curr = pows(n % 10)
    get_pow_sum(acc + curr, n / 10, pows)

def make_number_pow_table(pow: Int, start: Int, end: Int): List[Int] =
  if start == end then Nil
  else pow_int(1, start, pow) :: make_number_pow_table(pow, start + 1, end)

def pow_int(acc: Int, n: Int, pow: Int): Int =
  if pow == 0 then acc
  else pow_int(acc * n, n, pow - 1)
