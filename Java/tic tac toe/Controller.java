/**
 * This class implements the control setting of the Tic Tac Toe game.
 * 
 * @author Chau Cheuk Him
 *
 */
public class Controller {
	String symbol;
	int round;
	int numberOfPlayer;
	boolean gameStart;
	boolean gameOver;
	boolean newGame;
	
	/**
	 * No-argument constructor of the Controller class.
	 * 
	 */
	public Controller() {
		symbol = "X";
		round = 1;
		numberOfPlayer = 0;
		gameStart = false;
		gameOver = false;
		newGame = true;
	}
	/**
	 * The getter of the symbol.
	 * 
	 * @return the symbol "X" or "O".
	 */
	public synchronized String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * This method is to increment the number of player
	 */
	public synchronized void addPlayer() {
		numberOfPlayer++;
	}
	
	/**
	 * This method is to decrement the number of player
	 */
	public synchronized void removePlayer() {
		numberOfPlayer--;
	}
	
	/**
	 * This method indicate whether the game is started.
	 * 
	 * @return true if the game is started, otherwise false
	 */
	public synchronized boolean isGameStart() {
		return this.gameStart;
	}
	
	/**
	 * This method indicate whether the game is over.
	 * 
	 * @return true if the game is over, otherwise false
	 */
	public synchronized boolean isGameOver() {
		return this.gameOver;
	}
	
	/**
	 * This method indicate whether it is a new game.
	 * 
	 * @return true if it is a new game, otherwise false
	 */
	public synchronized boolean isNewGame() {
		return this.newGame;
	}
	
	/**
	 * This method indicate whether the game is started.
	 * 
	 * @return true if the game is started, otherwise false
	 */
	public synchronized void startGame() {
		gameStart = true;
		gameOver = false;
		newGame = false;
	}
	
	/**
	 * This method indicate whether the game is ended.
	 * 
	 * @return true if the game is ended, otherwise false
	 */
	public synchronized void endGame() {
		numberOfPlayer = 0;
		gameStart = false;
		gameOver = true;
	}
	
	/**
	 * The getter of the number of current round.
	 * 
	 * @return an integer about the current round.
	 */
	public synchronized int getRound() {
		return this.round;
	}
	
	/**
	 * The getter of the number of player(s).
	 * 
	 * @return an integer about the number of player(s).
	 */
	public synchronized int getNumberOfPlayer() {
		return this.numberOfPlayer;
	}
	
	/**
	 * This method change the symbol to "X" and "O" alternately.
	 */
	public synchronized void changeSymbol() {
		if (symbol.equals("X")) {
			symbol = "O";
		}
		else {
			symbol = "X";
		}
	}
	
	/**
	 * This method is to increment the number of round
	 */
	public synchronized void incRound() {
		this.round++;
	}
	
	/**
	 * This method is to reset the controller
	 */
	public synchronized void reset() {
		symbol = "X";
		round = 1;
		numberOfPlayer = 0;
		gameStart = false;
		gameOver = false;
		newGame = true;
	}
}
