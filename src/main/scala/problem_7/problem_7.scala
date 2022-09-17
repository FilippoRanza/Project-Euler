package problem_7

def problem_7(): Long =
  prime_sieve.sieve(3, (_, l) => l.length == 10_001, List(2)).last
