import java.util.*;
import javax.swing.*;

public class Player {
	private ArrayList<Integer> playerHand;
	private ArrayList<JLabel> playerCards;
	private int inputBet;
	private int balance;
	private int specialCardCount;
	
	public Player() {
		playerHand = new ArrayList<Integer>();
		playerCards = new ArrayList<JLabel>();
		inputBet = 0;
		balance = 100;
		specialCardCount = 0;
	}
	
	public ArrayList<Integer> getHand() {
		return playerHand;
	}

	public ArrayList<JLabel> getCards() {
		return playerCards;
	}

	public int getInputBet() {
		return inputBet;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public int getSpecialCardCount() {
		return specialCardCount;
	}
	
	public int getPoints() {
		int points = 0;
		
		for (int i : playerHand) {
			if (i <= 10) {
				points += i;
			}
		}
		
		return points;
	}
	
	public void setInputBet(int inputBet) {
		this.inputBet = inputBet;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public void setCards() {
		for (int i = 0; i < 3; i++) {
			JLabel cardLabel = new JLabel();
			ImageIcon cardIcon = new ImageIcon("card_back.gif");
			
			cardLabel.setIcon(cardIcon);
			playerCards.add(cardLabel);
		}
	}
	
	public void setCards(int idx, int cardIdx) {
		int fileNum = cardIdx - 1;
		ImageIcon cardIcon = new ImageIcon(fileNum + ".gif");
		playerCards.get(idx).setIcon(cardIcon);
		
		int cardValue = cardIdx % 13;
		playerHand.set(idx, cardValue);
		
		//check special card		
		if (cardValue > 10) {
			specialCardCount++;
		}
	}
	
	public void clearHand() {
		playerHand = new ArrayList<Integer>();
	}
	
	public void changeBalance(int amount) {
		balance += amount;
	}
}
