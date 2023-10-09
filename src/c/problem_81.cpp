#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "graph.h"

// int **matrix_init(int size) {
//
//     int **output = mmalloc(sizeof(int *) * size);
//     for (int i = 0; i < size; i++)
//         output[i] = mmalloc(sizeof(int) * size);
//
//     return output;
// }

// int **load_matrix(FILE *input, int size) {
//     int **mat = matrix_init(size);
//     char *buff = mmalloc(4096);
//     int row = 0;
//     while (read_line(input, buff)) {
//         char *tmp = buff;
//         char *tok;
//         int col = 0;
//         while ((tok = strtok(tmp, ",")) != NULL) {
//             tmp = NULL;
//             int curr = atoi(tok);
//             mat[row][col++] = curr;
//         }
//         row++;
//     }
//
//     free(buff);
//
//     return mat;
// }

struct matrix {
    int* buff;
    int cols;
    int rows;

    matrix(int cols, int rows);

};


int get_node(int i, int j, int size) { return (i * size) + j; }

void graph_add_arcs(int **mat, int size, graph& g) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            int src = get_node(i, j, size);
            if (i + 1 < size) {
                int dst = get_node(i + 1, j, size);
                int cost = mat[i][j] + mat[i + 1][j];
                g.add_arc(src, dst, cost);
            }
            if (j + 1 < size) {
                int dst = get_node(i, j + 1, size);
                int cost = mat[i][j] + mat[i][j + 1];
                g.add_arc(src, dst, cost);
            }
        }
    }
}


int main() {

    int size = 80;
    int **mat = load_matrix(stdin, size);

    int nodes = size * size;
    graph g(nodes, 2);
    graph_add_arcs(mat, size, g);
    int dst = get_node(size - 1, size - 1, size);
    sp_result res = shortest_path(&g, 0, dst);

    double dist = res.cost;
    double head_tail = mat[0][0] + mat[size - 1][size - 1];
    double cost = (dist + head_tail) / 2;
    printf("%f\n", cost);
    return 0;
}
