import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
 * This class implements the client of the Tic Tac Toe game.
 * @author Chau Cheuk Him
 *
 */
public class Client{
	Socket sock;
	PrintWriter writer;
	View view;
	boolean submitBtnEnabled;
	boolean[] clicked;
	boolean gameStart;
	boolean gameBoardEnabled;
	
	/**
	 * No-argument constructor of the Client class.
	 */
	public Client() {
		submitBtnEnabled = true;
		gameStart = false;
		gameBoardEnabled = false;
		clicked = new boolean[9];
		
		for (boolean b : clicked) {
			b = false;
		}
	}
	
	/**
	 * Main method of the Client class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		client.go();
	}
	
	/**
	 * This method control the flow of the Client class.
	 */
	public void go() {		
		view = new View();
		view.getSubmitBtn().addActionListener(new SubmitBtnListener());
		view.getExitBtn().addActionListener(new ExitListener());
		
		// set up the game board
		for (JLabel grid : view.getGameBoard()) {
			grid.addMouseListener(new GridListener(grid));
		}
		
		try {
			sock = new Socket("127.0.0.1", 5000);
			writer = new PrintWriter(sock.getOutputStream(), true);
			
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);
			
			String command;
			while ((command = reader.readLine()) != null) {
				System.out.println("Command from server: " + command);
				
				if (command.startsWith("Start")) {
					gameStart = true;
				}
				else if (command.startsWith("Enable")) {
					gameBoardEnabled = true;
				}
				else if (command.startsWith("Disable")) {
					gameBoardEnabled = false;
				}
				else if (command.startsWith("YourTurn")) {
					view.getAnnounceLabel().setText("Your opponent has moved, now is your turn.");
				}
				else if (command.startsWith("ValidMove")) {
					view.getAnnounceLabel().setText("Valid move, wait for your opponent.");
				}
				else if (command.startsWith("Win")) {
					//announce the result
					System.out.println("Game Over");
					JOptionPane.showMessageDialog(view.getFrame(), "Congratulations. You win.");
					
					//disable the game board
					gameBoardEnabled = false;
				}
				else if (command.startsWith("Lost")) {					
					//announce the result
					System.out.println("Game Over");
					JOptionPane.showMessageDialog(view.getFrame(), "You lose.");
					
					//disable the game board
					gameBoardEnabled = false;
				}
				else if (command.startsWith("Draw")) {
					//announce the result
					System.out.println("Game Over");
					JOptionPane.showMessageDialog(view.getFrame(), "Draw.");
					
					//disable the game board
					gameBoardEnabled = false;
				}
				else if (command.startsWith("Exit")) {
					//announce the result
					System.out.println("Opponent left");
					JOptionPane.showMessageDialog(view.getFrame(), "Game Ends. One of the players left.");
					
					//disable the game board
					gameBoardEnabled = false;
				}
				else {
					String[] commandList= command.split("\\s+");
					int gridNum = Integer.parseInt(commandList[0]);
					String symbol = commandList[1].replaceAll("\\s+", "");
					
					JLabel grid = view.getGameBoard().get(gridNum);
					grid.setText(symbol);
					if (symbol.equals("O")) {
						grid.setForeground(Color.red);
					}
					clicked[gridNum] = true;
					
					//check any winner
					if (checkWinner()) {
						System.out.println(checkRow());
						System.out.println(checkColumn());
						System.out.println(checkDiagonal());
						//tell the server the game is over
						writer.println("GameOver");

						//disable the game board
						gameBoardEnabled = false;
					}
					else if (commandList[2].replaceAll("\\s+", "").equals("9")) {
						System.out.println("Draw");
						//tell the server the game is draw
						writer.println("Draw");

						//disable the game board
						gameBoardEnabled = false;
					}
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method check whether any row is filled by the same symbol.
	 * 
	 * @return true if any row is filled by the same symbol, otherwise false.
	 */
	public boolean checkRow() {
		ArrayList<JLabel> labelList = view.getGameBoard();
		for (int i = 0; i < 9; i += 3) {
			String str1 = labelList.get(i).getText();
			String str2 = labelList.get(i + 1).getText();
			String str3 = labelList.get(i + 2).getText();
		
			if (str1.equals(str2) && str1.equals(str3) && !str1.equals("")) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method check whether any column is filled by the same symbol.
	 * 
	 * @return true if any column is filled by the same symbol, otherwise false.
	 */
	public boolean checkColumn() {
		ArrayList<JLabel> labelList = view.getGameBoard();
		for (int i = 0; i < 3; i++) {
			String str1 = labelList.get(i).getText();
			String str2 = labelList.get(i + 3).getText();
			String str3 = labelList.get(i + 6).getText();
			
			if (str1.equals(str2) && str1.equals(str3) && !str1.equals("")) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method check whether any diagonal is filled by the same symbol.
	 * 
	 * @return true if any diagonal is filled by the same symbol, otherwise false.
	 */
	public boolean checkDiagonal() {
		ArrayList<JLabel> labelList = view.getGameBoard();
		String str1 = labelList.get(0).getText();
		String str2 = labelList.get(2).getText();
		String str3 = labelList.get(4).getText();
		String str4 = labelList.get(6).getText();
		String str5 = labelList.get(8).getText();
		
		if (str1.equals(str3) && str1.equals(str5) && !str1.equals("")) {
			return true;
		}
		else if (str2.equals(str3) && str2.equals(str4) && !str2.equals("")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method check any player wins. Either player will win if any row, column or diagonal is filled by the same symbol.
	 * 
	 * @return true if any player wins, otherwise false.
	 */
	public boolean checkWinner() {
		return (checkRow() || checkColumn() || checkDiagonal());
	}
	
	/**
	 * This class implements the actionListener of the submitBtn.
	 * 
	 * @author Chau Cheuk Him
	 *
	 */
	class SubmitBtnListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			if (submitBtnEnabled) {
				System.out.println("The submit button is clicked");

				String playerName = view.getTextField().getText();
				view.getAnnounceLabel().setText("WELCOME " + playerName);
				writer.println("createPlayer");
				System.out.println("Client is sending: createPlayer");
				
				submitBtnEnabled = false;
				view.getTextField().setText("");
			}			
		}
	}
	
	/**
	 * This class implements the actionListener of the exitBtn.
	 * 
	 * @author Chau Cheuk Him
	 *
	 */
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {		
			try {
				writer.println("Exit");
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
			System.exit(0);
		}
	}
	
	/**
	 * This class implements the mouseListener of each grid.
	 * 
	 * @author Chau Cheuk Him
	 *
	 */
	class GridListener implements MouseListener {
		private JLabel grid;
		
		public GridListener(JLabel grid) {
			this.grid = grid;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (gameStart && gameBoardEnabled && !clicked[view.getGameBoard().indexOf(grid)]) {
				try {
					writer.println(view.getGameBoard().indexOf(grid));
					System.out.println("You've clicked grid " + view.getGameBoard().indexOf(grid));
					
					//do announcement
					view.getAnnounceLabel().setText("Valid move, wait for your opponent.");
				}
				catch (Exception ex){
					ex.printStackTrace();
				}
				
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}