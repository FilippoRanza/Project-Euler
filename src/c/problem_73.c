#include <stdio.h>

int gcd(int a, int b) {
    while (b != 0) {
        int t = b;
        b = a % b;
        a = t;
    }
    return a;
}

int main(int argc, char** argv) {

    int total = 0;
    int count = 12000;
    double upper = 1.0 / 2.0;
    double lower = 1.0 / 3.0;
    for (int d = 2; d <= count; d++) {
        for (int n = 1; n < d; n++) {
            if (gcd(d, n) == 1) {
                double q = ((double)n) / ((double)d);
                if (q > lower && q < upper)
                    total++;
            }
        }
    }

    printf("Total fractions: %d\n", total);

    return 0;
}
