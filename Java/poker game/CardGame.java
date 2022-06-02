import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class implements the simple card game.
 * 
 * @author Chau Cheuk Him
 */

public class CardGame {
	JFrame frame;
	JTextField inputBetTextField;
	JLabel infoLabel;
	JLabel moneyLabel;
	Player player;
	Dealer dealer;
	GameController controller;
	ArrayList<JButton> replaceBtnList;
	
	/**
	 * Constructor to initialize the CardGame object.
	 * 
	 */
	public CardGame() {
		frame = new JFrame("A Simple Card Game");
		inputBetTextField = new JTextField(10);
		infoLabel = new JLabel();
		moneyLabel = new JLabel();
		player = new Player();
		dealer = new Dealer();
		controller = new GameController();
		replaceBtnList = new ArrayList<JButton>();
	}
	
	/**
	 * main method for the class CardGame.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CardGame gui = new CardGame();
		gui.go();
	}

	/**
	 * This method operate the card game.
	 */
	public void go() {		
		//create the menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Control");
		JMenuItem exitBtn = new JMenuItem("Exit");
		
		exitBtn.addActionListener(new exitListener());
		
		menu.add(exitBtn);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		//set up the main Panel
		JPanel mainPanel = new JPanel();
		
		//set up the dealer panel
		JPanel dealerPanel = new JPanel();
		
		dealer.setCards();
		
		for (int i = 0; i < 3; i++) {
			dealerPanel.add(dealer.getCards().get(i));
		}
				
		//set up the player panel
		JPanel playerPanel = new JPanel();
		
		player.setCards();
		
		for (int i = 0; i < 3; i++) {
			playerPanel.add(player.getCards().get(i));
		}
		
		//set up the replace card button panel
		JPanel RpCardBtnPanel = new JPanel();
		
		for (int i = 1; i <= 3; i++) {
			JButton btn = new JButton("Replace Card " + i);
			replaceBtnList.add(btn);
			RpCardBtnPanel.add(btn);
		}

		//enable the replace button
		for (JButton btn : replaceBtnList) {
			btn.addActionListener(new replaceListener(btn));
		}
		
		//set up the button panel
		JPanel buttonPanel = new JPanel();
		
		JLabel betLabel = new JLabel("Bet: $ ");
		JButton startBtn = new JButton("Start");
		JButton resultBtn = new JButton("Result");
		
		buttonPanel.add(betLabel);
		buttonPanel.add(inputBetTextField);
		buttonPanel.add(startBtn);
		buttonPanel.add(resultBtn);
		
		//enable the start and result button
		startBtn.addActionListener(new startListener());
		resultBtn.addActionListener(new resultListener());
		
		//set up the info panel
		JPanel infoPanel = new JPanel();
		
		infoLabel.setText("Please place your bet! Amount of money you have: $");
		moneyLabel.setText(Integer.toString(player.getBalance()));
		
		infoPanel.add(infoLabel);
		infoPanel.add(moneyLabel);
		
		//add all panels into main panel
		mainPanel.setLayout(new GridLayout(5, 1));
		mainPanel.add(dealerPanel);
		mainPanel.add(playerPanel);
		mainPanel.add(RpCardBtnPanel);
		mainPanel.add(buttonPanel);
		mainPanel.add(infoPanel);
		
		//set the background color to green
		dealerPanel.setBackground(Color.green);
		playerPanel.setBackground(Color.green);
		RpCardBtnPanel.setBackground(Color.green);
		
		//set up the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mainPanel);
		frame.setSize(400, 700);
		frame.setVisible(true);
	}
	
	class exitListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			frame.dispose();
		}
	}
	
	class replaceListener implements ActionListener{
		private JButton btn;
		
		public replaceListener(JButton btn) {
			this.btn = btn;
		}
		
		public void actionPerformed(ActionEvent e) {
			int btnIdx = replaceBtnList.indexOf(btn);
			
			if (controller.isStarted() && controller.getReplaceChance() != 0 && !controller.isClicked(btnIdx)) {
				int playerCard = controller.getDeck().get(0) % 13;
				player.setCards(btnIdx, playerCard);
				
				controller.clicked(replaceBtnList.indexOf(btn));
				controller.removeCard(1);
				controller.decReplaceChance();
				controller.clicked(btnIdx);
			}			
		}
	}
	
//	class replace1Listener implements ActionListener {
//		public void actionPerformed(ActionEvent evt){
//			if (rp_1_enabled && (replaceChance > 0)) {
//				int playerCard = (deck.get(0) % 13) + 1;
//				playerHand.set(0, playerCard);
//				
//				player_card1_icon = new ImageIcon(deck.get(0) + ".gif");
//				player_card_1_label.setIcon(player_card1_icon);
//				
//				deck.remove(0);
//				rp_1_enabled = false;
//				replaceChance -= 1;
//			}
//		}
//	}
//	
//	class replace2Listener implements ActionListener {
//		public void actionPerformed(ActionEvent evt){
//			if (rp_2_enabled && (replaceChance > 0)) {
//				int playerCard = (deck.get(0) % 13) + 1;
//				playerHand.set(1, playerCard);
//								
//				player_card2_icon = new ImageIcon(deck.get(0) + ".gif");
//				player_card_2_label.setIcon(player_card2_icon);
//				
//				deck.remove(0);
//				rp_2_enabled = false;
//				replaceChance -= 1;
//			}
//		}		
//	}
//	
//	class replace3Listener implements ActionListener {
//		public void actionPerformed(ActionEvent evt){
//			if (rp_3_enabled && (replaceChance > 0)) {
//				int playerCard = (deck.get(0) % 13) + 1;
//				playerHand.set(2, playerCard);
//
//				player_card3_icon = new ImageIcon(deck.get(0) + ".gif");
//				player_card_3_label.setIcon(player_card3_icon);
//				
//				deck.remove(0);
//				rp_3_enabled = false;
//				replaceChance -= 1;
//			}
//		}
//	}
	
	class startListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (!controller.isStarted() && controller.startBtnEnabled()) {
				player.setInputBet(Integer.parseInt(inputBetTextField.getText()));
				
				controller.gameStarted();
				
				//get the dealerHand and playerHand
				for (int i = 0; i < 3; i++) {
					player.getHand().add(controller.getDeck().get(i));
					dealer.getHand().add(controller.getDeck().get(i + 3));
				}
				
				controller.removeCard(6);
				
				//display the player hand
				for (int i = 0; i < 3; i++) {
					player.setCards(i, player.getHand().get(i));
				}
				
				frame.repaint();
			}
		}
	}
	
	class resultListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (controller.isStarted()) {
				//display the dealer hand
				for (int i = 0; i < 3; i++) {
					dealer.setCards(i, dealer.getHand().get(i));
				}
				
				frame.repaint();
				
				//determine who is the winner
				if (dealer.getSpecialCardCount() > player.getSpecialCardCount()) {
					JOptionPane.showMessageDialog(frame, "Sorry! The Dealer wins this round!");
					
					//deduct the bet from player's balance
					player.changeBalance(-player.getInputBet());
				}
				else if (player.getSpecialCardCount() > dealer.getSpecialCardCount()) {
					JOptionPane.showMessageDialog(frame, "Congratulations! You win this round!");
					
					//add the bet to player's balance
					player.changeBalance(player.getInputBet());
				}
				else {                                                     //case of dealer and player have the same number of special card(s)										
					//determine who is the winner
					if (dealer.getPoints() >= player.getPoints()) {
						JOptionPane.showMessageDialog(frame, "Sorry! The Dealer wins this round!");
						
						//deduct the bet from player's balance
						player.changeBalance(-player.getInputBet());
					}
					else {
						JOptionPane.showMessageDialog(frame, "Congratulations! You win this round!");
						
						//add the bet to player's balance
						player.changeBalance(player.getInputBet());
					}
				}
								
				//clear the dealer's hand and player's hand
				player.clearHand();
				dealer.clearHand();
				
				//replace all icons back to card back
				player.setCards();
				dealer.setCards();
				
				//reset all buttons status and condition
				controller = new GameController();
				
				//check the balance of player
				if (player.getBalance() <= 0) {
					//disable the start button
					controller.lockStartBtn();
					
					//display restart message
					infoLabel.setText("You have no more money! Please start a new game!");
					moneyLabel.setText("");
					
					String outputMsg = "Game over!\nYou have no more money!\nPlease start a new game!";
					JOptionPane.showMessageDialog(frame, outputMsg);					
				}
				else {
					//display the current balance
					moneyLabel.setText("" + player.getBalance());
				}
				
				frame.repaint();			
			}
		}
	}	
}
