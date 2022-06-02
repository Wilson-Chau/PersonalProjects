import java.util.*;

public class GameController {
	private ArrayList<Boolean> clickedList;
	private boolean isStarted;
	private boolean startBtnEnable;
	private int replaceChance;
	private ArrayList<Integer> deck;
	
	public GameController() {
		clickedList = new ArrayList<Boolean>();
		isStarted = false;
		startBtnEnable = true;
		replaceChance = 2;
		deck = createShuffledDeck();
		
		for (int i = 0; i < 3; i++) {
			clickedList.add(false);
		}
	}
	
	/**
	 * this method generate a shuffled deck.
	 * 
	 * @return an ArrayList with numbers from 0 to 51, representing a deck of 52 cards.
	 */
	public ArrayList<Integer> createShuffledDeck(){
		ArrayList<Integer> deck = new ArrayList<Integer>();
		
		for (int i = 0; i < 52; i++) {
			deck.add(i + 1);
		}

		for (int i = 0; i < deck.size(); i++) {
			int temp = deck.get(i);
			int idx = (int) (Math.random() * deck.size());
			deck.set(i, deck.get(idx));
			deck.set(idx, temp);
		}
		
		return deck;
	}
	
	public boolean isStarted() {
		return isStarted;
	}
	
	public void gameStarted() {
		isStarted = true;
	}
	
	public int getReplaceChance() {
		return replaceChance;
	}
	
	public boolean startBtnEnabled() {
		return startBtnEnable;
	}
	
	public ArrayList<Integer> getDeck() {
		return deck;
	}
	
	public void removeCard(int count) {
		for (int i = 0; i < count; i++) {
			deck.remove(0);
		}
	}
	
	public void lockStartBtn() {
		startBtnEnable = false;
	}
	
	public void decReplaceChance() {
		replaceChance--;
	}
	
	public boolean isClicked(int idx) {
		return clickedList.get(idx);
	}
	
	public void clicked(int idx) {
		clickedList.set(idx, true);
	}
}
