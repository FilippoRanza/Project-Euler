package problem_43

def problem_43(): Long =
  make_all_permutations(Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))
    .map(join_number)
    .filter(l => substring_divisibile(l, List(17, 13, 11, 7, 5, 3, 2)))
    .toList
    .sum

def substring_divisibile(n: Long, primes: List[Long]): Boolean =
  primes match
    case p :: tail if check_div(n, p) => substring_divisibile(n / 10, tail)
    case Nil                          => true
    case _                            => false

def check_div(n: Long, p: Long): Boolean =
  (n % 1000) % p == 0

def join_number(a: Array[Long]): Long =
  rec_join_number(a.toList, 0)

def rec_join_number(a: List[Long], acc: Long): Long =
  a match
    case h :: tail => rec_join_number(tail, (acc * 10) + h)
    case Nil       => acc

def make_all_permutations(list: Array[Long]): LazyList[Array[Long]] =
  make_permutations(list) match
    case Some(next) => list #:: make_all_permutations(next)
    case None       => LazyList.empty

def make_permutations(list: Array[Long]): Option[Array[Long]] =
  find_last_k(list.toList, None, 0)
    .map(k => (k, find_largest_l(list, k)))
    .map((k, l) => (k, swap(list, k, l)))
    .map(reverse_tail)

def find_last_k(list: List[Long], k: Option[Long], curr: Long): Option[Long] =
  list match
    case h1 :: h2 :: tail => find_last_k(h2 :: tail, next_opt(k, curr, h1, h2), curr + 1)
    case _                => k

def find_largest_l(list: Array[Long], k: Long): Long =
  rec_find_largest_l(list, k, k + 1, k + 1)

def rec_find_largest_l(list: Array[Long], k: Long, l: Long, curr: Long): Long =
  if l == list.length then curr
  else rec_find_largest_l(list, k, l + 1, get_next_l(list, k, l, curr))

def get_next_l(list: Array[Long], k: Long, l: Long, curr: Long) =
  if list(k.toInt) < list(l.toInt) then l
  else curr

def swap(list: Array[Long], a: Long, b: Long): Array[Long] =
  val tmp = list(a.toInt)
  list(a.toInt) = list(b.toInt)
  list(b.toInt) = tmp
  list

def reverse_tail(k: Long, list: Array[Long]): Array[Long] =
  rec_reverse_tail(k + 1, list.length - 1, list)

def rec_reverse_tail(a: Long, b: Long, list: Array[Long]): Array[Long] =
  if a >= b then list
  else rec_reverse_tail(a + 1, b - 1, swap(list, a, b))

def next_opt(curr: Option[Long], idx: Long, v1: Long, v2: Long): Option[Long] =
  if v1 < v2 then Some(idx)
  else curr
