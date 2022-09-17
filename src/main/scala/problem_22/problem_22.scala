package problem_22

import scala.io.Source
import scala.math.BigInt

def problem_22(): BigInt =
  val names = load_names("data/problem_22/names.txt")
  scores_list(names, 1, 0)

def scores_list(names: List[String], curr_idx: BigInt, acc: BigInt): BigInt =
  names match
    case curr :: rest => scores_list(rest, curr_idx + 1, curr_idx * alphabetical_value(curr) + acc)
    case Nil          => acc

def alphabetical_value(name: String): BigInt =
  name.iterator.map(_ - 'A' + 1).sum

def load_names(filename: String): List[String] =
  Source.fromFile(filename).getLines.map(get_name_list).flatten.toList.sortWith(_ < _)

def get_name_list(line: String): List[String] =
  line.split(',').map(remove_quote).toList

def remove_quote(name: String): String =
  val last = name.length - 1
  name.substring(1, last)
