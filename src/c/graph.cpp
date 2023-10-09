#include "graph.h"

#include <limits.h>
#include <queue>
#include <iostream>

graph::graph(int nodes) : graph(nodes, 1) {}
graph::graph(int nodes, int deg) {
    this->nodes = nodes;
    this->arcs = 0;
    this->lists = std::vector<adj_list>();
    this->lists.reserve(nodes);
    for (int i; i < nodes; i++) {
        auto tmp = std::vector<arc>();
        tmp.reserve(deg);
        this->lists.push_back(adj_list(tmp));
    }
}

void graph::add_arc(int src, int dst, int cost) {
    auto a = arc(dst, cost);
    this->lists[src].arcs.push_back(a);
}

struct node_cost {
    int node;
    int cost;

    bool operator<(const node_cost &other) const;
    static node_cost init(int node, int cost);
};

bool node_cost::operator<(const node_cost &other) const {
    return this->cost < other.cost;
}
node_cost node_cost::init(int node, int cost) {
    node_cost output = {.node = node, .cost = cost};
    return output;
}

struct dijkstra_result {
    std::vector<int> prev;
    std::vector<int> dist;

    static dijkstra_result init(std::vector<int> prev, std::vector<int> dist) {
        dijkstra_result output = {.prev = prev, .dist = dist};
        return output;
    }
};

dijkstra_result dijkstra(graph *g, int src, int dst) {
    auto prev = std::vector<int>();
    auto dist = std::vector<int>();
    prev.reserve(g->nodes);
    dist.reserve(g->nodes);
    for (int i = 0; i < g->nodes; i++) {
        prev.push_back(-1);
        dist.push_back(INT_MAX);
    }

    dist[src] = 0;
    int missing = g->nodes;
    auto queue = std::priority_queue<node_cost>();
    queue.push(node_cost::init(src, 0));
    while (!queue.empty()) {
        auto curr = queue.top();
        queue.pop();

        int node = curr.node;
        int cost = curr.cost;
        auto adj = g->lists[node];
        for (auto &next : adj.arcs) {
            int curr_cost = next.cost;
            int curr_dst = next.dst;
            int alt = dist[node] + curr_cost;
            if (alt < dist[curr_dst]) {
                prev[curr_dst] = node;
                dist[curr_dst] = alt;
                queue.push(node_cost::init(curr_dst, alt));
            }
        }
    }

    return dijkstra_result::init(prev, dist);
}

sp_result shortest_path(graph *g, int src, int dst) {

    auto result = dijkstra(g, src, dst);

    int cost = result.dist[dst];

    auto path = std::vector<int>();
    path.reserve(g->nodes);

    while (src != dst) {
        path.push_back(dst);
        dst = result.prev[dst];
    }
    path.push_back(src);
    int begin = 0;
    int end = path.size() - 1;
    while (begin < end) {
        int tmp = path[begin];
        path[begin] = path[end];
        path[end] = tmp;
        begin++;
        end--;
    }

    sp_result output;
    output.path = path;
    output.cost = cost;

    return output;
}
