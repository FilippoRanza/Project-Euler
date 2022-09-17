package problem_10

import scala.math

def problem_10(): Long = prime_sieve.sieve(3, (c, _) => c >= 2_000_000, List(2)).sum
