package factorial

import scala.math.BigInt


def factorial(n: Int): BigInt = rec_fact(n, BigInt(1))


def rec_fact(n: Int, res: BigInt): BigInt = 
  if n == 1 then
    res
  else
    rec_fact(n - 1, res * n)



