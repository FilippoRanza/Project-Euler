package problem_17

def problem_17(): Int =
  (1 to 1_000).iterator.map(get_digits).map(map_thousand).map(_.length).sum

def get_digits(n: Int): (Int, Int, Int, Int) =
  val u  = n % 10
  val t  = n % 100 / 10
  val h  = n % 1000 / 100
  val th = n % 10000 / 1000
  (th, h, t, u)

def map_thousand(th: Int, h: Int, t: Int, u: Int): String =
  val th_str =
    if th != 0 then map_one_digit(th) + "thousand"
    else ""
  th_str + map_hundreds(h, t, u)

def map_hundreds(h: Int, t: Int, u: Int): String =
  val h_str =
    if h != 0 then map_one_digit(h) + "hundred"
    else ""
  val join =
    if h != 0 && (t != 0 || u != 0) then "and"
    else ""

  h_str + join + map_teen_digit(t, u)

def map_teen_digit(t: Int, u: Int): String =
  if t == 1 then
    u match
      case 0 => "ten"
      case 1 => "eleven"
      case 2 => "twelve"
      case 3 => "thirteen"
      case 4 => "fourteen"
      case 5 => "fifteen"
      case 6 => "sixteen"
      case 7 => "seventeen"
      case 8 => "eighteen"
      case 9 => "nineteen"
  else
    val teen = t match
      case 0 => ""
      case 2 => "twenty"
      case 3 => "thirty"
      case 4 => "forty"
      case 5 => "fifty"
      case 6 => "sixty"
      case 7 => "seventy"
      case 8 => "eighty"
      case 9 => "ninety"
    teen + map_one_digit(u)

def map_one_digit(u: Int): String =
  u match
    case 0 => ""
    case 1 => "one"
    case 2 => "two"
    case 3 => "three"
    case 4 => "four"
    case 5 => "five"
    case 6 => "six"
    case 7 => "seven"
    case 8 => "eight"
    case 9 => "nine"
