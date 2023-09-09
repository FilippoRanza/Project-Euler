#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "primes.h"

int main() {

    int count = 1000000;
    bool *primes = malloc(count * sizeof(bool));

    int total = prime_sieve(primes, count);

    int value = 0;
    int seq_len = 0;

    for (int i = 2; i < count; i++) {
        if (!primes[i])
            continue;

        int curr_tot = i;
        int best_tot = i;
        int curr_len = 0;
        int best_len = 0;
        for (int j = i + 1; j < count; j++) {
            if (!primes[j])
                continue;
            curr_tot += j;
            if(curr_tot >= count)
                break;
            curr_len++;
            if(primes[curr_tot]) {
                best_len = curr_len;
                best_tot = curr_tot;
            }
        }

        if(best_len > seq_len) {
            seq_len = best_len;
            value = best_tot;
        }


    }

    printf("%d\n", value);
    return 0;
}
