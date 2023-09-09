#ifndef __PRIMES_H
#define __PRIMES_H

#include <stdbool.h>

bool is_prime(int n) {
    if (n < 2)
        return false;
    if (n == 2)
        return true;
    if (n % 2 == 0)
        return false;

    int curr = 3;
    int stop = n / curr;
    while (curr < stop) {
        if (n % curr == 0)
            return false;
        curr += 2;
        stop = n / curr;
    }

    return true;
}

int prime_sieve(bool *buff, int len) {
    for (int i = 0; i < len; i++) {
        buff[i] = true;
    }

    buff[0] = buff[1] = false;
    int count = 0;
    for (int i = 2; i < len; i++) {
        if (!buff[i])
            continue;

        for (int j = i + i; j < len; j += i) {
            buff[j] = false;
        }
        count++;
    }
    return count;
}

#endif
