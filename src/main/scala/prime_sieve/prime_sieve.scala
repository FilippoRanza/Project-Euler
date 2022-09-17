package prime_sieve

def sieve(curr: Long, cond: (Long, List[Long]) => Boolean, primes: List[Long]): List[Long] =
  if cond(curr, primes) then primes
  else if is_prime(curr, primes) then sieve(curr + 2, cond, primes :+ curr)
  else sieve(curr + 2, cond, primes)

def is_prime(n: Long, primes: List[Long]): Boolean =
  val upper = upper_limit(n)
  rec_is_prime(n, upper, primes)

def rec_is_prime(n: Long, upper: Long, primes: List[Long]): Boolean =
  primes match
    case p :: rest =>
      if n % p == 0 then false
      else if p > upper then true
      else rec_is_prime(n, upper, rest)
    case Nil => true

def upper_limit(n: Long): Long = math.ceil(math.sqrt(n)).longValue
