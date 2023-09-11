#include "primes.h"
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#define UP 0
#define LEFT 1
#define RIGHT 2
#define DOWN 3

int is_diagonal(int i, int j) {
    if (i == j)
        return 1;

    if (i == -j)
        return 1;
    return 0;
}

int main(void) {

    int total_primes = 1000000000;
    bool *primes = calloc(sizeof(bool), total_primes);
    prime_sieve(primes, total_primes);

    int i, j;
    i = j = 0;

    int count = 49;
    int len = 1;
    int dir = RIGHT;
    int rem = len;
    int tot_diag = 0;
    int tot_primes = 0;
    int run = 1;
    for (int curr = 1; run; curr++) {
        if (is_diagonal(i, j)) {
            tot_diag++;
            if (primes[curr])
                tot_primes++;

            double ratio = ((double)tot_primes) / ((double)tot_diag);
            if (ratio <= 0.1 && curr > 10)
                run = 0;
        }
        rem--;
        switch (dir) {
        case UP:
            j--;
            if (rem == 0) {
                dir = LEFT;
                rem = ++len;
            }
            break;
        case LEFT:
            i--;
            if (rem == 0) {
                dir = DOWN;
                rem = len;
            }
            break;
        case RIGHT:
            i++;
            if (rem == 0) {
                dir = UP;
                rem = len;
            }
            break;
        case DOWN:
            j++;
            if (rem == 0) {
                dir = RIGHT;
                rem = ++len;
            }
            break;
        }
    }

    printf("Side len: %d\n", len);

    return 0;
}
