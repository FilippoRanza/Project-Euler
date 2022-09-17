package problem_19

def problem_19(): Int =
  val day_of_week = days_after(0, 365)
  count_sundays(day_of_week, 0, 1901, 2001, 0)

def count_sundays(curr_dow: Int, curr_month: Int, curr_year: Int, last_year: Int, acc: Int): Int =
  val curr_acc = curr_dow match
    case 6 => acc + 1
    case _ => acc

  val next_dow                = step_one_month(curr_dow, curr_month, curr_year)
  val (next_month, next_year) = next_month_year(curr_month, curr_year)
  if next_year == last_year then curr_acc
  else count_sundays(next_dow, next_month, next_year, last_year, curr_acc)

def next_month_year(month: Int, year: Int): (Int, Int) =
  val next_month = month + 1
  val next_year  = year + (next_month / 12)
  (next_month % 12, next_year)

def step_one_month(day_of_week: Int, month: Int, year: Int): Int =
  var days_ahead = days_in_month(month, year)
  days_after(day_of_week, days_ahead)

def days_after(day_of_week: Int, days_ahead: Int): Int =
  (day_of_week + days_ahead) % 7

def days_in_month(month: Int, year: Int): Int =
  val days = List(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
  if month == 1 && is_leap(year) then days(month) + 1
  else days(month)

def is_leap(year: Int): Boolean =
  (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
