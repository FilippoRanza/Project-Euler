package problem_6

def problem_6(): Int = (1 to 100).iterator.map(cube_minus_square).sum

def cube_minus_square(n: Int) = n * n * (n - 1)
