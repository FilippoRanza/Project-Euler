#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "digit_utils.h"
#include "primes.h"

typedef struct {
    bool prime;
    bool seen;
} seen_prime;

int concat_number(int a, int b) {
    int len = number_len(b);
    while (len--)
        a *= 10;
    return a + b;
}

int main(void) {

    int limit = 20000;
    int upper = limit * limit;
    bool *primes = malloc(sizeof(bool) * upper);
    int count = prime_sieve(primes, upper);

    seen_prime *seen_primes = malloc(sizeof(seen_prime) * upper);
    for (int i = 0; i < upper; i++) {
        seen_primes[i].prime = primes[i];
        seen_primes[i].seen = false;
    }

    for (int curr = 10; curr < limit; curr *= 10) {
        for (int i = 0; i < upper; i++) {
            if (i > curr && primes[i]) {
                int rem = i % curr;
                if (primes[rem]) {
                    seen_primes[rem].seen = true;
                }
            }
        }
    }

    bool *new_primes = malloc(sizeof(bool) * limit);
    for (int i = 0; i < limit; i++) {
        bool cond = seen_primes[i].prime && seen_primes[i].seen;
        new_primes[i] = cond;
    }

    bool **matrix = calloc(sizeof(bool *), limit);
    for (int i = 0; i < limit; i++) {
        matrix[i] = calloc(sizeof(bool), limit);
    }

    for (int i = 0; i < limit; i++) {
        if (!new_primes[i])
            continue;
        for (int j = i + 1; j < limit; j++) {
            if (!new_primes[j])
                continue;
            int a = concat_number(i, j);
            int b = concat_number(j, i);
            if (primes[a] && primes[b]) {
                matrix[i][j] = true;
                matrix[j][i] = true;
            }
        }
    }

    for (int i = 0; i < limit; i++) {
        if (!new_primes[i])
            continue;
        for (int j = 0; j < limit; j++) {
            if (!new_primes[j])
                continue;

            if (!(matrix[i][j] && matrix[j][i]))
                continue;

            for (int k = 0; k < limit; k++) {
                if (!new_primes[k])
                    continue;
                if (!(matrix[i][k] && matrix[k][i] && matrix[j][k] &&
                      matrix[k][j]))
                    continue;
                for (int t = 0; t < limit; t++) {
                    if (!new_primes[t])
                        continue;
                    if (!(matrix[i][t] && matrix[t][i] && matrix[j][t] &&
                          matrix[t][j] && matrix[k][t] && matrix[t][k]))
                        continue;

                    for (int l = 0; l < limit; l++) {
                        if (!new_primes[l])
                            continue;
                        if (!(matrix[i][l] && matrix[l][i] && matrix[j][l] &&
                              matrix[l][j] && matrix[k][l] && matrix[l][k] &&
                              matrix[l][t] && matrix[t][l]))
                            continue;

                        printf("%d %d %d %d %d\n", i, j, k, t, l);
                        printf("%d\n", i + j + k + t + l);
                        goto DONE;
                    }
                }
            }
        }
    }
DONE:

    return 0;
}
