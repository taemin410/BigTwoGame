import java.util.ArrayList;
/**
 * Public abstract class Hand is a subclass of CardList
 * Used to model a hand of cards.
 * 
 * Has Private instance variable for storing the player who plays this hand and for Hand value that helps compare hand types.
 * 
 * methods for getting the player of this hand, checking if it is a valid hand, getting the type of this hand, getting
   the top card of this hand, and checking if it beats a specified hand.
 * method for getting the value of cards that help comparing beats.
 * 
 * 
 * @author hataemin
 *
 */
public abstract class Hand extends CardList {

	private CardGamePlayer player;
	private ArrayList<Hand> value = new ArrayList<Hand>();
	/**
	 * constructor for building a hand with
	 * the specified player and list of cards
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player=player;
		for(int i= 0; i < cards.size();i++) {
	    this.addCard(cards.getCard(i));
		}
	    
	}
	/**
	 * A method for retrieving the player of this hand.
	 * 
	 * @return player of the hand 
	 */
	public CardGamePlayer getPlayer() {
		return player;
	}
	/**
	 *  A method for retrieving the top card of the hand.
	 *  
	 *  Sorts Cards in hand, and retrieve the Card at the end.
	 *  For FullHouse and Quad apply different approach.
	 *  
	 * @return Card that is Top Card of the Hand.
	 */
	public Card getTopCard() {
		this.sort();
		//Set exceptions for Full House getting Top card
		if(this.getType()=="FullHouse") {
			if(this.getCard(1).getRank()==this.getCard(2).getRank()) {
				return this.getCard(2);
			}
			else {
				return this.getCard(4);
			}
		}
		//Set exceptions in Quad for top card
		if(this.getType()=="Quad") {
			if(this.getCard(0).getRank()==this.getCard(1).getRank()) {
				return this.getCard(3);
			}
			else {
				return this.getCard(4);
			}
		} 
		else {
			//Card at the end being top card
			return this.getCard(this.size()-1);
		}
		
	}
	/**
	 * A method for checking if this hand beats a specified hand.
	 * 
	 * Checks by comparing hand size, hand type and hand TopCard,
	 * then returns a boolean value for whether it beats or not. 
	 * 
	 * @param hand 
	 * 				hand that is being compared.
	 * @return a boolean type variable according to the result. 
	 */
	public boolean beats(Hand hand) {
		//Compare size of hand
		if(this.size()==hand.size()) {
			//Compare type
			if(this.getType()==hand.getType()) {
				//Compare Top card
			if(this.getTopCard().compareTo(hand.getTopCard())>0){
				return true;
			}	
			else if(this.getTopCard().compareTo(hand.getTopCard())==0) {
				if(this.getTopCard().getSuit()>hand.getTopCard().getSuit()) {
					return true;
				}
			}
			else {
				return false;
			}
		}
			//Compare different type and their order using getValue.
			else if(this.getValue() > hand.getValue()) {
				return true;
			}
			else if(this.getValue() < hand.getValue()) {
				return false;
			}
		}
		else {
			return false;
		}
		return false;
	}
	/**
	 * A method for getting the value of cards that help comparing.
	 * 
	 * Give value for hands that is of size 5.
	 * Since they have same number of cards, give each of them a integer value according to their order.
	 * 
	 * @return a integer value specific to their hand type.
	 */
	public int getValue() {
		int value=0;
		//Give 5 cards hand types values to assist comparison in order.
		if(this.getType()=="StraightFlush") {
			return value+4;
		}
		else if(this.getType()=="Quad") {
			return value+3;
		}
		else if(this.getType()=="FullHouse") {
			return value+2;
		}
		else if(this.getType()=="Flush") {
			return value+1;
		}
		else
		return value;
	}
	
	/**
	 * An abstract method for checking if this is a valid hand.
	 *
	 * Meant to be overriden in its subclasses.
	 * 
	 * @return boolean value whether the hand is valid or not.
	 */
	public abstract boolean isValid();
	/**
	 * An abstract method for retrieving Hand Type.
	 * 
	 * Meant to be overriden in its subclasses.
	 * 
	 * @return string value of the Hand Type name.
	 */
	public abstract String getType();
	
}
