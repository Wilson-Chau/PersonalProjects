#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>
#include <ctime>
#include "sicbo.h"
#include "check_valid_bet.h"
#include "gainorlose.h"
using namespace std;

// print the odd table
void printodds(int oddslist[3]){
	cout << "// Odds table //" << endl;
	cout << "Type 0: Triple /" << oddslist[0] << " to 1." << " /(Same number for 3 dice)" << endl;
	cout << "Type 1: Big /" << oddslist[1] << " to 1." << " /(Sum of 3 dice > 10, except Triple)" << endl;
	cout << "Type 2: Small /" << oddslist[2] << " to 1." << " /(Sum of 3 dice <= 10, except Triple)" << endl;
	cout << endl;

}
// generate the 3 dice and the type result
void gen3dice(string *type){
	string types[3] = {"Triple", "Big", "Small"};
	srand(time(NULL));

	int dice1 = rand() % 6 + 1;		// random from 1-6
	int dice2 = rand() % 6 + 1;
	int dice3 = rand() % 6 + 1;

	int sum = dice1 + dice2 + dice3;

	if (dice1 == dice2 && dice2 == dice3){
		*type = "0";				//triple
	}
	else if (sum > 10){
		*type = "1";				//big
	}

	cout << "Dice result: " << dice1 << " " << dice2 << " " << dice3 << endl;
	cout << "Type: " << types[stoi(*type)] << endl;
}
// check whether player's guess is correct
bool correct(string guess, string type){

	if (guess == type){
		cout << "Your guess is correct." << endl;
		return true;
	}
	else{
		cout << "Your guess is wrong." << endl;
		return false;
	}

}

double sicbo(int money, double extra ){
	int oddslist[3] = {30,1,1};			// index 0 = triple, 1=big, 2= small --> e.g. $1 gain $30 if Triple
	printodds(oddslist);				// print out the odds table

	string guess;
	
	cout << "Please guess and enter the result type. / 0: Triple / 1: Big / 2: Small" << endl;

	while (1 == 1){
		cin >> guess;   				        // input the cost
		cout << endl;

		if ( guess=="0" || guess=="1" || guess=="2"){		// only 3 valid input are allowed
			break;
		}
		else{
			cout << "Invalid input!!! Please enter again" << endl;
		}
	}
	
	int cost;

	cout << "Enter your bet." << endl;
	cin >> cost;    				// input the cost
	check_valid_bet( money, cost );    		// check validity of the cost, need re-enter the cost if invalid

	string type = "2"; 				// 2 represent type small, initiate the type
	gen3dice(&type);				// generate the 3 dice, and gives the correct type ("0" or "1")

	int odds = oddslist[stoi(type)];		// assign the corresponding odds ( stoi() turn type to an integer to access the list above)
	bool win = correct(guess, type);

	//output the change in money (can be positive or negative)
	cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;

	return gainorlose(cost, odds, win, extra);

}
