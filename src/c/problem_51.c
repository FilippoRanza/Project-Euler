
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "primes.h"
#include "utils.h"

void count_digits(int num, int *ptr) {
    while (num) {
        ptr[num % 10]++;
        num /= 10;
    }
}

typedef struct {
    int curr;
    int digits;
} digit_iter;

int digit_count(int num) {
    int output = 0;
    while (num) {
        num /= 10;
        output++;
    }
    return output;
}

digit_iter di_init(int num) {
    int digits = digit_count(num) - 1;
    digit_iter output = {.curr = num, .digits = digits};
    return output;
}

bool di_has_next(digit_iter *di) { return di->curr > 0; }

int pow_10(int exp) {
    int output = 1;
    while (exp--)
        output *= 10;
    return output;
}

int di_get_next(digit_iter *di) {
    int div = pow_10(di->digits);
    int output = di->curr / div;
    di->curr = di->curr % div;
    di->digits--;
    return output;
}

int make_step(int num, int dig) {
    int output = 0;
    digit_iter di = di_init(num);
    while (di_has_next(&di)) {
        int n = di_get_next(&di);
        output *= 10;
        if (n == dig)
            output += 1;
        num /= 10;
    }
    return output;
}

int check_chain(int num, int step, bool *primes, int count, int remain) {
    int output = 0;
    while (remain--) {
        if (num >= count)
            break;
        if (primes[num])
            output++;
        num += step;
    }

    return output;
}

int search_longest_chain(int num, bool *primes, int len) {
    int count[10] = {0};
    count_digits(num, count);

    int max_len = 0;
    for (int i = 0; i < 10; i++) {
        if (count[i] >= 1) {
            int step = make_step(num, i);
            int curr = check_chain(num, step, primes, len, 10 - i);
            if (curr > max_len)
                max_len = curr;
        }
    }
    return max_len;
}

int main(void) {
    int upper = 1000000;

    bool *primes = malloc(upper * sizeof(bool));
    prime_sieve(primes, upper);

    int curr_init = -1;
    int curr_len = 0;

    for (int i = 3; i < upper; i += 2) {
        if (primes[i]) {
            int len = search_longest_chain(i, primes, upper);
            if (len > curr_len) {
                curr_init = i;
                curr_len = len;
            }
        }
    }

    printf("Begin: %d\n", curr_init);
    printf("Length: %d\n", curr_len);

    free(primes);

    return 0;
}
