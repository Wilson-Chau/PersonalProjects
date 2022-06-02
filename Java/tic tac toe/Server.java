import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class implements the server of the Tic Tac Toe game.
 * 
 * @author Chau Cheuk Him
 *
 */
public class Server {
	ServerSocket serverSock;
	Controller controller;
	ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
	
	/**
	 * No-argument constructor of the Server class.
	 */
	public Server() {
		controller = new Controller();
	}
	
	/**
	 * The main method of the Server class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server();
		server.go();
	}
	
	/**
	 * This method indicates the flow of the Server class.
	 */
	public void go() {	
		try {
			serverSock = new ServerSocket(5000);
			System.out.println("Server is running...");
			
			while (true) {
				Socket sock = serverSock.accept();
				System.out.println("Server is connected to the client");
				
				ClientHandler clientHandler = new ClientHandler(sock);
				Thread clientThread = new Thread(clientHandler);
				clientThread.start();
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This class implements the Runnable object.
	 * 
	 * @author Chau Cheuk Him
	 *
	 */
	class ClientHandler implements Runnable{
		private Socket sock;
		
		public ClientHandler(Socket sock) {
			this.sock = sock;
		}
		
		public void run() {			
			try {
				PrintWriter writer = new PrintWriter(sock.getOutputStream(), true);
				writers.add(writer);
				
				InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
				BufferedReader reader = new BufferedReader(streamReader);
				
				//read the command
				String command;
				while ((command = reader.readLine()) != null) {
					System.out.println("Command from client: " + command);
					
					if (command.startsWith("createPlayer")) {
						controller.addPlayer();
						
						System.out.println(controller.getNumberOfPlayer());
					}
					else if (command.startsWith("GameOver")) {
						if (!controller.isGameOver()) {
							controller.endGame();
							
							if (controller.getRound() % 2 == 1) {
								writers.get(0).println("Lost");
								writers.get(1).println("Win");
							}
							else {
								writers.get(0).println("Win");
								writers.get(1).println("Lost");
							}
						}
					}
					else if (command.startsWith("Draw")) {						
						if (!controller.isGameOver()) {
							controller.endGame();
							
							writers.get(0).println("Draw");
							writers.get(1).println("Draw");
						}
						
					}
					else if (command.startsWith("Exit")) {						
						if (!controller.isGameOver()) {
							for (PrintWriter w : writers) {
								w.println("Exit");
							}
						}

						controller.reset();
						writers = new ArrayList<PrintWriter>();
					}
					else {
						command = command.replaceAll("\\s+","") + " " + controller.getSymbol() + " " + controller.getRound();
						System.out.println("Server is broadcasting: " + command);
						
						for (PrintWriter w : writers) {
							w.println(command);
						}
						
						controller.changeSymbol();
						controller.incRound();
						
						if (controller.getRound() % 2 == 0) {    //even rounds
							//enable and disable corresponding game board
							writers.get(0).println("Disable");
							writers.get(1).println("Enable");
							
							//do announcement to each player
							writers.get(0).println("ValidMove");
							writers.get(1).println("YourTurn");
						}
						else {                                   //odd rounds
							//enable and disable corresponding game board
							writers.get(0).println("Enable");
							writers.get(1).println("Disable");
							
							//do announcement to each player
							writers.get(0).println("YourTurn");
							writers.get(1).println("ValidMove");
						}
					}
					
					//start the game if not yet started
					if (controller.getNumberOfPlayer() == 2 && !controller.isGameStart() && controller.isNewGame()) {
						controller.startGame();
						
						for (PrintWriter w : writers) {
							w.println("Start");
						}
						
						System.out.println("Server is sending: Start");
						writers.get(0).println("Enable");
						writers.get(1).println("Disable");
					}
				}
			}

			catch(Exception ex) {
				ex.printStackTrace();
			}			
		}
	}
}