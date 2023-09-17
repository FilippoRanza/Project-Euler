#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

int triangular(int n) { return (n * (n + 1)) / 2; }

int square(int n) { return n * n; }

int pentagonal(int n) { return (n * (3 * n - 1)) / 2; }

int hexagonal(int n) { return n * (2 * n - 1); }

int heptagonal(int n) { return (n * (5 * n - 3)) / 2; }

int octagonal(int n) { return n * (3 * n - 2); }

typedef struct {
    bool set;
    bool used;
} info;

info *make_buffer(int upper, int (*f)(int)) {
    info *buff = calloc(sizeof(info), upper);
    if (buff == NULL)
        exit(1);

    for (int i = 0; i < upper; i++) {
        int j = f(i);
        if (j >= upper)
            break;
        buff[j].set = true;
    }

    return buff;
}

int **make_matrix(int size) {

    int **output = malloc(sizeof(int *) * size);
    for (int i = 0; i < size; i++) {
        output[i] = malloc(sizeof(int) * size);
    }

    return output;
}

typedef struct {
    int *buff;
    int len;
    int head;
    int tail;
} queue;

queue *queue_init(int len) {
    queue *q = malloc(sizeof(queue));
    if (q == NULL)
        return NULL;

    int *buff = malloc(sizeof(int) * len);
    if (buff == NULL) {
        free(q);
        return NULL;
    }

    q->buff = buff;
    q->len = len;
    q->head = 0;
    q->tail = 0;

    return q;
}

void queue_push(queue *q, int n) {
    q->buff[q->tail] = n;
    q->tail = (q->tail + 1) % q->len;
}

int queue_pop(queue *q) {
    int output = q->buff[q->head];
    q->head = (q->head + 1) % q->len;
    return output;
}

int queue_is_empty(queue *q) { return q->head == q->tail; }

int breadth_first_search(queue *q, int root, int **mat, int size, int *seen) {
    if (seen[root] != -1)
        return 0;

    queue_push(q, root);
    while (!queue_is_empty(q)) {
        int curr = queue_pop(q);
        for (int j = 0; j < size; j++) {
            if (mat[curr][j] && seen[j] == -1) {
                seen[j] = curr;
                queue_push(q, j);
            }
        }
    }

    return seen[root] != -1;
}

void reset_seen(int *seen, int len) {
    while (len--)
        *seen++ = -1;
}

void update_matrix(int **mat, info **all_buffs, int curr, int idx) {
    for (int j = 0; j < 6; j++) {
        if (all_buffs[j][curr].set) {
            mat[idx][j] = 1;
        }
    }
}

int check_matrix(int **counter) {
    for (int i = 0; i < 6; i++) {
        int tot = 0;
        for (int j = 0; j < 6; j++) {
            if (counter[j][i])
                tot++;
        }
        if (tot == 0)
            return 0;
    }

    return 1;
}

int get_loop_value(int *seen, int root, info **all_buffs, int **counter) {
    int idx = 0;
    int output = root;
    update_matrix(counter, all_buffs, root, idx);
    int len = 1;
    int curr = seen[root];
    while (curr != root) {
        idx++;
        if (idx < 6)
            update_matrix(counter, all_buffs, curr, idx);
        output += curr;
        len++;
        curr = seen[curr];
    }

    if (len == 6 && check_matrix(counter))
        return output;

    return -1;
}

void reset_matrix(int **mat, int size) {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            mat[i][j] = 0;
        }
    }
}

int main(void) {

    int upper = 10000;
    info *tri_buff = make_buffer(upper, triangular);
    info *sq_buff = make_buffer(upper, square);
    info *penta_buff = make_buffer(upper, pentagonal);
    info *hex_buff = make_buffer(upper, hexagonal);
    info *hep_buff = make_buffer(upper, heptagonal);
    info *oct_buff = make_buffer(upper, octagonal);

    info *all_buffs[] = {tri_buff, sq_buff,  penta_buff,
                         hex_buff, hep_buff, oct_buff};
    int buff_count = sizeof(all_buffs) / sizeof(info *);

    int **matrix = make_matrix(upper);

    int match = 0;
    for (int i = 0; i < buff_count; i++) {
        for (int j = 0; j < buff_count; j++) {
            if (i == j)
                continue;
            info *b1 = all_buffs[i];
            info *b2 = all_buffs[j];
            for (int k = 0; k < upper; k++) {
                if (b1[k].set) {
                    int rem = k % 100;
                    if (rem > 10) {
                        int begin = rem * 100;
                        int end = begin + 100;
                        for (int t = begin; t < end; t++) {
                            if (b2[t].set) {
                                matrix[k][t] = match++;
                            }
                        }
                    }
                }
            }
        }
    }

    queue *q = queue_init(upper);
    int *seen = malloc(sizeof(int) * upper);
    int **counter = make_matrix(buff_count);
    for (int i = 1000; i < upper; i++) {
        reset_seen(seen, upper);
        int last = breadth_first_search(q, i, matrix, upper, seen);
        if (last) {
            reset_matrix(counter, buff_count);
            int value = get_loop_value(seen, i, all_buffs, counter);
            if (value != -1)
                printf("%d\n", value);
        }
    }

    return 0;
}
