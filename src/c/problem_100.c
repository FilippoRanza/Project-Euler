#include <stdio.h>

typedef struct {
    long x;
    long y;
} Couple;

typedef struct {
    Couple base;
    Couple curr;
    long n;
} ConvIter;

ConvIter conv_iter_new(long x, long y, long n) {
    Couple base = {.x = x, .y = y};
    ConvIter output = {0};
    output.base = base;
    output.n = n;
    return output;
}

void conv_iter_step(ConvIter *iter) {
    long x =
        iter->curr.x * iter->base.x + iter->n * iter->curr.y * iter->base.y;
    long y = iter->curr.x * iter->base.y + iter->curr.y * iter->base.x;
    iter->curr.x = x;
    iter->curr.y = y;
}

Couple conv_iter_next(ConvIter *iter) {

    if (iter->curr.x == 0) {
        iter->curr = iter->base;
    } else {
        conv_iter_step(iter);
    }

    return iter->curr;
}

Couple compute_blue_red_number(Couple c) {

    long r = c.y;
    long n = c.x;
    long c1 = 2 * r + 1;
    long b = (c1 + n) / 2;
    Couple output = {.x = b, .y = r};
    return output;
}

int main(int argc, char **argv) {

    ConvIter iter = conv_iter_new(3, 1, 8);

    for (int i = 0; i < 100; i++) {
        Couple conv = conv_iter_next(&iter);
        Couple discs = compute_blue_red_number(conv);
        if (discs.x + discs.y >= 1e12) {
            printf("Blue disks: %ld\n", discs.x);
            break;
        }
    }

    return 0;
}
