#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "utils.h"

#define VALUES 15
#define SUITS 4
#define HAND 5

typedef struct {
    int value;
    int suit;
} card;

void count_values(card *hand, int *vect) {
    for (int i = 0; i < HAND; i++) {
        vect[hand->value]++;
        hand++;
    }
}

void count_suits(card *hand, int *vect) {
    for (int i = 0; i < HAND; i++) {
        vect[hand->suit]++;
        hand++;
    }
}

typedef struct {
    int values[VALUES];
    int suits[SUITS];
    int couples;
    int triples;
    int quadruples;
    int same_suit;
    int straight;
    int high_couple;
    int high_triple;
    int high_quadruple;
    int high_card;
    int score;
} hand_check;

void find_repetitions(hand_check *output) {
    output->high_couple = output->high_triple = output->high_quadruple = -1;
    for (int i = 0; i < VALUES; i++) {
        if (output->values[i] == 2) {
            output->high_couple = i;
            output->couples++;
        }

        if (output->values[i] == 3) {
            output->high_triple = i;
            output->triples++;
        }

        if (output->values[i] == 4) {
            output->high_quadruple = i;
            output->quadruples++;
        }
        if (output->values[i])
            output->high_card = i;
    }
}

void find_same_suit(hand_check *output) {
    for (int i = 0; i < SUITS; i++) {
        if (output->suits[i] == HAND) {
            output->same_suit = 1;
        } else if (output->suits[i]) {
            return;
        }
    }
}

void find_straight(hand_check *output) {
    int len = 0;
    for (int i = 0; i < VALUES; i++) {
        if (output->values[i]) {
            len++;
            if (len == HAND) {
                output->straight = 1;
                return;
            }
        } else {
            len = 0;
        }
    }
}

hand_check build_hand_check(card *hand) {
    hand_check output = {0};

    count_values(hand, output.values);
    count_suits(hand, output.suits);

    find_repetitions(&output);
    find_straight(&output);
    find_same_suit(&output);

    return output;
}

bool is_one_pair(card *hand, hand_check *hc) { return hc->couples == 1; }

bool is_two_pair(card *hand, hand_check *hc) { return hc->couples == 2; }

bool is_three_of_a_kind(card *hand, hand_check *hc) { return hc->triples == 1; }

bool is_straight(card *hand, hand_check *hc) { return hc->straight; }

bool is_flush(card *hand, hand_check *hc) { return hc->same_suit; }

bool is_full_house(card *hand, hand_check *hc) {
    return hc->couples == 1 && hc->triples == 1;
}

bool is_four_of_a_kind(card *hand, hand_check *hc) { return hc->quadruples; }

bool is_straight_flush(card *hand, hand_check *hc) {
    return hc->same_suit && hc->straight;
}

bool is_royal_flush(card *hand, hand_check *hc) {
    return hc->same_suit && hc->straight && hc->values[10];
}

hand_check get_score(card *hand) {

    bool (*checks[])(card *, hand_check *hc) = {
        is_one_pair,       is_two_pair,       is_three_of_a_kind,
        is_straight,       is_flush,          is_full_house,
        is_four_of_a_kind, is_straight_flush, is_royal_flush};
    int output = 0;

    hand_check hc = build_hand_check(hand);

    int count = sizeof(checks) / sizeof(bool (*)(card *, hand_check *));
    for (int i = 0; i < count; i++) {
        if (checks[i](hand, &hc))
            output = i + 1;
    }
    hc.score = output;
    return hc;
}

int parse_suit(char c) {
    int suit;
    switch (c) {
    case 'H':
        suit = 0;
        break;
    case 'D':
        suit = 1;
        break;
    case 'C':
        suit = 2;
        break;
    case 'S':
        suit = 3;
        break;
    default:
        fprintf(stderr, "unknown suit %c\n", c);
        exit(1);
    }

    return suit;
}

int parse_value(char c) {
    int value;
    switch (c) {
    case 'T':
        value = 10;
        break;
    case 'J':
        value = 11;
        break;
    case 'Q':
        value = 12;
        break;
    case 'K':
        value = 13;
        break;
    case 'A':
        value = 14;
        break;
    default:
        value = c - '0';
    }

    return value;
}

int get_highest_card(hand_check *pl1, hand_check *pl2) {
    for (int i = VALUES - 1; i >= 0; i--) {
        int c1 = pl1->values[i];
        int c2 = pl2->values[i];
        if (c1 && c2)
            continue;

        if (c1)
            return 1;
        if (c2)
            return 2;
    }
    return 0;
}

#define read_ch(c, fp)                                                         \
    c = fgetc(fp);                                                             \
    if (c == EOF)                                                              \
        return 0;

int read_file(FILE *fp, card *pl1, card *pl2) {
    for (int i = 0; i < 10; i++) {
        char c;
        read_ch(c, fp);
        int value = parse_value(c);
        read_ch(c, fp);
        int suit = parse_suit(c);
        fgetc(fp);
        if (i < HAND) {
            pl1[i].value = value;
            pl1[i].suit = suit;
        } else {
            pl2[i % HAND].value = value;
            pl2[i % HAND].suit = suit;
        }
    }
    return 1;
}

void print_hand(card *p) {
    for (int i = 0; i < HAND; i++) {
        printf("V: %d, S: %d - ", p->value, p->suit);
        p++;
    }
}

int highest_score(hand_check *h1, hand_check *h2) {
    int score = 0;
    if (h1->score > h2->score) {
        score = 1;
    } else if (h1->score < h2->score) {
        score = 2;
    }
    return score;
}

int untie_high_rank(int p1, int p2) {
    if (p1 > p2)
        return 1;
    if (p2 > p1)
        return 2;
    return 0;
}

int untie_match(hand_check *h1, hand_check *h2) {
    int output = 0;
    switch (h1->score) {
    case 1:
    case 2:
        output = untie_high_rank(h1->high_couple, h2->high_couple);
        break;
    case 3:
    case 6:
        output = untie_high_rank(h1->high_triple, h2->high_triple);
        break;
    case 7:
        output = untie_high_rank(h1->high_quadruple, h2->high_quadruple);
        break;
    case 4:
    case 5:
    case 8:
        output = untie_high_rank(h1->high_card, h2->high_card);
        break;
    }

    if (output == 0)
        output = get_highest_card(h1, h2);

    return output;
}

int main(int arc, char **argv) {

    card pl1[5] = {0};
    card pl2[5] = {0};

    int win1 = 0;
    while (read_file(stdin, pl1, pl2)) {
        hand_check sc1 = get_score(pl1);
        hand_check sc2 = get_score(pl2);
        int score = highest_score(&sc1, &sc2);
        if (score == 1)
            win1++;
        else if (score == 0 && untie_match(&sc1, &sc2) == 1)
            win1++;
    }

    printf("Player 1 score: %d\n", win1);
    return 0;
}
