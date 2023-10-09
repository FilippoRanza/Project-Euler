#ifndef __GRAPH_H
#define __GRAPH_H

#include <vector>

struct arc {
    int dst;
    int cost;

    arc(int dst, int cost) : dst(dst), cost(cost) {}
};

struct adj_list {
    std::vector<arc> arcs;
    adj_list(std::vector<arc> arcs) : arcs(arcs) {}
};

struct graph {
    std::vector<adj_list> lists;
    int nodes;
    int arcs;

    graph(int nodes);
    graph(int nodes, int deg);

    void add_arc(int src, int dst, int cost);
};

typedef struct {
    int *nodes;
    int len;
} sp_vect;

struct sp_result {
    std::vector<int> path;
    int cost;
};

sp_result shortest_path(graph *g, int src, int dst);

#endif
