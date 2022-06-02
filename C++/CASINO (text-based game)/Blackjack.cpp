#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include "Blackjack.h"
#include "check_valid_bet.h"
#include "gainorlose.h"
using namespace std;

// to enlarge the hand by 1
void enlarge( int * &hand, int &handNum ){
    int * newHand = new int[handNum + 1];

    // copy all the items in hand to newHand
    for ( int i = 0; i < handNum; ++i){
	newHand[i] = hand[i];
    }

    delete [] hand;    	// release dynamic memory
    hand = newHand;
    ++handNum;    	// record the new hand size
}

// to deal a card
int deal_hand( bool dealt_card[] ){
    int card;
    bool dealt = 0;

    // check whether the card is dealt
    while ( !dealt ){
	card = rand() % 52;    	// card generated would be in range 0 - 51

	if( dealt_card[card] ){
	    dealt_card[card] = 0;   	 // record the card is dealt
	    dealt = 1;
	    return (card % 13);   	 // number within 0-12 will be returned
	}    // end of if statement
    }    // end of while loop
}

// to give sum of hand
int sum_hand( int * hand, int handNum, int card_value[] ){
    int aceNum = 0, sum = 0;

    // count the number of ace
    for ( int i = 0; i < handNum; ++i){
	if ( hand[i] == 0)
	    ++aceNum;
    }

    // find the sum of the hand
    for ( int i = 0; i < handNum; ++i){
	sum += card_value[hand[i]];
    }

    // deal with soft hand case
    while ( true ){
	if ( sum > 21 && aceNum > 0 ){
	    sum -= 10;
	    --aceNum;
	}

	if (aceNum == 0 || sum <= 21)
	    break;
    }

    return sum;
}

// use to print the hand
void print_hand( int * hand, int handNum, string card_face[] ){
    for ( int i = 0; i < handNum; ++i)
	cout << card_face[ hand[i] ] << " ";

    cout << endl << endl;
}

void check_valid_hit( string &x ){
	while( true ){
	    	if (x == "0" || x == "1"){    // check validity of input
		    break;
	    	}
	    	else {
		    	cout << "Invalid input. Please enter again." << endl;    // ask the player to input again
		    	cin >> x;
	    	}
    	}
}

// the BlackJack game
double Blackjack( int money, double extra ){
    int cost;

    cout << "Enter your bet." << endl;
    cin >> cost;    // intake the cost
    check_valid_bet( money, cost );    // check validity of the cost, re-enter the cost if invalid

    int player_hand_num = 2, dealer_hand_num = 2;
    bool dealt_card[52];    // storing which card is being dealt in the game
    int * player_hand = new int[player_hand_num], * dealer_hand = new int[dealer_hand_num];
    srand(time(NULL));

    for ( int i = 0; i < 52; ++i)    // create the array dealt_card
	dealt_card[i] = 1;

    for ( int i = 0; i < 2; ++i)    // create the player_hand
	player_hand[i] = deal_hand( dealt_card );

    for ( int i = 0; i < 2; ++i)    // create the dealer_hand
	dealer_hand[i] = deal_hand( dealt_card );

    // check any blackjack appears
    int odds, player_sum, dealer_sum;
    bool win, hit;
    string face_up_card;
    int card_value[13] = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    string card_face[13] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    // calculate the points that dealer and player have
    dealer_sum = sum_hand( dealer_hand, dealer_hand_num, card_value );
    player_sum = sum_hand( player_hand, player_hand_num, card_value );
	
    cout << endl;
	
    // check any blackjack case or a tie
    if ( dealer_sum == 21 && player_sum == 21 ){
	cout << "Dealer's hand: ";
	print_hand( dealer_hand, dealer_hand_num, card_face );    // show dealer's hand

	cout << "You and dealer have the same points." << endl;    // display the result
	cout << "Tie!!!" << endl;
	cout << "Net change of your money: " << 0 << endl;

	return 0;
    }    // end of if statement
    else if ( dealer_sum == 21 ){    // check dealer's hand
	odds = 2;
	win = 0;

	cout << "Dealer's hand: ";    // show dealer's hand
	print_hand( dealer_hand, dealer_hand_num, card_face );

	cout << "Your hand: ";    // show player's hand                
	print_hand( player_hand, player_hand_num, card_face );

	cout << "Dealer gets a BlackJack!!!" << endl;    // display the result
	cout << "Dealer wins!!!" << endl;
	cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;

	return gainorlose(cost, odds, win, extra);
    }    // end of first else if statement
    else if ( player_sum == 21 ){    // check player's hand
	odds = 2;
	win = 1;

	face_up_card = card_face[ dealer_hand[0] ];    // show the first card of dealer
	cout << "The face up card of the dealer : " << face_up_card << endl;

	cout << "Your hand: ";    // show player's hand                
	print_hand( player_hand, player_hand_num, card_face );

	cout << "You get a BlackJack!!!" << endl;    // display the result
	cout << "You win!!!" << endl;
	cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;

	return gainorlose(cost, odds, win, extra);
    }    // end of second else if statement

    face_up_card = card_face[ dealer_hand[0] ];    // show the first card of dealer
    cout << "The face up card of the dealer: " << face_up_card << endl;

    cout << "Your hand: ";    // show player's hand
    print_hand( player_hand, player_hand_num, card_face );

    cout << "Hit or stand? ( 0: Stand ; 1: Hit )" << endl;

    string x;
    cin >> x;

    check_valid_hit( x );    // check the validity of input x
    hit = stoi(x);

    while ( hit ){    // keep giving player one more hard if hit
	enlarge( player_hand, player_hand_num );
	player_hand[player_hand_num - 1] = deal_hand( dealt_card );

	cout << "Your hand: ";    // show player's hand                
	print_hand( player_hand, player_hand_num, card_face );    // show player's hand

	cout << "Hit or stand? ( 0: Stand ; 1: Hit )" << endl;
	cin >> x;
	    
	check_valid_hit( x );    // check the validity of input x
	hit = stoi(x);

	if ( !hit )
	    player_sum = sum_hand( player_hand, player_hand_num, card_value );
    }

    dealer_sum = sum_hand( dealer_hand, dealer_hand_num, card_value );

    while ( dealer_sum < 17 ){    // give one more card to dealer if dealer's point < 17
	enlarge( dealer_hand, dealer_hand_num );
	dealer_hand[dealer_hand_num - 1] = deal_hand( dealt_card );
	dealer_sum = sum_hand( dealer_hand, dealer_hand_num, card_value );
    }

    // check any busted case
    if ( player_sum > 21 ){
	win = 0;
	odds = 1;

	cout << "Dealer's hand: ";
	print_hand( dealer_hand, dealer_hand_num, card_face );    // show dealer's hand

	cout << "You are busted!!!" << endl;    // display the result
	cout << "Dealer wins!!!" << endl;
	cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;

	return gainorlose( cost, odds, win, extra );
    }    // end of if statement
    else if ( dealer_sum > 21 ){
	win = 1;
	odds = 1;

	cout << "Dealer's hand: ";
	print_hand( dealer_hand, dealer_hand_num, card_face );    // show dealer's hand

	cout << "Dealer is busted!!!" << endl;    // display the result
	cout << "You win!!!" << endl;
	cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;

	return gainorlose( cost, odds, win, extra );
    }

    // check whether dealer or player wins, or have a tie
    if ( dealer_sum > player_sum ){
	win = 0;
	odds = 1;

	cout << "Dealer's hand: ";
	print_hand( dealer_hand, dealer_hand_num, card_face );    // show dealer's hand

	cout << "Dealer has higher points." << endl;    // display the result
	cout << "Dealer wins!!!" << endl;
	cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;

	return gainorlose( cost, odds, win, extra );
    }    // end of if statement
    else if ( player_sum > dealer_sum ){
	win = 1;
	odds = 1;

	cout << "Dealer's hand: ";
	print_hand( dealer_hand, dealer_hand_num, card_face );    // show dealer's hand

	cout << "You have higher points." << endl;    // display the result
	cout << "You win!!!" << endl;
	cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;

	return gainorlose( cost, odds, win, extra );
    }    // end of else if statement
    else{
	cout << "Dealer's hand: ";
	print_hand( dealer_hand, dealer_hand_num, card_face );    // show dealer's hand

	cout << "You and dealer have the same points." << endl;    // display the result
	cout << "Tie!!!" << endl;
	cout << "Net change of your money: " << 0 << endl;

	return 0;
    }
}

//test game independently
//int main(){
  //Blackjack(1000,0);
//}
