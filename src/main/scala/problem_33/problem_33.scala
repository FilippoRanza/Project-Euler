package problem_33

//import scala.collection.immutable.IndexedSequence

def problem_33(): Int =
  val fracs = get_naive_simplify_fractions().toList
  val prod  = prod_fractions(fracs, (1, 1))
  simplify_fraction(prod)._2

def prod_fractions(fracs: List[(Int, Int)], acc: (Int, Int)): (Int, Int) =
  fracs match
    case h :: tail => prod_fractions(tail, mult_fracs(acc, h))
    case Nil       => acc

def mult_fracs(f1: (Int, Int), f2: (Int, Int)) =
  (f1._1 * f2._1, f1._2 * f2._2)

def get_naive_simplify_fractions(): IndexedSeq[(Int, Int)] =
  for (i <- 10 to 99; j <- (i + 1) to 99 if is_naive_simpliable(i, j))
    yield (i, j)

def is_naive_simpliable(a: Int, b: Int): Boolean =
  naive_simplify(a, b) match
    case Some((sa, sb)) => equivalent_fractions((a, b), (sa, sb))
    case None           => false

def naive_simplify(a: Int, b: Int): Option[(Int, Int)] =
  val (a1, a2) = get_digits(a)
  val (b1, b2) = get_digits(b)
  if a1 == b1 then Some((a2, b2))
  else if a1 == b2 then Some((a2, b1))
  else if a2 == b1 then Some((a1, b2))
  else if a2 == b2 && a2 != 0 then Some((a1, b1))
  else None

def equivalent_fractions(fr1: (Int, Int), fr2: (Int, Int)): Boolean =
  simplify_fraction(fr1) == simplify_fraction(fr2)

def simplify_fraction(fr: (Int, Int)): (Int, Int) =
  val (a, b) = fr
  val s      = gcd(a, b)
  (a / s, b / s)

def gcd(a: Int, b: Int): Int =
  if a > b then rec_gcd(a, b)
  else rec_gcd(b, a)

def rec_gcd(a: Int, b: Int): Int =
  if b != 0 then rec_gcd(b, a % b)
  else a

def get_digits(a: Int): (Int, Int) =
  (a / 10, a % 10)
