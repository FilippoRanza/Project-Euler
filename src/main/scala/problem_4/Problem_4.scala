package problem_4

def problem_4(): Option[Int] =
  outer_max(100, 999)

def outer_max(a: Int, b: Int): Option[Int] =
  (a to b).iterator.map(inner_max(_, b)).foldLeft(None)(get_max)

def get_max(a: Option[Int], b: Option[Int]): Option[Int] =
  (a, b) match
    case (Some(x), Some(y)) => Some(scala.math.max(x, y))
    case (_, Some(b))       => Some(b)
    case (Some(a), _)       => Some(a)
    case _                  => None

def inner_max(a: Int, b: Int): Option[Int] =
  (a to b).iterator.map(a * _).filter(is_palindrome).foldLeft(None)(option_max)

def option_max(curr: Option[Int], value: Int): Option[Int] = curr map {
  scala.math.max(_, value)
} orElse { Some(value) }

def is_palindrome(n: Int): Boolean =
  var s = symbol_iterator.IntSymbols(n)
  palindrome.is_palindrome(s)
