#include <iostream>
#include <cstdlib>
#include <ctime>
#include "tiger_machine.h"
#include "check_valid_bet.h"
#include "gainorlose.h"
using namespace std;

// print the board out
void printBoard ( char a[][3] ){
    cout << endl;
    cout << "Output screen:" << endl;
    
    for ( int i = 0 ; i < 3 ; ++i ){
        for ( int j = 0 ; j < 3 ; ++j )
            cout << a[i][j] << " ";

        cout << endl;
    }
    
    cout << endl;
}

// create the board for tiger machine
void createBoard( char a[][3] ){
    char symbol[] = {'!', '@', '#', '$', '%', '^', '&', '*', '7', '+'};    // symbol for the board

    for ( int i = 0 ; i < 3 ; ++i ){
        for ( int j = 0 ; j < 3 ; ++j ){
            a[i][j] = symbol[rand() % 10];
        }    // end of j loop
    }    // end of i loop
    
    //prevent 3 same item in a column
    for ( int i = 0 ; i < 3 ; ++i ){
        while ( a[0][i] == a[1][i] && a[1][i] == a[2][i] ){
            a[1][i] = symbol[rand() % 10];
        }    // end of while loop
    }    // end of i loop
}


// check whether the characters along the same row / diagonal are all same or not
bool check( char a[][3] ){
    bool x = (a[0][0] == a[1][1] && a[1][1] == a[2][2]);                //top left to right bottom
    bool y = (a[0][2] == a[1][1] && a[1][1] == a[2][0]);                //top right to left bottom
    
    for ( int i = 0 ; i < 3 ; ++i ){
        bool z = (a[i][0] == a[i][1] && a[i][1] == a[i][2]);            //same row
        if (z){
            return 1;
            break;                                                          //any same row have
        }
    }
    if(x || y){
        return 1;
    }
    else{
        return 0;
    }
}

double tiger_machine( int money, double extra ){
    int cost;
    char board[3][3];
    srand(time(NULL));
    
    cout << "If you get '777' in the middle row, you can gain 30x !" << endl << endl;
    
    cout << "Enter your bet." << endl;
    cin >> cost;                         // intake the cost
    check_valid_bet( money, cost );      // check validity of the cost, re-enter the cost if invalid
    
    createBoard( board );                // create the board
    printBoard( board );                 // print out the board for the user
    
    if ( check( board ) ){
        bool win = 1;
        int odds = 1;
        
        cout << "You win!!!" << endl;
        
        // if 777 in the middle row --> odds x 30
        if (board[1][0] == '7' && board[1][1] == '7' && board[1][2] == '7'){
            cout << "Congratulations!!!! You get a 777!!!! 77x gain!!!!!" << endl;
            odds = 77;
        }
        
        cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;
        
        return gainorlose(cost, odds, win, extra);
    }    // end of if statement
    else{
        bool win = 0;
        int odds = 1;
        
        cout << "You lose!!!" << endl;
        
        //output the change in money (can be positive or negative)
        cout << "Net change of your money: " << gainorlose(cost, odds, win, extra) << endl;
        return gainorlose(cost, odds, win, extra);
    }    // end of else statement
}
