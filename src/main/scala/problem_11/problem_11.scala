package problem_11

import scala.io.Source
import scala.math.max

type Index = (Int, Int)
type Incr  = (Index) => Index

class Grid(grid: Array[Array[Int]]):
  private var values = grid
  val rows           = grid.length
  val cols           = grid(0).length
  def get(idx: Index): Option[Int] =
    val (x, y) = idx
    if x < 0 || x >= rows then None
    else if y < 0 || y >= cols then None
    else Some(values(x)(y))

  def get_values(idxs: LazyList[Index]): LazyList[Option[Int]] =
    idxs.map(this.get(_))

def problem_11(): Option[Int] =
  val grid = load_grid_file("data/problem_11/grid.txt")
  val rows = grid.rows
  val cols = grid.cols
  (1 to rows).iterator.map(r => max_grid_prod_row(grid, r, cols, 4)).flatten.reduce(option_max)

def max_grid_prod_row(grid: Grid, row: Int, cols: Int, count: Int): Iterator[Option[Int]] =
  (1 to cols).iterator.map(c => max_grid_prod_at(grid, (row, c), count)).flatten

def max_grid_prod_at(grid: Grid, index: Index, count: Int): List[Option[Int]] =
  val increments = List(right_diag_incr, left_diag_incr, col_incr, row_incr)
  increments.map(mult_grid_line(grid, index, _, count))

def option_list_max(opt_list: List[Option[Int]]): Option[Int] =
  opt_list reduce option_max

def option_max(a: Option[Int], b: Option[Int]): Option[Int] =
  (a, b) match
    case (Some(x), Some(y)) => Some(max(x, y))
    case (Some(_), _)       => a
    case (_, Some(_))       => b
    case _                  => None

def mult_grid_line(grid: Grid, begin: Index, incr: Incr, count: Int): Option[Int] =
  val indexes = line_index_list(begin, incr, count)
  val values  = grid.get_values(indexes)
  mult_option_list(values)

def right_diag_incr(idx: Index): Index =
  val (a, b) = idx
  (a + 1, b + 1)

def left_diag_incr(idx: Index): Index =
  val (a, b) = idx
  (a + 1, b - 1)

def col_incr(idx: Index): Index =
  val (a, b) = idx
  (a + 1, b)

def row_incr(idx: Index): Index =
  val (a, b) = idx
  (a, b + 1)

def line_index_list(begin: Index, incr: Incr, count: Int): LazyList[Index] =
  if count == 0 then LazyList.empty
  else begin #:: line_index_list(incr(begin), incr, count - 1)

def mult_option_list(ops: LazyList[Option[Int]]): Option[Int] =
  ops.foldLeft(Some(1))(mult_option)

def mult_option(x: Option[Int], y: Option[Int]): Option[Int] =
  (x, y) match
    case (Some(a), Some(b)) => Some(a * b)
    case _                  => None

def load_grid_file(filename: String): Grid =
  val grid = Source.fromFile(filename).getLines.map(line_to_row).toArray
  Grid(grid)

def line_to_row(line: String): Array[Int] =
  line.split(' ').map(_.toInt)
