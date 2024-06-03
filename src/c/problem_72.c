#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

int gcd(int a, int b) {
    while (b != 0) {
        int t = b;
        b = a % b;
        a = t;
    }
    return a;
}

typedef struct {
    int *buff;
    int cap;
    int len;
} Vect;

Vect vect_init(int cap) {
    Vect output = {0};
    int *buff = malloc(sizeof(int) * cap);
    if (buff == NULL)
        abort();
    output.buff = buff;
    output.cap = cap;
    output.len = 0;
    return output;
}

void vect_free(Vect *vect) { free(vect->buff); }

void vect_append(Vect *vect, int value) {

    vect->buff[vect->len++] = value;
    if (vect->len == vect->cap) {
        int new_cap = vect->cap * 2;
        int *tmp = realloc(vect->buff, new_cap * sizeof(int));
        if (tmp == NULL)
            abort();
        vect->cap = new_cap;
        vect->buff = tmp;
    }
}

int factorize(int n, Vect *primes) {
    int factors = 0;
    primes->len = 0;
    for (int i = 2; i <= n; i++) {
        bool set = false;
        while (n % i == 0) {
            set = true;
            n /= i;
        }
        if (set) {
            factors++;
            vect_append(primes, i);
        }
    }
    return factors;
}

bool is_coprime(int n, Vect *primes) {
    for (int i = 0; i < primes->len; i++) {
        if (n % primes->buff[i] == 0)
            return false;
    }
    return true;
}

bool is_prime(Vect *primes, int n) {
    return primes->len == 1 && primes->buff[0] == n;
}

int main(int argc, char **argv) {

    long total = 0;
    int count = 1000000;
    Vect primes = vect_init(10);
    for (int d = 2; d <= count; d++) {
        printf("\rDenominator: %d", d);
        int factors = factorize(d, &primes);
        if (is_prime(&primes, d)) {
            total += d - 1;
        } else {
            int inc = d % 2 ? 1 : 2;
            int stop = d - primes.buff[0] + 1;
            int start = primes.buff[0] + 1;
            for (int n = start; n < stop; n = n + inc) {
                if (is_coprime(n, &primes))
                    total++;
            }
            total += 2 * (primes.buff[0] - 1);
        }
    }

    printf("\rTotal fractions: %ld\n", total);

    return 0;
}
