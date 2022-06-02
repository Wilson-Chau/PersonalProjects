#include <iostream>
#include "gainorlose.h"
using namespace std;

double gainorlose(double cost, int odds, bool win, double extra){
	double gain = 0;

	if (win){
		gain = cost * (odds) * ( 1 + extra / 100);
	}
	else{
		gain = 0 - cost;
	}

	return gain;
}
