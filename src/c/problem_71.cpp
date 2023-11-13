#include <iostream>

ulong gcd(ulong a, ulong b) {
    if (b < a) {
        auto tmp = a;
        a = b;
        b = tmp;
    }

    while (b) {
        auto tmp = a % b;
        a = b;
        b = tmp;
    }

    return a;
}

int main() {

    double best = 2.0 / 5;
    double upper = 3.0 / 7;
    ulong best_num = 2;
    ulong best_den = 5;


    ulong limit = 1000000;
    // ulong limit = 8;

    for (ulong den = 2; den <= limit; den++) {
        ulong num = (ulong)den * upper;
        while (gcd(num, den) != 1) {
            num--;
        }
        double curr = ((double) num) / den;
        if(curr < upper && curr > best)  {
            best = curr;
            best_num = num;
            best_den = den;
        }
    }

    std::cout << "Best: " << best << std::endl;
    std::cout << best_num << "/" << best_den << std::endl;

    return 0;
}
