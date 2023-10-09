#include "matrix.hpp"

matrix::matrix(int cols, int rows) : cols(cols), rows(rows) {
    int tot = cols * rows;
    buff = new int[tot];
}

matrix::~matrix() { delete[] buff; }

int get_index(matrix *mat, int c, int r) { return mat->rows * c + r; }
int get_index(const matrix *mat, int c, int r) { return mat->rows * c + r; }

int matrix::get(int col, int row) {
    int idx = get_index(this, col, row);
    return buff[idx];
}
int matrix::get(int col, int row) const {
    int idx = get_index(this, col, row);
    return buff[idx];
}

void matrix::set(int col, int row, int value) {
    int idx = get_index(this, col, row);
    buff[idx] = value;
}
