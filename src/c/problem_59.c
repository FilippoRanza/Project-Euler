#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char *read_file(FILE *file) {
    int curr_size = 1024;
    char *buff = realloc(NULL, curr_size);
    int missing = curr_size;
    char *tmp = buff;
    char c;
    while ((c = fgetc(file)) != EOF) {
        if (missing == 0) {
            missing = curr_size;
            buff = realloc(buff, 2 * curr_size);
            tmp = buff + curr_size;
            curr_size *= 2;
        }
        *tmp++ = c;
        missing--;
    }
    *tmp++ = '\0';

    return buff;
}

typedef struct {
    int *buff;
    int len;
} cipher;

cipher parse_input(char *data) {
    int curr_size = 2 * strlen(data) / 3;
    int *buff = malloc(sizeof(int) * curr_size);
    int *tmp = buff;
    int missing = curr_size;
    for (char *curr = strtok(data, ","); curr != NULL;
         curr = strtok(NULL, ",")) {
        if (missing == 0) {
            missing = curr_size;
            buff = realloc(buff, 2 * curr_size);
            tmp = buff + curr_size;
            curr_size *= 2;
        }
        int b = atoi(curr);
        *tmp++ = b;
    }
    int len = tmp - buff;
    cipher output = {.buff = buff, .len = len};
    return output;
}

int *create_empty(cipher *c) { return malloc(sizeof(int) * c->len); }

void decrypt(cipher *c_data, int *buff, int k1, int k2, int k3) {
    int curr = 0;
    int key[] = {k1, k2, k3};
    int *c_buff = c_data->buff;
    for (int i = 0; i < c_data->len; i++) {
        *buff++ = *c_buff++ ^ key[curr];
        curr = (curr + 1) % 3;
    }
}

int main(void) {
    char *data = read_file(stdin);
    cipher c_data = parse_input(data);
    int *buff = create_empty(&c_data);

    int count_1[256] = {0};
    int count_2[256] = {0};
    int count_3[256] = {0};
    int curr = 0;
    int* counters[] = {count_1, count_2, count_3};
    for (int i = 0; i < c_data.len; i++) {
        int c = c_data.buff[i];
        counters[curr][c]++;
        curr = (curr + 1) % 3;
    }

    int max_indx[3] = {0};
    int max_count[3] = {0};
    
    for(int i = 0; i < 256; i++) {
        for(int j = 0; j < 3; j++) {
            if(counters[j][i] > max_count[j]) {
                max_count[j] = counters[j][i];
                max_indx[j] = i;
            }
        }
    }

    int k1 = max_indx[0] ^ ' ';
    int k2 = max_indx[1] ^ ' ';
    int k3 = max_indx[2] ^ ' ';
    decrypt(&c_data, buff, k1, k2, k3);

    int result = 0;
    for(int i = 0; i < c_data.len; i++) {
        printf("%c ", buff[i]);
        result += buff[i];
    }


    printf("Result: %d\n", result);

    free(data);
    free(c_data.buff);
    free(buff);

    return 0;
}
