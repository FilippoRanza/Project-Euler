#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

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

bool same_digits(int a, int b) {
    int digits[10] = {0};

    while (a) {
        int i = a % 10;
        a /= 10;
        digits[i]++;
    }

    while (b) {
        int i = b % 10;
        b /= 10;
        digits[i]--;
    }

    for (int i = 0; i < 10; i++)
        if (digits[i])
            return false;

    return true;
}

int all_same_digits(int a, int b, int c) {
    return same_digits(a, b) && same_digits(a, c);
}

int main() {

    int count = 10000;
    bool *primes = malloc(sizeof(bool) * count);
    int total = prime_sieve(primes, count);

    for (int i = 1000; i < count; i++) {
        for (int j = 1; (2 * j) + i < count; j++) {
            int a = i;
            int b = a + j;
            int c = b + j;
            if(primes[a] && primes[b] && primes[c]) {
                if(all_same_digits(a, b, c))
                    printf("%d%d%d\n", a, b, c);
            }
        }
    }

    return 0;
}
