#include <stdbool.h>
#include <stdio.h>

#include "digit_utils.h"
#include "utils.h"

bool equal_vect(int *a, int *b, int len) {
    while (len--) {
        if (*a++ != *b++)
            return false;
    }
    return true;
}

bool is_permutated_multiple(int n) {
    int base[10] = {0};
    digit_count(n, base);
    for (int i = 2; i <= 6; i++) {
        int other[10] = {0};
        digit_count(n * i, other);
        if (!equal_vect(base, other, 10))
            return false;
    }

    return true;
}

int main(void) {

    for (int i = 1; i < 1000000000; i *= 10) {
        for (int j = i; j < 10 * i; j++) {
            if (is_permutated_multiple(j)){
                printf("%d\n", j);
                break;
            }
        }
    }

    return 0;
}
