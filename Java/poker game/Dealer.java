import java.util.*;
import javax.swing.*;

public class Dealer {
	private ArrayList<Integer> dealerHand;
	private ArrayList<JLabel> dealerCards;
	private int specialCardCount;
	private int points;
	
	public Dealer() {
		dealerHand = new ArrayList<Integer>();
		dealerCards = new ArrayList<JLabel>();
		specialCardCount = 0;
		points = 0;
	}
	
	public ArrayList<Integer> getHand() {
		return dealerHand;
	}

	public ArrayList<JLabel> getCards() {
		return dealerCards;
	}
	
	public int getSpecialCardCount() {
		return specialCardCount;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setCards() {
		for (int i = 0; i < 3; i++) {
			JLabel cardLabel = new JLabel();
			ImageIcon cardIcon = new ImageIcon("card_back.gif");
			
			cardLabel.setIcon(cardIcon);
			dealerCards.add(cardLabel);
		}
	}
	
	public void setCards(int idx, int cardIdx) {
		int fileNum = cardIdx - 1;
		ImageIcon cardIcon = new ImageIcon(fileNum + ".gif");
		dealerCards.get(idx).setIcon(cardIcon);
		
		int cardValue = cardIdx % 13;
		
		if (cardValue <= 10) {
			points += cardValue;
			points = points % 10;
		}
		
		//check special card
		if (cardValue > 10) {
			specialCardCount++;
		}
	}
	
	public void clearHand() {
		dealerHand = new ArrayList<Integer>();
	}
}
