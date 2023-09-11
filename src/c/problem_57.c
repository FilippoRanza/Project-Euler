#include <stdio.h>

#include "digit_utils.h"
#include <gmp.h>

void get_expansion(mpq_t exp, mpq_t a) {
    mpq_t one;
    mpq_init(one);
    mpq_set_ui(one, 1, 1);

    mpq_t rht;
    mpq_init(rht);

    mpq_div(rht, one, a);
    mpq_add(exp, one, rht);

    mpq_clear(one);
    mpq_clear(rht);
}

void get_next(mpq_t a) {
    mpq_t one;
    mpq_init(one);
    mpq_set_ui(one, 1, 1);

    mpq_t rht;
    mpq_init(rht);

    mpq_div(rht, one, a);
    mpq_set_ui(one, 2, 1);
    mpq_add(a, one, rht);


    mpq_clear(one);
    mpq_clear(rht);
}

unsigned long comp_digit_len(mpq_t exp) {
    mpz_t tmp;
    mpz_init(tmp);
    mpq_get_num(tmp, exp);
    unsigned long num_len = big_number_len(tmp);
    mpq_get_den(tmp, exp);
    unsigned long den_len = big_number_len(tmp);
    mpz_clear(tmp);
    return num_len > den_len;
}

int main(void) {

    mpq_t a;
    mpq_init(a);
    mpq_set_ui(a, 5, 2);
    mpq_t exp;
    mpq_init(exp);

    int count = 0;
    for (unsigned long i = 1; i < 1000; i++) {
        get_expansion(exp, a);
        if(comp_digit_len(exp))
            count++;
        get_next(a);
    }

    printf("Total: %d\n", count);

    mpq_clear(a);
    mpq_clear(exp);
    return 0;
}
