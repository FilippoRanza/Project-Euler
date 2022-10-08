package problem_42

import scala.io.Source

def problem_42(): Int =
  val triangles = triangle_number_list(2, 1).take(1000).toList
  load_words("data/problem_42/p042_words.txt")
    .map(word_value)
    .filter(bin_search(triangles, _))
    .length

def bin_search(list: List[Int], v: Int): Boolean = rec_bin_search(list, v, 0, list.length - 1) != -1

def rec_bin_search(list: List[Int], v: Int, a: Int, b: Int): Int =
  if a <= b then
    val c = (a + b) / 2
    val m = list(c)
    if m == v then c
    else if m < v then rec_bin_search(list, v, c + 1, b)
    else rec_bin_search(list, v, a, c - 1)
  else -1

def triangle_number_list(n: Int, acc: Int): LazyList[Int] =
  acc #:: triangle_number_list(n + 1, acc + n)

def word_value(w: String): Int =
  (for c <- w yield c - 'A' + 1).sum

def load_words(filename: String): Iterator[String] =
  Source
    .fromFile(filename)
    .getLines
    .map(convert_line)
    .flatten
    .toIterator

def convert_line(line: String): Iterator[String] =
  line.split(",").map(get_word).toIterator

def get_word(token: String): String =
  val last = token.length - 1
  token.substring(1, last)
