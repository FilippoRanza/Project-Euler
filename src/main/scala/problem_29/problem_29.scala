package problem_29

import scala.collection.immutable.HashSet

type Factors = List[(Int, Int)]

def problem_29(): Int =
  val pows = compute_all_pows(2, 100, 2, 100)
  all_unique_pows(pows).size

def all_unique_pows(pows: LazyList[LazyList[Factors]]): HashSet[Factors] =
  pows.foldLeft(HashSet[Factors]())((acc, list) => acc ++ list)

def compute_all_pows(
    base_start: Int,
    base_end: Int,
    exp_start: Int,
    exp_end: Int
): LazyList[LazyList[Factors]] =
  if base_start > base_end then LazyList.empty
  else
    val facts      = factors(base_start, 2, 0)
    val base_facts = raise_to_pow(facts, exp_start)
    compute_pows(base_facts, exp_start, exp_end) #:: compute_all_pows(
      base_start + 1,
      base_end,
      exp_start,
      exp_end
    )

def compute_pows(base: Factors, curr: Int, end: Int): LazyList[Factors] =
  if curr > end then LazyList.empty
  else
    val curr_pow = raise_to_pow(base, curr)
    curr_pow #:: compute_pows(base, curr + 1, end)

def raise_to_pow(facts: Factors, pow: Int): Factors =
  facts.map((f, p) => (f, p * pow)).toList

def factors(n: Int, curr: Int, count: Int): Factors =
  if n == 1 then List((curr, count))
  else if n % curr == 0 then factors(n / curr, curr, count + 1)
  else if count != 0 then (curr, count) :: factors(n, curr + 1, 0)
  else factors(n, curr + 1, 0)
