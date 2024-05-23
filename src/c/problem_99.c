#include <math.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct {
    int base;
    int exp;
} Number;

char *find_char(char *str, char c) {
    while (*str) {
        if (*str == c)
            return str;
        str++;
    }

    return NULL;
}

Number parse_line(char *line) {
    Number output = {0};
    char *exp = find_char(line, ',');
    *exp++ = '\0';

    output.base = atoi(line);
    output.exp = atoi(exp);

    return output;
}

double get_value(const Number *restrict const n) {
    double tmp = n->exp * log(n->base);
    return tmp;
}

int main(int argc, char **argv) {

    char line[1024] = {0};
    double largest_value = 0;
    int best_line = 0;
    int curr_line = 1;
    while (fgets(line, 1024, stdin)) {
        Number current = parse_line(line);
        double value = get_value(&current);
        if (value > largest_value) {
            largest_value = value;
            best_line = curr_line;
        }
        curr_line++;
    }
    printf("Largest %d\n", best_line);

    return 0;
}
