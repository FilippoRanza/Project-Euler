#include <stdio.h>

#include "digit_utils.h"
#include "hashmap.h"

#define DIGITS 10

typedef struct {
    int digits[DIGITS];
    unsigned long first;
    int count;
} cube;

int cube_compare(const void *ptr_1, const void *ptr_2, void *udata) {
    const cube *c1 = ptr_1;
    const cube *c2 = ptr_2;

    for (int i = 0; i < DIGITS; i++) {
        int diff = c1->digits[i] - c2->digits[i];
        if (diff)
            return diff;
    }

    return 0;
}

uint64_t cube_hash(const void *ptr, uint64_t seed0, uint64_t seed1) {
    const cube *c = ptr;
    return hashmap_sip(c->digits, sizeof(int) * DIGITS, seed0, seed1);
}

cube cube_init(unsigned long n) {
    cube c = {0};
    c.first = n;
    while (n) {
        c.digits[n % 10]++;
        n /= 10;
    }
    c.count = 1;
    return c;
}

int main(void) {

    struct hashmap *map = hashmap_new(sizeof(cube), 10000, 0, 0, cube_hash,
                                      cube_compare, NULL, NULL);

    for (unsigned long n = 3300; n < 13300; n++) {
        cube c = cube_init(n * n * n);
        const cube *ptr = hashmap_get(map, &c);
        if (ptr != NULL) {
            c.count += ptr->count;
            c.first = ptr->first;
        }
        if (c.count == 5)
            printf("%lu\n", c.first);
        hashmap_set(map, &c);
    }

    return 0;
}
