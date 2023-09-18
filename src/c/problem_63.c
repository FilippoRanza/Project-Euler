#include <gmp.h>
#include <stdio.h>

#define __USE_BIG_NUMBER
#include "digit_utils.h"

typedef unsigned long ulong;

void powi(mpz_t result, ulong base, int exp) {
    mpz_set_d(result, 1);

    while (exp--) {
        mpz_mul_ui(result, result, base);
    }
}

int main() {

    int count = 0;
    mpz_t big_n;
    mpz_init(big_n);

    for (ulong base = 1; base < 1000; base++) {
        for (int exp = 1; exp < 1000; exp++) {
            powi(big_n, base, exp);
            int len = big_number_len(big_n);
            if (len == exp)
                count++;
            if(len > exp)
                break;
        }
    }

    printf("%d\n", count);

    mpz_clear(big_n);
    return 0;
}
