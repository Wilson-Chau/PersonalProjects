#include <iostream>
#include <string>
#include <limits>
#include "check_valid_bet.h"
using namespace std;

void check_valid_bet( int money, int &cost ){
    while ( true ){
	    if(cin.fail()){	//wrong data type
		    cin.clear();
		    cin.ignore(numeric_limits<streamsize>::max(),'\n');
		    cout << "Invalid input. Please enter an integer again." << endl;
		    cin >> cost;
	    }
	    else if (cost <= 0){
		    cost = 0;				// avoid error
		    cout << "Please enter a positive integer for your bet!!!" << endl;
		    cin >> cost;
	    }
	    else if ( cost <= money ){
			break;
	    }
	    else if ( cost > money){
			cost = 0;				// avoid error
			cout << "You do not have enough money!!! Please enter again." << endl;
			cin >> cost;
	    }
	    else{
		    cost = 0;				// avoid error
		    cout << "Invalid input. Please enter again." << endl;
		    cin >> cost;
	    }
	}
}
