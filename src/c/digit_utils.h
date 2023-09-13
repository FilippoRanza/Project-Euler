#ifndef __DIGIT_UTILS_H
#define __DIGIT_UTILS_H

#include <gmp.h>

void digit_count(int number, int *ptr) {
    while (number) {
        ptr[number % 10]++;
        number /= 10;
    }
}

int number_len(int number) {
    int output = 0;
    while (number) {
        output++;
        number /= 10;
    }
    return output;
}

#ifdef __USE_BIG_NUMBER
unsigned long big_number_len(mpz_t n) {
    unsigned long output = 0;
    while (mpz_cmp_ui(n, 0) != 0) {
        output++;
        mpz_fdiv_q_ui(n, n, 10);
    }

    return output;
}
#endif

#endif
