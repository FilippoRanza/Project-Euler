#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>

typedef struct {
    int dst;
    int cost;
} arc;

typedef struct {
    arc *buff;
    int len;
    int cap;
} adj_list;

typedef struct {
    adj_list *lists;
    int nodes;
} graph;

void *mmalloc(size_t size) {
    void *output = malloc(size);
    if (output == NULL)
        exit(1);

    return output;
}

int **matrix_init(int size) {

    int **output = mmalloc(sizeof(int *) * size);
    for (int i = 0; i < size; i++)
        output[i] = mmalloc(sizeof(int) * size);

    return output;
}

void matrix_free(int **mat, int size) {
    for (int i = 0; i < size; i++)
        free(mat[i]);
    free(mat);
}

int read_line(FILE *input, char *buff) {
    char c;
    while ((c = fgetc(input)) && c != EOF && c != '\n')
        *buff++ = c;
    *buff = '\0';

    return c != EOF;
}

int **load_matrix(FILE *input, int size) {
    int **mat = matrix_init(size);
    char *buff = mmalloc(4096);
    int row = 0;
    while (read_line(input, buff)) {
        char *tmp = buff;
        char *tok;
        int col = 0;
        while ((tok = strtok(tmp, ",")) != NULL) {
            tmp = NULL;
            int curr = atoi(tok);
            mat[row][col++] = curr;
        }
        row++;
    }

    free(buff);

    return mat;
}

void adj_list_add_arc(adj_list *list, arc a) {
    if (list->len == list->cap)
        exit(2);

    list->buff[list->len] = a;
    list->len++;
}

void graph_add_arc(graph *g, int src, int dst, int cost) {
    arc tmp = {.dst = dst, .cost = cost};
    adj_list_add_arc(g->lists + src, tmp);
}

graph *graph_new(int nodes, int degree) {
    graph *g = mmalloc(sizeof(graph));
    adj_list *lists = mmalloc(sizeof(adj_list) * nodes);
    for (int i = 0; i < nodes; i++) {
        lists[i].buff = mmalloc(sizeof(arc) * degree);
        lists[i].len = 0;
        lists[i].cap = degree;
    }

    g->lists = lists;
    g->nodes = nodes;

    return g;
}

int get_node(int i, int j, int size) { return (i * size) + j; }

void graph_add_arcs(int **mat, int size, graph *g) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            int src = get_node(i, j, size);
            if (i + 1 < size) {
                int dst = get_node(i + 1, j, size);
                int cost = mat[i][j] + mat[i + 1][j];
                graph_add_arc(g, src, dst, cost);
            }
            if (j + 1 < size) {
                int dst = get_node(i, j + 1, size);
                int cost = mat[i][j] + mat[i][j + 1];
                graph_add_arc(g, src, dst, cost);
            }
        }
    }
}

void graph_print(graph *g) {
    for (int i = 0; i < g->nodes; i++) {
        adj_list list = g->lists[i];
        for (int j = 0; j < list.len; j++) {
            printf("%d -> %d cost: %d\n", i, list.buff[j].dst,
                   list.buff[j].cost);
        }
    }
}

void graph_free(graph *g) {
    for (int i = 0; i < g->nodes; i++)
        free(g->lists[i].buff);

    free(g->lists);
    free(g);
}

typedef struct {
    int** dist;
    int** pred;
} all_pairs;

all_pairs all_pairs_shortest_path(graph *g) {
    int **dist_mat = matrix_init(g->nodes);
    int **pred_mat = matrix_init(g->nodes);
    for (int i = 0; i < g->nodes; i++) {
        for (int j = 0; j < g->nodes; j++) {
            dist_mat[i][j] = INT_MAX >> 4;
        }
    }

    for (int i = 0; i < g->nodes; i++) {
        adj_list lst = g->lists[i];
        for (int j = 0; j < lst.len; j++) {
            dist_mat[i][lst.buff[j].dst] = lst.buff[j].cost;
            pred_mat[i][lst.buff[j].dst] = i;
        }
    }

    for (int h = 0; h < g->nodes; h++) {
        for (int i = 0; i < g->nodes; i++) {
            for (int j = 0; j < g->nodes; j++) {
                int tmp = dist_mat[i][h] + dist_mat[h][j];
                if (dist_mat[i][j] > tmp){
                    dist_mat[i][j] = tmp;
                    pred_mat[i][j] = pred_mat[h][j];
                }
            }
        }
    }

    all_pairs output = {.dist = dist_mat, .pred = pred_mat};
    return output;
}

int main() {

    int size = 80;
    int **mat = load_matrix(stdin, size);

    int nodes = size * size;
    graph *g = graph_new(nodes, 2);
    graph_add_arcs(mat, size, g);
    all_pairs result = all_pairs_shortest_path(g);

    int dst = get_node(size - 1, size - 1, size);
    double dist = result.dist[0][dst];
    double head_tail = mat[0][0] + mat[size - 1][size - 1];
    double cost = (dist + head_tail) / 2;
    printf("%f\n", cost);


    matrix_free(result.dist, nodes);
    matrix_free(result.pred, nodes);
    graph_free(g);
    matrix_free(mat, size);
    return 0;
}
