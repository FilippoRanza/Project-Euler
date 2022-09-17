package problem_18

import scala.io.Source
import scala.math.max

def problem_18(): Int = process_triangle("data/problem_18/triangle.txt")

def problem_67(): Int = process_triangle("data/problem_67/p067_triangle.txt")

def process_triangle(filename: String): Int =
  val triangle = load_triangle(filename)
  bottom_traverse_triangle(triangle)

def bottom_traverse_triangle(triangle: List[List[Int]]): Int =
  triangle match
    case head :+ pre_last :+ last =>
      bottom_traverse_triangle(head :+ sum_row(pre_last, find_row_max(last)))
    case single :: Nil => single(0)
    case _             => -1

def sum_row(row_a: List[Int], row_b: List[Int]): List[Int] =
  (row_a, row_b) match
    case (ha :: rest_a, hb :: rest_b) => (ha + hb) :: sum_row(rest_a, rest_b)
    case _                            => Nil

def find_row_max(row: List[Int]): List[Int] =
  row match
    case l :: r :: rest => max(l, r) :: find_row_max(r :: rest)
    case _              => Nil

def load_triangle(filename: String): List[List[Int]] =
  Source.fromFile(filename).getLines.map(map_line).toList

def map_line(line: String): List[Int] =
  line.split(' ').map(_.toInt).toList
