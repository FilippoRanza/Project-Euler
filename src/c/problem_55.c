#include <stdbool.h>
#include <stdio.h>

unsigned long invert_digits(unsigned long n) {
    unsigned long o = 0;
    while (n) {
        o *= 10;
        o += n % 10;
        n /= 10;
    }
    return o;
}

bool is_palindrome(unsigned long n) {
    unsigned long in = invert_digits(n);
    return n == in;
}

#define MAX_ITER 50
bool is_lychrel(unsigned long n) {

    for (int i = 0; i < MAX_ITER; i++) {
        unsigned long tmp = invert_digits(n);
        n += tmp;
        if (is_palindrome(n))
            return true;
    }

    return false;
}

int main(void) {

    int count = 0;
    for (unsigned long i = 1; i < 10000; i++) {
        if (!is_lychrel(i))
            count++;
    }

    printf("Lychrel below 10000: %d\n", count);

    return 0;
}
