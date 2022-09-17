package problem_14

import scala.collection.mutable.HashMap

class CollatzValue():
  private var next_value   = HashMap[Long, Long]()
  private var steps_to_one = HashMap[Long, Long]()

  def contains(n: Long): Boolean        = next_value.contains(n)
  def get_values(n: Long): (Long, Long) = (next_value.get(n).get, steps_to_one.get(n).get)
  def get_next(n: Long): Long           = next_value.get(n).get
  def get_steps(n: Long): Long          = steps_to_one.get(n).get

  def insert_next_for(curr: Long, next: Long, steps: Long) =
    next_value.put(curr, next)
    steps_to_one.put(curr, steps)

def problem_14(): Long =
  var store = CollatzValue()
  (1 to 1_000_000).iterator.map(dyn_collatz(_, store)).foldLeft((1L, 1L))(max_by)._1

def max_by(acc: (Long, Long), curr: (Long, Long)): (Long, Long) =
  var (acc_n, acc_l)   = acc
  var (curr_n, curr_l) = curr
  if curr_l > acc_l then curr
  else acc

def dyn_collatz(n: Long, store: CollatzValue): (Long, Long) =
  if n == 1 then (n, 1)
  else if store.contains(n) then store.get_values(n)
  else
    val (next, steps) = dyn_collatz(next_collatz(n), store)
    store.insert_next_for(n, next, steps + 1)
    (n, steps + 1)

def collatz(n: Long): List[Long] =
  if n == 1 then 1 :: Nil
  else n :: collatz(next_collatz(n))

def next_collatz(n: Long): Long =
  if n % 2 == 0 then n / 2
  else 3 * n + 1
