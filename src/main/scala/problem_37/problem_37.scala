package problem_37

def problem_37(): Long =
  println(next_prime(List(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L)))
  val t_primes = find_truncatable_primes(List(2, 3, 5, 7, 11), 11, List())
  println(t_primes)
  t_primes.sum

def find_truncatable_primes(curr_primes: List[Long], missing: Int, acc: List[Long]): List[Long] =
  if missing == 0 then acc
  else
    val primes = next_prime(curr_primes)
    val curr_p = primes.last
    if is_truncatable(curr_p, primes) then
      find_truncatable_primes(primes, missing - 1, acc :+ curr_p)
    else find_truncatable_primes(primes, missing, acc)

def is_truncatable(curr_p: Long, curr_primes: List[Long]): Boolean =
  search_trunc(truncate_right(curr_p), curr_primes) && search_trunc(
    truncate_left(curr_p),
    curr_primes
  )

def search_trunc(trunc: LazyList[Long], curr_primes: List[Long]): Boolean =
  trunc match
    case h #:: tail =>
      if bin_search(curr_primes, h).isDefined then search_trunc(tail, curr_primes)
      else false
    case _ => true

def next_prime(curr: List[Long]): List[Long] =
  find_next_prime(curr.last + 2, curr)

def find_next_prime(curr: Long, list: List[Long]): List[Long] =
  if prime_sieve.is_prime(curr, list) then list :+ curr
  else find_next_prime(curr + 2, list)

def truncate_left(n: Long): LazyList[Long] = rec_truncate_left(n, int_log10(n, 0) - 1)

def rec_truncate_left(n: Long, d: Long): LazyList[Long] =
  if n == 0 then LazyList.empty
  else n #:: rec_truncate_left(n % pow_ten(d), d - 1)

def int_log10(n: Long, acc: Long): Long =
  if n == 0 then acc
  else int_log10(n / 10, acc + 1)

def pow_ten(d: Long): Long = (1L to d).fold(1L)((c, _) => c * 10)

def truncate_right(n: Long): LazyList[Long] =
  if n == 0 then LazyList.empty
  else n #:: truncate_right(n / 10)

def is_prime(primes: List[Long], n: Long): Boolean = bin_search(primes, n).isDefined

def bin_search(list: List[Long], t: Long): Option[Long] = rec_bin_search(list, 0, list.length, t)

def rec_bin_search(list: List[Long], a: Long, b: Long, t: Long): Option[Long] =
  if a <= b then
    val middle = (a + b) / 2
    val value  = list(middle.toInt)
    if value == t then Some(middle)
    else if value > t then rec_bin_search(list, a, middle - 1, t)
    else rec_bin_search(list, middle + 1, b, t)
  else None
