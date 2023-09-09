#ifndef __DIGIT_UTILS_H
#define __DIGIT_UTILS_H

void digit_count(int number, int *ptr) {
    while (number) {
        ptr[number % 10]++;
        number /= 10;
    }
}

int number_len(int number) {
    int output = 0;
    while (number) {
        output++;
        number /= 10;
    }
    return output;
}

#endif
