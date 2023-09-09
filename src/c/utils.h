#ifndef __UTILS_H
#define __UTILS_H

#include <stdio.h>

void print_int_vect(int* ptr, int count) {
    while(count--) {
        printf("%d ", *ptr++);
    }
}


#endif
