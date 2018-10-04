import java.util.*;
/**	
 * BigTwo class 
 * 
 * BigTwo class implements BigTwo game.
 * 
 * Stores CardGamePlayer, Hand in a Arraylist which necessitate Hand and CardGamePlayer class.
 * Uses BigTwoTable to show game interface.
 * Calls BigTwoDeck which is made of BigTwoCards to make a Deck for BigTwoGame.
 * Using start method, plays the BigTwo card Game, with BigTwoTable on screen.
 * 
 * @author hataemin
 *
 */
public class BigTwo implements CardGame{
	/**
	 *	Constructor for BigTwo class
	 *
	 *	No parameters ();
	 *
	 *	make new arraylists of Card Game Player and hand.
	 *  create and put 4 players in the playerlist(arraylist) for CardGamePlayer
	 *	make new BigTwoTable for user interface.
	 */
	public BigTwo() {
		
		playerList = new ArrayList<CardGamePlayer>();
		handsOnTable=new ArrayList<Hand>();
		
		//make four players
		CardGamePlayer first = new CardGamePlayer();
		CardGamePlayer second = new CardGamePlayer();
		CardGamePlayer third = new CardGamePlayer();
		CardGamePlayer fourth = new CardGamePlayer();
		
		//add four players in the list
		this.playerList.add(first);
		this.playerList.add(second);
		this.playerList.add(third);
		this.playerList.add(fourth);	
		
		//make new 
		table = new BigTwoTable(this);	
	}
	
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int currentIdx;
	/**
	 * getNumOfPlayers() is a method for retrieving the the number of players.
	 * 
	 * @return number of players
	 */
	private BigTwoTable table;
	
	public int getNumOfPlayers(){
		return playerList.size();
	}
	/**
	 *
	 * getDeck() is a method for retrieving the deck of cards being used.
	 * 
	 * @return deck of the big two class object.
	 */
	public Deck getDeck() {
		return deck;
	}
	/**
	 *  getPlayerList() is a method that returns a list of players.
	 *  
	 * @return playerList - Arraylist of Card game players
	 */
	public ArrayList<CardGamePlayer> getPlayerList(){
		return playerList;
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
		return currentIdx;
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
		
		table.setActivePlayer(currentIdx);
		//table.repaint();
		
	}
	
	/**
	 *  makeMove method makes move, by using checkMove Method.
	 *  
	 * @param playerID
	 * 					player who is making the move
	 * @param cardIdx
	 * 					player's played card index integer array.
	 * 				
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		checkMove(playerID, cardIdx);
		
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
	}else if(0!=playerList.get(playerID).getNumOfCards()) {	
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
							table.printMsg(hand.getPlayer().getName());
							table.printMsg(hand.getType()+" ");
							table.printMsg(hand.toString());
							playerList.get(currentIdx).removeCards(tobeplayed);
							handsOnTable.remove(0);
							next=true;
						}
						//when other player plays valid hand that beats one on the table
						else if(hand!=null && hand.beats(handsOnTable.get(0))){
							handsOnTable.add(hand);
							table.printMsg(hand.getPlayer().getName());
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
						table.printMsg(playerList.get(playerID).getName());
						table.printMsg("{pass}");
						next=true;
					}
				}
			}
		
		if(endOfGame()) {
			table.disable();
			legalmove=false;
			table.printMsg("Game ends");
			//print cards left and Game winner 
			for(int i=0; i<4; i++) {
				if(currentIdx==i) {
					table.printMsg(playerList.get(currentIdx).getName()+" wins the game.");
				}
				else {
					table.printMsg(playerList.get(i).getName()+" has "+playerList.get(i).getNumOfCards()+" cards in hand.");
				}
			}
		}
		
		if(legalmove==true) {
			currentIdx++;
			if(currentIdx>3) {
				currentIdx=0;
			}
			table.setActivePlayer(currentIdx);
		}
		
		//table.setActivePlayer(currentIdx);
		table.resetSelected();
		table.repaint();
		
		
	}
	
	/**
	 *  Method to check if the game is over.
	 *  
	 *  if current player's hand is empty, return true.
	 *  else return false;
	 *  
	 */
	public boolean endOfGame() {
		if(playerList.get(currentIdx).getNumOfCards()==0){
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 *  public static void main.
	 *  
	 *  Main method initializing new BigTwo Game.
	 *  Initializes new big Two card deck, shuffles them, use start method to start the game with giving bigtwo deck as parameter.
	 * 
	 * @param args
	 *				not used in this program.
	 */
	public static void main(String[] args) {
		BigTwo BigTwoGame = new BigTwo();
		BigTwoDeck bigtwodeck = new BigTwoDeck();
		bigtwodeck.shuffle();
		BigTwoGame.start(bigtwodeck);
		
		
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
		//make a hand object which is an object from super class Hand
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


