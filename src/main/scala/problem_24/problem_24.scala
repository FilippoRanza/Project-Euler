package problem_24

def problem_24(): Option[List[Int]] =
  nth_lex_permutation(Some(Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)), 1_000_000).map(_.toList)

def nth_lex_permutation(curr: Option[Array[Int]], count: Int): Option[Array[Int]] =
  if count == 1 then curr
  else
    curr match
      case Some(c) => nth_lex_permutation(lex_permutation.next_permutation(c), count - 1)
      case None    => None
