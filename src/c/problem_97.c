#include <stdio.h>

int main(int argc, char** argv) {

    long base = 1;
    for (int i = 0; i < 10; i++)
        base *= 10;

    long curr = 28433;
    for (int i = 0; i < 7830457; i++) {
        curr *= 2;
        curr = curr % base;
    }
    curr += 1;
    curr %= base;
    printf("%ld\n", curr);

    return 0;
}
