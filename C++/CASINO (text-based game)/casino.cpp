#include <iostream>
#include <fstream>
#include <string>
#include "Blackjack.h"
#include "check_valid_bet.h"
#include "gainorlose.h"
#include "sicbo.h"
#include "tiger_machine.h"

using namespace std;

int main() {
	// load status at the beginning (once only)
	ifstream fin ("status.txt");
	if ( fin.fail() ){
		cout << "Error in loading status!" << endl;
		exit(1);
	}
	
	//initiate variables
	int money = 1000;		// player's money
	int round = 0;			// count total number of rounds
	int Nround = 0;			// number of rounds remaining for special events
	double extra = 0;		// extra % of gaining

	//read from txt: int round, Nround, money, extra
	fin >> money >> round >> Nround >> extra;

	string input;
	bool quit = false;
	cout << "Welcome come to Casino!!" << endl;
	// print current status
	// keep looping(select and play game) until quit
	while (!quit) {
		// cout game status
		cout << "Money: " << money << " / Round: " << round;
		cout << " / Round remaining with extra odds: " << Nround << " / Extra odds: " << extra << "%" << endl << endl;
		// ask player to input
		cout << "What would you like to play?" << endl;
		cout << "Input 0: Sicbo // 1: Blackjack // 2: Tiger Machine // 'quit': Exit the game // 'reset': Reset game status (Money = 1000)" << endl << endl;
		
		while(true){				// loop for getting valid input
			cin >> input;			// if invalid input, go back to this line without the above cout
			if (input == "quit" || input == "reset" || input == "0" || input == "1" || input == "2"){
				break;	//valid, esape loop
			}
			else{
				cout << "Invalid input, please enter again." << endl;
			}
		}

		if (input == "quit"){
			quit = true;		
			cout << "You have quitted the Casino." << endl;
			break;		// escape the game loop
		}

		//reset
		if (input == "reset"){
			cout << "Are you sure to reset game status? Input 1: Reset // Input any other key: Cancel // " << endl;
			cin >> input;
			if (input == "1"){
				cout << "You have reset the game status." << endl;
				money = 1000;
				round = 0;
				Nround = 0;
				extra = 0;
				
				//save(output) game status after every sucessful round
				ofstream fout ("status.txt");
				if (fout.fail()) {
					cout << "Error in saving status!" << endl;
					exit(1);
				}
				fout << money << endl << round << endl << Nround << endl << extra;
			}
			else{
				cout << "You have cancelled the reset." << endl;
			}

			continue;	// go back to outerloop to choose game or quit the casino with displaying the game status
		}

		// goes to different game according to input

		if (input == "0"){
			cout << endl;
			cout << "==============================================================" << endl;
			cout << endl << "!!Sicbo!!" << endl;
			money += sicbo(money, extra);	//test: money + 100;//+1(money, extra);
			cout << endl << "==============================================================" << endl;
		}

		else if (input == "1"){
			cout << endl;
			cout << "==============================================================" << endl;
			cout << endl << "!!Blackjack!!" << endl;
			money += Blackjack(money, extra);//+2(money, extra);
			cout << endl << "==============================================================" << endl;
		}

		else if (input == "2"){
			cout << endl;
			cout << "==============================================================" << endl;
			cout << endl << "!!Tiger Machine!!" << endl;
			money += tiger_machine(money, extra);//+3(money, extra);
			cout << endl << "==============================================================" << endl;
		}

		cout << endl;	//seperate part
		

		round += 1;

		//deal with Nround and extra
		if (Nround > 0){
			Nround--;
		}

		if (Nround == 0){
			extra = 0;
		}

		// random event (every 10 rounds? or any other style?)
		if (round % 10 == 0){
			cout << endl << "!! Random event !!" << endl;
			int random = rand() % 3;					// 3 different random events

			switch(random){
				case 0:{						// 1% - 5% money stole
					int stole = money * (rand() % 6 + 5) / 100;
					money = money - stole;

					cout << "Someone stole $" << stole << " from you!" << endl;
					break;
				}

				case 1:{						// Pick up 5% - 10% money on the floor
					int pick = money * (rand() % 6 + 5) / 100;
					money = money + pick;

					cout << "You pick up $" << pick << " on the floor!" << endl;
					break;
				}

				case 2:{					// Extra 5-10% odds for 1-5 rounds
					Nround = rand() % 5 + 1;
					extra = rand() % 11 + 5;

					cout << "Extra " << extra << "% odds for " << Nround << " rounds!" << endl;
					break;
				}

			}

		}

		//save(output) game status after every sucessful round
		ofstream fout ("status.txt");
		if (fout.fail()) {
			cout << "Error in saving status!" << endl;
			exit(1);
		}
		fout << money << endl << round << endl << Nround << endl << extra;
	}
	return 0;
}
