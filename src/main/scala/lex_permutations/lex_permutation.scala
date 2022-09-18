package lex_permutation

def next_permutation(curr: Array[Int]): Option[Array[Int]] =
  largest_k_index(None, curr).map(k => permute(curr, k))

def permute(curr: Array[Int], k: Int): Array[Int] =
  val i        = largest_i_index(k, curr)
  val tmp_list = swap_values(k, i, curr)
  reverse_from(curr, k + 1)

def reverse_from(array: Array[Int], f: Int): Array[Int] =
  rec_reverse(array, f, array.length - 1)

def rec_reverse(array: Array[Int], a: Int, b: Int): Array[Int] =
  if a >= b then array
  else rec_reverse(swap_values(a, b, array), a + 1, b - 1)

def swap_values(a: Int, b: Int, list: Array[Int]): Array[Int] =
  val va = list(a)
  val vb = list(b)
  list(a) = vb
  list(b) = va
  list

def largest_k_index(acc: Option[Int], curr: Array[Int]): Option[Int] =
  var prev: Option[Int]   = None
  var output: Option[Int] = None
  for (v, i) <- curr.zipWithIndex do
    prev match
      case Some(p) => if p < v then output = Some(i - 1)
      case None    => {}
    prev = Some(v)
  output

def compare_values(k: Int, acc: Option[Int], a: Int, b: Int): Option[Int] =
  if a < b then Some(k)
  else acc

def largest_i_index(k: Int, curr: Array[Int]): Int =
  val list  = get_last(k + 1, curr)
  val k_val = curr(k)
  search_largest_i_index(k_val, k + 1, list)

def search_largest_i_index(k_val: Int, acc: Int, list: Array[Int]): Int =
  list.zipWithIndex.filter((v, _) => k_val < v).maxBy((v, i) => i)._2 + acc

def get_last(k: Int, curr: Array[Int]): Array[Int] =
  val last = curr.length
  curr.slice(k, last)
