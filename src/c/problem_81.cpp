#include <boost/tokenizer.hpp>
#include <fstream>
#include <iostream>
#include <string.h>
#include <string>

#include "graph.h"
#include "matrix.hpp"

matrix load_matrix(std::istream& file, int size) {

    matrix mat(size, size);
    std::string line;
    int row = 0;
    while (getline(file, line)) {
        boost::tokenizer<> tokens(line);  
        int col = 0;
        for(const auto& tok: tokens) {
            int value = stoi(tok);
            mat.set(col++, row, value);
        }
        row++;
    }
    return mat;
}

int get_node(int i, int j, int size) { return (i * size) + j; }

void graph_add_arcs(const matrix& mat, int size, graph &g) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            int src = get_node(i, j, size);
            if (i + 1 < size) {
                int dst = get_node(i + 1, j, size);
                int cost = mat.get(i, j) + mat.get(i + 1, j);
                g.add_arc(src, dst, cost);
            }
            if (j + 1 < size) {
                int dst = get_node(i, j + 1, size);
                int cost = mat.get(i, j) + mat.get(i, j + 1);
                g.add_arc(src, dst, cost);
            }
        }
    }
}

int main() {

    int size = 80;
    const auto mat = load_matrix(std::cin, size);

    int nodes = size * size;
    graph g(nodes, 2);
    graph_add_arcs(mat, size, g);
    int dst = get_node(size - 1, size - 1, size);

    sp_result res = shortest_path(&g, 0, dst);

    double dist = res.cost;
    double head_tail = mat.get(0, 0) + mat.get(size - 1, size - 1);
    double cost = (dist + head_tail) / 2;
    printf("%f\n", cost);
    return 0;
}
