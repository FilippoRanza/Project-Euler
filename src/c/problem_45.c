#include <stdio.h>
#include <math.h>

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

int is_triangular(int n) {
    solution sol = solve_eq(0.5, 0.5, -n);
    return is_natural(sol.sol_a) || is_natural(sol.sol_b);
}

int hexagonal(int n) {
    return n * (2 * n - 1);
}

int main() {

    for(int i = 283; i < 100000000; i++) {
        int n = hexagonal(i);
        if(n < 0)
            printf("Warning\n");
        if(is_pentagonal(n) && is_triangular(n)) {
            printf("%d\n", n);
        }
    }


    return 0;
}



