package problem_27

import scala.collection.immutable.HashSet
import scala.collection.mutable.HashMap

class PrimeCache(primes: HashSet[Long], upper: Long):
  private var prime_map = make_prime_map(primes, upper)
  def is_prime(n: Long): Boolean =
    if n <= 1 then false
    else 
      if !prime_map.contains(n) then prime_map.addOne((n, bf_is_prime(n)))
      prime_map.get(n).get

def bf_is_prime(n: Long): Boolean =
  if n % 2 == 0 then false
  else rec_bf_is_prime(3, n)

def rec_bf_is_prime(curr: Long, n: Long): Boolean =
  if curr == n then true
  else if n % curr == 0 then false
  else rec_bf_is_prime(curr + 2, n)

def make_prime_map(primes: HashSet[Long], upper: Long): HashMap[Long, Boolean] =
  HashMap[Long, Boolean]() ++= (1L to upper).map(n => (n, primes.contains(n)))

def problem_27(): Long =
  val prime_list = prime_sieve.sieve(3, (c, _) => c > 1_000, List(2))
  val prime_set  = HashSet() ++ prime_list

  var primes = PrimeCache(prime_set, 1_000)

  val output = find_best_for_primes(prime_list, prime_set, primes)

  output._1

def find_best_for_primes(
    prime_list: List[Long],
    prime_set: HashSet[Long],
    primes: PrimeCache
): (Long, Long) =
  prime_list.map(b => find_best_for_prime(b, prime_set, primes)).maxBy(_._2)


def find_best_for_prime(b: Long, prime_set: HashSet[Long], primes: PrimeCache): (Long, Long) =
  val (a, count) =
    suitable_coeffs(b, prime_set).map(a => (a, prime_seq_len(0, a, b, primes))).maxBy(_._2)
  (a * b, count)

def prime_seq_len(total: Long, a: Long, b: Long, primes: PrimeCache): Long =
  val c = (total * total) + (a * total) + b
  if primes.is_prime(c) then prime_seq_len(total + 1, a, b, primes)
  else total

def suitable_coeffs(b: Long, prime_set: HashSet[Long]): List[Long] =
  (-999 to 999).filter(a => prime_set.contains(a + b + 1)).map(_.toLong).toList
