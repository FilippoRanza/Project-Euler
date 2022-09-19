package problem_28

def problem_28(): Int = 
  get_diagonals_on_frame(1001).sum

def get_diagonals_on_frame(frame_size: Int): LazyList[Int] =
  val count = 2*frame_size - 1
  rec_get_diagonals_on_frame(1, 2, 0, count)


def rec_get_diagonals_on_frame(start_value: Int, incr: Int, index: Int, count: Int): LazyList[Int] =
  if count == 0 then LazyList.empty
  else
    val next_incr = get_next_incr(incr, index)
    start_value #:: rec_get_diagonals_on_frame(
      start_value + next_incr,
      next_incr,
      index + 1,
      count - 1
    )

def get_next_incr(incr: Int, index: Int): Int =
  if index > 0 && index % 4 == 0 then incr + 2
  else incr
