package problem_8
import scala.io.Source

def problem_8(): Long =
  val numbers = load_from_file("data/problem_8/number.txt")
  val lists   = sub_lists(numbers, 13)
  find_max_prod(lists)

def find_max_prod(lists: LazyList[List[Long]]): Long =
  lists.iterator.map(_.product).max

def sub_lists(list: List[Long], len: Long): LazyList[List[Long]] =
  list match
    case h :: tail =>
      sub_list(list, len, List()) match
        case Some(subl) => subl #:: sub_lists(tail, len)
        case None       => LazyList.empty
    case Nil => LazyList.empty

def sub_list(list: List[Long], len: Long, acc: List[Long]): Option[List[Long]] =
  list match
    case h :: tail =>
      if len > 0 then sub_list(tail, len - 1, h :: acc)
      else Some(acc)
    case Nil =>
      if len > 0 then None
      else Some(acc)

def load_from_file(file: String): List[Long] =
  var output = List()
  Source
    .fromFile(file)
    .getLines
    .map(map_line)
    .foldLeft(List())((a: List[Long], b: List[Long]) => a ++ b)

def map_line(line: String): List[Long] =
  line.toList.map(_.toInt - '0'.toInt)
