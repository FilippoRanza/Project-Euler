#include <math.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct {
    int *buff;
    int cap;
    int len;
} factors;

factors *factors_init(int cap) {

    factors *output = malloc(sizeof(factors));
    if (output == NULL)
        return NULL;

    int *buff = malloc(cap * sizeof(int));
    if (buff == NULL) {
        free(output);
        return NULL;
    }

    output->buff = buff;
    output->cap = cap;
    output->len = 0;

    return output;
}

void factors_clear(factors *ptr) { ptr->len = 0; }

bool factor_all_diff(factors *ptr_a, factors *ptr_b) {

    int *buff_a = ptr_a->buff;
    int len_a = ptr_a->len;

    int *buff_b = ptr_b->buff;
    int len_b = ptr_b->len;

    for (int i = 0; i < len_a; i++) {
        for (int j = 0; j < len_b; j++) {
            if (buff_a[i] == buff_b[j])
                return false;
        }
    }

    return true;
}

void factor_push(factors *ptr, int n) {
    ptr->buff[ptr->len] = n;
    ptr->len++;
}

void factorize(factors *ptr, int n) {
    int i = 2;
    while (n > 1) {
        int curr = 1;
        while (n % i == 0) {
            n /= i;
            curr *= i;
        }
        if (curr > 1)
            factor_push(ptr, curr);
        i++;
    }
}

factors **factors_vect_init(int count, int cap) {
    factors **output = malloc(count * sizeof(factors *));
    if (output == NULL)
        return NULL;

    factors **tmp = output;
    while (cap--) {
        factors *f = factors_init(cap);
        if (f == NULL)
            return NULL;

        *tmp++ = f;
    }

    return output;
}

bool all_diff_factors(factors **all_fact, int count) {
    for (int i = 0; i < count; i++) {
        if (all_fact[i]->len != count)
            return false;
    }

    for (int i = 0; i < count; i++) {
        for (int j = i + 1; j < count; j++) {
            if (!factor_all_diff(all_fact[i], all_fact[j]))
                return false;
        }
    }

    return true;
}

int main() {

    int count = 4;
    factors **all_fact = factors_vect_init(count, 100);
    for (int i = 0; i < count; i++) {
        factors_clear(all_fact[i]);
    }

    int idx = 0;
    for (int i = 1000; i < 1000000; i++) {
        factors_clear(all_fact[idx]);
        factorize(all_fact[idx], i);
        if (all_diff_factors(all_fact, count)) {
            for (int j = 0; j < count; j++)
                printf("%d ", i - j);
            putchar('\n');
            break;
        }
        idx = (idx + 1) % count;
    }

    return 0;
}
