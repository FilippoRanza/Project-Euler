#include <gmp.h>
#include <stdio.h>

unsigned long digit_sum(mpz_t n) {
    unsigned long output = 0;
    while (mpz_cmp_ui(n, 0) != 0) {
        output += mpz_fdiv_ui(n, 10);
        mpz_fdiv_q_ui(n, n, 10);
    }

    return output;
}

int main(void) {

    mpz_t big_n = {0};

    mpz_init(big_n);

    unsigned long max = 0;
    for (unsigned long a = 1; a < 100; a++) {
        for (unsigned long b = 1; b < 100; b++) {
            mpz_ui_pow_ui(big_n, a, b);
            unsigned long tmp = digit_sum(big_n);
            if(tmp > max) 
                max = tmp;
        }

    }

    mpz_clear(big_n);

    printf("Max digit sum: %lu\n", max);

    return 0;
}
