package problem_35

import scala.collection.immutable.HashMap
import scala.collection.immutable.HashSet

def problem_35(): Long =
  val primes          = prime_sieve.sieve(3, (n, _) => n >= 1_000_000, List(2))
  val prime_set       = HashSet() ++ primes
  val rot_prime_table = check_rotations(primes, prime_set, HashMap())
  rot_prime_table.count(_._2)

def check_rotations(
    primes: List[Long],
    prime_set: HashSet[Long],
    table: HashMap[Long, Boolean]
): HashMap[Long, Boolean] =
  primes match
    case p :: rest => check_rotations(rest, prime_set, check_rot_prime(p, prime_set, table))
    case Nil       => table

def check_rot_prime(
    p: Long,
    prime_set: HashSet[Long],
    table: HashMap[Long, Boolean]
): HashMap[Long, Boolean] =
  table.get(p) match
    case Some(_) => table
    case None    => eval_rots(p, prime_set, table)

def eval_rots(
    p: Long,
    prime_set: HashSet[Long],
    table: HashMap[Long, Boolean]
): HashMap[Long, Boolean] =
  val rots   = rotations(p).toList
  val is_rot = rec_check_rot_prime(rots, prime_set)
  insert_rotations(rots, table, is_rot)

def insert_rotations(
    rots: List[Long],
    table: HashMap[Long, Boolean],
    value: Boolean
): HashMap[Long, Boolean] =
  rots match
    case r :: tail => insert_rotations(tail, insert_if_missing(table, r, value), value)
    case Nil       => table

def insert_if_missing(
    table: HashMap[Long, Boolean],
    r: Long,
    value: Boolean
): HashMap[Long, Boolean] =
  if table.contains(r) then table
  else table + ((r, value))

def rec_check_rot_prime(rot_list: List[Long], prime_set: HashSet[Long]): Boolean =
  rot_list match
    case r :: rest =>
      if prime_set.contains(r) then rec_check_rot_prime(rest, prime_set)
      else false
    case Nil => true

def rotations(n: Long): LazyList[Long] =
  val digits = int_log10(n)
  rec_rotations(n, digits, digits)

def rec_rotations(n: Long, d: Long, r: Long): LazyList[Long] =
  if r == 0 then LazyList.empty
  else
    val next = next_rot(n, d)
    next #:: rec_rotations(next, d, r - 1)

def next_rot(n: Long, d: Long): Long =
  val last = n % 10
  (pow_ten(last, d - 1) + (n / 10))

def pow_ten(n: Long, d: Long): Long =
  if d == 0 then n
  else pow_ten(n * 10, d - 1)

def int_log10(n: Long): Long = rec_int_log10(n, 0)

def rec_int_log10(n: Long, acc: Long): Long =
  if n == 0 then acc
  else rec_int_log10(n / 10, acc + 1)
