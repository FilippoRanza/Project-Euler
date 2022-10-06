package problem_40

def problem_40(): Int =
  champernowne()
    .take(1000000)
    .zipWithIndex
    .map((v, i) => (v, i + 1))
    .filter((v, i) => is_pow_10(i))
    .map(_._1)
    .product

def is_pow_10(n: Int): Boolean = n == pow10(int_log10(n) - 1)

def champernowne(): LazyList[Int] =
  num_list(1).flatMap(digits)

def num_list(curr: Int): LazyList[Int] =
  curr #:: num_list(curr + 1)

def digits(n: Int): LazyList[Int] = rec_digits(n, int_log10(n))

def rec_digits(n: Int, d: Int): LazyList[Int] =
  if d == 0 then LazyList.empty
  else
    val p = pow10(d - 1)
    (n / p) #:: rec_digits(n % p, d - 1)

def pow10(d: Int): Int = rec_pow10(d, 1)

def rec_pow10(d: Int, acc: Int): Int =
  if d == 0 then acc
  else rec_pow10(d - 1, acc * 10)

def int_log10(n: Int): Int = rec_int_log10(0, n)

def rec_int_log10(acc: Int, n: Int): Int =
  if n == 0 then acc
  else rec_int_log10(acc + 1, n / 10)
