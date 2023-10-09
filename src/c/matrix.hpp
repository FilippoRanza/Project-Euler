#ifndef __MATRIX_H
#define __MATRIX_H

struct matrix {
    int* buff;
    int cols;
    int rows;

    matrix(int cols, int rows);
    ~matrix();


    int get(int col, int row);
    int get(int col, int row) const;
    void set(int col, int row, int value);

};

#endif

