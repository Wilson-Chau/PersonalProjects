import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
 * This class implements the GUI of the Tic Tac Toe game.
 * 
 * @author Chau Cheuk Him
 */
public class View {
	JFrame frame;
	JLabel announceLabel;
	JButton submitBtn;
	JMenuItem exitBtn;
	JTextField textField;
	ArrayList<JLabel> gameBoard;
	
	/**
	 * No-argument constructor to initialize the View object.
	 * 
	 */
	public View() {
		//set up the frame
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create the menu
		JMenuBar menuBar = new JMenuBar();
		JMenu control = new JMenu("Control");
		exitBtn = new JMenuItem("Exit");
		JMenu help = new JMenu("Help");
		JMenuItem instructionBtn = new JMenuItem("Instruction");
		
		instructionBtn.addActionListener(new instructionListener());
		
		control.add(exitBtn);
		help.add(instructionBtn);
		menuBar.add(control);
		menuBar.add(help);
		
		//set up the announcement panel
		announceLabel = new JLabel("Enter your player name...");
						
		//set up the display panel
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(3,3));

		gameBoard = createGameBoard();
		for (JLabel label : gameBoard) {
			displayPanel.add(label);
		}
		
		//set up the control panel
		JPanel controlPanel = new JPanel();
		textField = new JTextField(25);
		submitBtn = new JButton("Submit");
		
		controlPanel.add(textField);
		controlPanel.add(submitBtn);
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		frame.setJMenuBar(menuBar);
		frame.add(announceLabel, BorderLayout.NORTH);
		frame.add(displayPanel, BorderLayout.CENTER);
		frame.add(controlPanel, BorderLayout.SOUTH);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	/**
	 * This method create the game board for the Tic Tac Toe game.
	 * 
	 * @return an ArrayList with 9 JLabels.
	 */
	//create the game board
	public ArrayList<JLabel> createGameBoard(){
		JLabel label;
		ArrayList<JLabel> labelList = new ArrayList<JLabel>();
		
		for (int i = 0; i < 9; i++) {
			label = new JLabel();
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			label.setFont(label.getFont().deriveFont(60.0f));
			labelList.add(label);
		}
		
		return labelList;
	}
	
	/**
	 * The getter of the JFrame.
	 * 
	 * @return the JFrame of the GUI.
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * The getter of the game board ArrayList.
	 * 
	 * @return the ArrayList with 9 JLabel, which is the game board.
	 */
	public ArrayList<JLabel> getGameBoard(){
		return this.gameBoard;
	}
	
	/**
	 * The getter of the submit button.
	 * 
	 * @return the submitBtn, a JButton.
	 */
	public JButton getSubmitBtn() {
		return this.submitBtn;
	}
	
	/**
	 * The getter of the exit button.
	 * 
	 * @return the exitBtn, a JMenuItem.
	 */
	public JMenuItem getExitBtn() {
		return this.exitBtn;
	}
	
	/**
	 * The getter of the text field.
	 * 
	 * @return the JTextField for entering the player's name.
	 */
	public JTextField getTextField() {
		return this.textField;
	}
	
	/**
	 * The getter of the announce label.
	 * 
	 * @return the announceLabel, which is a label for doing announcement to the players.
	 */
	public JLabel getAnnounceLabel() {
		return this.announceLabel;
	}
	
	/**
	 * This class implements the actionListener for the instructionBtn, a JMenuItem.
	 * 
	 * @author Chau Cheuk Him
	 *
	 */
	class instructionListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			String msg = "Some information about the game:\n"
							+ "Criteria for a valid move:\n"
							+ "-The move is not occupied by any mark.\n"
							+ "-The move is made in the player's turn.\n"
							+ "-The move is made within the 3 x 3 board.\n"
							+ "The game would continue and switch among the opposite player until it reaches either one of the following conditions:\n"
							+ "-Player 1 wins.\n"
							+ "-Player 2 wins\n"
							+ "-Draw.";
			JOptionPane.showMessageDialog(frame, msg);
		}
	}
}
