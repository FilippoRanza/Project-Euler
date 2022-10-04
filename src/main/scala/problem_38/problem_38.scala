package problem_38

def problem_38(): Long =
  find_pandigital(1, 100000).max

def find_pandigital(a: Long, b: Long): List[Long] =
  if a == b then Nil
  else
    val next = build_concat_product(a, 1, 0) filter { is_pandigital }
    next match
      case Some(n) => n :: find_pandigital(a + 1, b)
      case None    => find_pandigital(a + 1, b)

def build_concat_product(n: Long, c: Long, acc: Long): Option[Long] =
  val dc = int_log10(acc)
  if dc < 9 then build_concat_product(n, c + 1, concat(acc, n * c))
  else if dc == 9 then Some(acc)
  else None

def concat(a: Long, b: Long): Long =
  val digits = int_log10(b)
  pow_ten(a, digits) + b

def pow_ten(n: Long, p: Long): Long =
  if p == 0 then n
  else pow_ten(n * 10, p - 1)

def int_log10(n: Long): Long = rint_log10(n, 0)

def rint_log10(n: Long, a: Long): Long =
  if n == 0 then a
  else rint_log10(n / 10, a + 1)

def is_pandigital(n: Long): Boolean =
  val count = search_pandigital(n, Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
  if count(0) > 0 then false
  else all_one(count.slice(1, 10))

def all_one(a: Array[Long]): Boolean =
  a.map(_ == 1).fold(true)(_ & _)

def search_pandigital(n: Long, count: Array[Long]): Array[Long] =
  if n == 0 then count
  else
    val d = n % 10
    count(d.toInt) += 1
    search_pandigital(n / 10, count)

def zeros(n: Long): List[Long] =
  if n == 0 then Nil
  else 0 :: zeros(n - 1)

def digits(n: Long): LazyList[Long] =
  if n == 0 then LazyList.empty
  else (n % 10) #:: digits(n / 10)
