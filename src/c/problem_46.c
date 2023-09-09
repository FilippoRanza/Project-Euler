#include <math.h>
#include <stdio.h>

int gcd(int a, int b) {
    while (b) {
        int tmp = b;
        b = a % b;
        a = tmp;
    }
    return a;
}

int is_double_square(int n) {
    if (n % 2)
        return 0;
    n /= 2;
    double sq = sqrt(n);
    return round(sq) == sq;
}

int is_prime(int n) {
    if (n < 2)
        return 0;
    if (n == 2)
        return 1;
    if (n % 2 == 0)
        return 0;

    int curr = 3;
    int stop = n / curr;
    while (curr < stop) {
        if (n % curr == 0)
            return 0;
        curr += 2;
        stop = n / curr;
    }

    return 1;
}

int main() {

    for (int i = 9; i < 10000; i += 2) {
        int goldbach = 0;
        if (!is_prime(i)) {
            int n = 1;
            int sq = 2 * n * n;
            while (sq < i) {
                int rem = i - sq;
                if (is_prime(rem)) {
                    goldbach = 1;
                    break;
                }

                n++;
                sq = 2 * n * n;
            }

            if (!goldbach) {
                printf("Violation: %d\n", i);
                break;
            }
        }
    }

    return 0;
}
