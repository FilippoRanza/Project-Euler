#include <stdint.h>
#include <stdio.h>

uint64_t mod_power(uint64_t base, uint64_t exp, uint64_t mod) {
    uint64_t output = 1;
    while (exp--) {
        output = (output * base) % mod;
    }

    return output;
}

int main() {

    uint64_t mod = 10000000000;
    uint64_t total = 0;

    for (uint64_t i = 1; i <= 1000; i++) {
        uint64_t tmp = mod_power(i, i, mod);
        total = (total + tmp) % mod;
    }

    printf("%lu\n", total);

    return 0;
}
