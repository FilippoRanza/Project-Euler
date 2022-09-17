package palindrome

import scala.collection.mutable.Stack

def is_palindrome[T](n: symbol_iterator.SymbolIterator[T]): Boolean =
  val size  = n.size()
  val limit = size / 2

  def check = if size % 2 == 0 then (n: Int) => n >= limit
  else (n: Int) => n > limit

  def cond_push(i: Int, s: T, stack: Stack[T]): Stack[T] =
    if i < limit then stack.push(s)
    else stack

  rec_palindrome(n.iter(), 0, check, cond_push, new Stack[T])

def rec_palindrome[T](
    list: LazyList[T],
    i: Int,
    check: (Int) => Boolean,
    push: (Int, T, Stack[T]) => Stack[T],
    stack: Stack[T]
): Boolean =
  list match
    case s #:: rest =>
      if check(i) then
        val tmp = stack.pop()
        if tmp != s then false
        else rec_palindrome(rest, i + 1, check, push, stack)
      else rec_palindrome(rest, i + 1, check, push, push(i, s, stack))

    case LazyList() => true
