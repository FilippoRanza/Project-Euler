#include <stdbool.h>
#include <stdio.h>

int min(int a, int b) { return (a < b) ? a : b; }

bool mult_binomial(double n, double k, double upper) {
    double output = 1;
    double end = min(k, n - k);
    for (double i = 1; i <= end; i++) {
        double term = (n + 1 - i) / i;
        output *= term;
        if (output >= upper)
            return true;
    }
    return false;
}

int main(void) {
    int upper = 1000000;

    int total = 0;
    for(int n = 1; n <= 100; n++) {
        for(int k = 0; k <= n; k++) {
            if(mult_binomial(n, k, upper))
                total++;
        }
    }

    printf("%d\n", total);

    return 0;
}
