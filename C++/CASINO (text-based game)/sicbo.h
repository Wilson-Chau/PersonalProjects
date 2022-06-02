#ifndef SICBO_H
#define SICBO_H

#include <string>
using namespace std;

void printodds(int oddslist[3]);

void gen3dice(string *type);

bool correct(string guess, string type);

double sicbo(int money, double extra );

#endif
