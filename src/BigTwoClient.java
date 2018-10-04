import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.lang.*;

/**
 * The BigTwoClient class implements the CardGame interface and NetworkGame interface. 
 * It is used to model a Big Two card game that supports 4 players playing over the internet.
 * This program is a client that connects to the Game Server to play the big two game with 4 players.
 * Handles messages coming from the server.
 * 
 * @author hataemin
 *
 */
public class BigTwoClient implements CardGame, NetworkGame{
	
/**
 * 	a constructor for creating a Big Two client. 
 *  create 4 players and add them to the list of players;
 *  create a Big Two table which builds the GUI for the game and handles user actions;
 *  make a connection to the game server by calling the makeConnection() method from the NetworkGame interface.
 */
	public BigTwoClient(){
		
		playerList = new ArrayList<CardGamePlayer>();
		handsOnTable=new ArrayList<Hand>();
		
		CardGamePlayer a = new CardGamePlayer();
		CardGamePlayer b = new CardGamePlayer();
		CardGamePlayer c = new CardGamePlayer();
		CardGamePlayer d = new CardGamePlayer();
		playerList.add(a);
		playerList.add(b);
		playerList.add(c);
		playerList.add(d);
		
		
		table = new BigTwoTable(this);
		//table.enable();
		makeConnection();
		
		
	}
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;	
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;
	
	/**
	 * getNumOfPlayers() is a method for retrieving the the number of players.
	 * 
	 * @return number of players
	 */
	public int getNumOfPlayers() {
		return this.numOfPlayers;
	}
	/**
	 *
	 * getDeck() is a method for retrieving the deck of cards being used.
	 * 
	 * @return deck being used
	 */
	public Deck getDeck() {
		return this.deck;
	}
	/**
	 *  getPlayerList() is a method that returns a list of players.
	 *  
	 * @return playerList - Arraylist of Card game players
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return this.playerList;
	}
	/**
	 * 	getHandsOnTable() returns handsOnTable for BigTwo Class.
	 * 
	 * @return handsOnTable - Arraylist of handsOnTable(hand played on the table)
	 */
	public ArrayList<Hand> getHandsOnTable(){
		return this.handsOnTable;
	}
	/**
	 *  getCurrentIdx() method returns index of the current player.
	 * 
	 * @return currentIdx (current index of the player)
	 */
	public int getCurrentIdx() {
		return this.currentIdx;
	}
	/**
	 *  Starts the game with given deck.
	 * 
	 *  Divides deck to four players
	 *  Make 3 of Diamond holder first player.
	 *    
	 *  
	 * @param deck 
	 * 				deck of cards used to play.
	 */
	public void start(Deck deck) {			
			handsOnTable.clear();

			for(int i = 0; i<4 ; i++) {
				playerList.get(i).removeAllCards();
			}
			
			for(int i=0;i<4;i++) {
				for(int a=0;a<13;a++) {
					playerList.get(i).addCard(deck.getCard(0));
					deck.removeCard(0);
				}
				playerList.get(i).sortCardsInHand();
			}
			
			//Make diamond 3 player the first player
			BigTwoCard card=new BigTwoCard(0,2);
			currentIdx=0;
			for(int p=0;p<4 ; p++) {
				CardList a=this.playerList.get(p).getCardsInHand();
				if(a.contains(card)) {
					currentIdx=p;
				}
			}

			table.printMsg(playerList.get(currentIdx).getName() + "'s turn:\n");
			//table.repaint();
			
		
	}
	/**
	 *  makeMove method makes move, by using sending message move to server
	 *  Handle exception.
	 *  
	 * @param playerID
	 * 					player who is making the move
	 * @param cardIdx
	 * 					player's played card index integer array.
	 * 				
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		try {
			CardGameMessage cgm=new CardGameMessage(6, -1, cardIdx);
			sendMessage(cgm);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 	checkMove method checks the validity of the played card by player.
	 * 	
	 *  It implements Big Two card game logic .
	 *  If current player has no card in hand, ends the game.
	 *  Handle cases for pass, non-legal move, and legal hand plays.
	 *  After each play, repaints the table, resets the selected cards. 
	 *  
	 *  Handle first player play.
	 *  
	 *  Ends the game and print number of cards left and the game winner. 
	 * 
	 * @param playerID
	 * 					player who is making the move
	 * @param cardIdx
	 * 					player's played card index integer array. 
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		currentIdx=playerID;
		Card card = new Card(0,2);
		CardList tobeplayed = playerList.get(playerID).play(cardIdx);
		
		boolean legalmove=true;
		
		//first player's turn
		if(handsOnTable.isEmpty()==true) {
		if(!tobeplayed.contains(card)) {
			table.printMsg("Not a Legal Move!");
			legalmove=false;
		}
		else{
		Hand hand= composeHand(playerList.get(currentIdx),tobeplayed);
		if(hand!=null) {
			playerList.get(currentIdx).removeCards(tobeplayed);
			handsOnTable.add(hand);
			table.printMsg(hand.getPlayer().getName());
			table.printMsg(hand.getType()+" ");
			table.printMsg(hand.toString());
		}
		//if not valid hand
		else if(hand==null) {
				table.printMsg("Not a Legal Move!");
				legalmove=false;
			}
		}	
	}else if(0!=playerList.get(currentIdx).getNumOfCards()) {	
		    		boolean next;
				//table.repaint();
				next=false;
				if(next==false) {

					tobeplayed = playerList.get(currentIdx).play(cardIdx);

					//handle program when there is input
					if(tobeplayed!=null) {
						Hand hand = composeHand(playerList.get(currentIdx),tobeplayed);

						//if current player and table player coincide 
						//thus, allow player to play new card of hand
						if(hand!=null&&handsOnTable.get(0).getPlayer()==playerList.get(currentIdx)){
							handsOnTable.add(hand);
							table.printMsg(hand.getPlayer().getName()+" plays");
							table.printMsg(hand.getType()+" ");
							table.printMsg(hand.toString());
							playerList.get(currentIdx).removeCards(tobeplayed);
							handsOnTable.remove(0);
							next=true;
						}
						//when other player plays valid hand that beats one on the table
						else if(hand!=null && hand.beats(handsOnTable.get(0))){
							handsOnTable.add(hand);
							table.printMsg(hand.getPlayer().getName()+" plays");
							table.printMsg(hand.getType()+" ");
							table.printMsg(hand.toString());
							playerList.get(currentIdx).removeCards(tobeplayed);
							handsOnTable.remove(0);
							next=true;
						}
						//hand not able to beat hands on table
						else {
							table.printMsg("Not a legal move!");
							legalmove=false;
						}
					}
					//handle program when there no input and the current player and hands on table both have same player name
					else if(handsOnTable.get(0).getPlayer()==playerList.get(currentIdx)) {
						table.printMsg("Not a legal move!");
						legalmove=false;
					}
					// pass if current player has not played on table
					else {
						table.printMsg(playerList.get(playerID).getName()+" plays");
						table.printMsg("{pass}");
						next=true;
					}
				}
			}
		
		if(endOfGame()) {
			table.disable();
			legalmove=false;
			table.printMsg("Game ends");
			String result="Game ends \n";
			//print cards left and Game winner 
			for(int i=0; i<4; i++) {
				if(currentIdx==i) {
					table.printMsg(playerList.get(currentIdx).getName()+" wins the game.");
					result+=playerList.get(currentIdx).getName()+" wins the game. \n";
				}
				else {
					table.printMsg(playerList.get(i).getName()+" has "+playerList.get(i).getNumOfCards()+" cards in hand.");
					result+=playerList.get(i).getName()+" has "+playerList.get(i).getNumOfCards()+" cards in hand. \n";
				}
			}
			//show dialog for Game ending, results 
			JOptionPane.showMessageDialog(null, result, "Game Over!", JOptionPane.PLAIN_MESSAGE);
			//when clicking ok, send ready message to server for that client.
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			
		}
		
		if(legalmove==true) {
			currentIdx++;
			if(currentIdx>3) {
				currentIdx=0;
			}
		}
		else {
			//table.setActivePlayer(playerID);
			table.enable();
		}
		
		table.resetSelected();
		table.repaint();
		
	}
	/**
	 * boolean method returning whether the game is over or not
	 * 
	 * @return endofGame true if game ends
	 */
	public boolean endOfGame() {
		if(this.playerList.get(currentIdx).getNumOfCards()==0){
			return true;
		}
		else {
			return false;
		}
	}
	
	//networkGame interfaces
	/**
	 * getPlayerID method retrieves the playerID of the Client
	 * @return playerID
	 */
	public int getPlayerID() {
		return this.playerID;
	}
	/**
	 * setPlayerID method sets playerID with given parameter
	 * 
	 * @param integer value specifying player id
	 */
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
	}
	/**
	 * getPlayerName method retrieves the playerName of the Client
	 * @return playerName
	 */
	public String getPlayerName() {
		return this.playerName;
	}
	
	/**
	 * setPlayerName method sets PlayerName with given parameter
	 * 
	 * @param String value specifying playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName=playerName;
	}
	/**
	 * getServerIP() method retrieves the ServerIP of the Client
	 * @return ServerIP
	 */
	public String getServerIP() {
		return this.serverIP;
	}
	/**
	 * setServerIP method sets ServerIP with given parameter
	 * 
	 * @param String value specifying serverIP
	 */
	public void setServerIP(String serverIP) {
		this.serverIP=serverIP;
	}
	/**
	 * getServerPort() method retrieves the ServerPort of the Client
	 * @return ServerPort
	 */
	public int getServerPort() {
		return this.serverPort;
	}
	/**
	 * setServerPort method sets serverPort with given parameter
	 * 
	 * @param integer value specifying serverPort
	 */
	public void setServerPort(int serverPort) {
		this.serverPort=serverPort;
	}
	/**
	 * makeConnection method creates an initial connection from client to server.
	 * 
	 * For this program initializes IP as 127.0.0.1 and serverport 2396.
	 * Creates socket with given serverIP and port.
	 * Creates an objectoutputstream to a socket.
	 * Creates a thread for serverhandler which handles receiving message
	 * starts thread
	 * 
	 *  send initial message of join and ready to server 
	 * 
	 */
	public void makeConnection() {
		try {
		
		sock = new Socket("127.0.0.1", 2396);
		oos = new ObjectOutputStream(sock.getOutputStream());
		Thread thread = new Thread(new ServerHandler());
		thread.start();
		//join
		sendMessage(new CardGameMessage(1, -1, this.getPlayerName()));
		//ready
		sendMessage(new CardGameMessage(4, -1, null));
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	/**
	 * a method for parsing the messages received from the game server. 
	 * Based on the message type, different actions will be carried out.
	 * 
	 * parse the type of message first and carry out client action to it.
	 * 
	 * @param GameMessage
	 */
	public void parseMessage(GameMessage message) {
		
		if(message.getType()==0) {//playerList
			setPlayerID(message.getPlayerID());
			table.setActivePlayer(message.getPlayerID());
			String[] names = (String[])message.getData();
			for(int i=0; i<4; i++) {
				playerList.get(i).setName(names[i]);
			}
			table.repaint();

		}
		
		if(message.getType()==1) {//join
			playerList.get(message.getPlayerID()).setName((String)message.getData());
			table.repaint();
		}
		
		if(message.getType()==2) {//Full
			table.printMsg("FULL cannot join");
		}
		
		if(message.getType()==3) {//quit
			table.printMsg("Player "+message.getPlayerID()+" left the game");
			playerList.get(message.getPlayerID()).setName(null);
			//stops the game when someone leaves
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			table.repaint();
		}
		
		if(message.getType()==4) {//ready
			table.printMsg("Player "+ message.getPlayerID()+ " " + playerList.get(message.getPlayerID()).getName() +" is ready.");
			table.repaint();
		}
		
		if(message.getType()==5) {//start
			table.reset();
			start((BigTwoDeck)message.getData());
			table.printMsg("All players are ready. Game Starts");
			table.repaint();
		}
		
		if(message.getType()==6) {//move
			 checkMove(message.getPlayerID(), (int[])message.getData());
		}
		
		if(message.getType()==7) {//msg
			table.printChatMsg(message.getData().toString() + "\n");
			//chatbox.display();
		}
		
	}
	/**
	 * Send message method 
	 * 
	 * sends message to server by object output stream. 
	 * 
	 * @param message - Game message parameter given to sending message method.
	 * 
	 */
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
			oos.flush();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 
	 * inner class Server Handlerer 
	 * handles message received from server.
	 * implements Runnable interface
	 * 
	 * use objectInputStream to receive messgae 
	 * 
	 * @author hataemin
	 *
	 */
	public class ServerHandler implements Runnable{
		
		ObjectInputStream ois;
		/**
		 * run() method from interface Runnable overriden.
		 * use try catch to handle exception 
		 * create a Objectinputstream and receive message and parse message using parsemessage method.
		 * 
		 */
		public void run() {
			CardGameMessage message;
			try {
				ois = new ObjectInputStream(sock.getInputStream());

				// waits for messages from the server
				while (true) {
				message=(CardGameMessage)ois.readObject();
					parseMessage(message);
				} // close while
			} catch (Exception ex) {
				System.out.println("Error in receiving messages from the client at ");
				ex.printStackTrace();
			}			
		}		
		
	}
	/**
	 * static void main method for making a object for bigTwoclient.
	 * Running this game.
	 * 
	 * @param args not used in this program.
	 */
	public static void main(String[] args) {
		BigTwoClient btc = new BigTwoClient();
	}
	
	/**
	 * Public static composeHand method
	 * Uses polymorphism of Hand Class and its subclasses to check for valid hand played by the player.
	 * Checks for every subclasses' isValid method using hand reference type variable pointing to subclass objects.
	 * Returns valid hand or null if there is none.
	 * 
	 * @param player
	 * 				CardGamePlayer who is composing hand.
	 * @param cards
	 * 				Specific list of cards from the given player.
	 * 
	 * @return valid hand from the specific list of card player. returns 
	 * 		   null if no valid hand can be composed from the card list. 
	 */
	public static Hand composeHand(CardGamePlayer player, CardList cards) {
		Hand polymorph;
		
		//Test for subclass objects 
		
		//StraightFlush must be ordered first since Cards that pass in StraightFlush also pass in Straight or Flush.
		polymorph = new StraightFlush(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}
		
		//Test for valid single
		polymorph = new Single(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}
		
		//Test for valid Pair
		polymorph = new Pair(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}
		
		//Test for valid Triple
		polymorph = new Triple(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}

		//Test for valid Straight
		polymorph = new Straight(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}
		
		//Test for valid Flush
		polymorph = new Flush(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}
				
		//Test for valid FullHouse
		polymorph = new FullHouse(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}
		
		//Test for valid Quad
		polymorph = new Quad(player, cards);
		if(polymorph.isValid()) {
			return polymorph;
		}
		
		//returns null if no hand could be formed with given cardlist.
		return null;
		
		
	}
}
