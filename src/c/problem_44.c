#include <stdio.h>
#include <stdlib.h>
#include <math.h>


int pentagonal(int n) {
    return (n * (3 * n  - 1)) / 2;
}

typedef struct {
    double sol_a;
    double sol_b;
} solution;

solution solve_eq(double a, double b, double c) {
    double delta = 4 * a * c;
    double sq = sqrt(b*b - delta);
    double sol_a = (-b + sq) / (2*a);
    double sol_b = (-b - sq) / (2*a);
    solution output = { .sol_a = sol_a, .sol_b = sol_b };
    return output;
}

int is_natural(double n) {
    return n > 0 && round(n) == n;
}

int is_pentagonal(int n) {
    solution sol = solve_eq(1.5, -0.5, -n);
    return is_natural(sol.sol_a) || is_natural(sol.sol_b);
}


int main() {


    int min = -1;
    for(int i = 1; i < 100000; i++) {
        int pi = pentagonal(i);
        for(int j = 1; j < i; j++) {
            int pj = pentagonal(j);
            if(is_pentagonal(pi + pj) && is_pentagonal(pi - pj)) {
                int diff = pi - pj;
                if(min == -1 || min > diff) {
                    min = diff;
                }
            }

        }
    }

    printf("%d\n", min);

     

    return 0;
}
