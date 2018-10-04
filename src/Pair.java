import java.util.ArrayList;
/**
 * Subclass of the Hand class, and is used to model a hand of Pair.
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class Pair extends Hand {

	/**
	 * Constructor for building a hand of Pair
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
		}
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the Pair object by 
	 * checking if 2 cards are in the list, then
	 * check if their ranks are the same. If same return true, else false.
	 * 
	 * @return a boolean value that shows if the hand is a Pair or not.
	 * 
	 */
	public boolean isValid() {
		//check for size=2, then check if they have same rank.
		if(this.size()==2) {
			if(this.getCard(0).getRank()==this.getCard(1).getRank()) {
			return true;
			}
			else 
			{return false;}
		}
		else {
		return false;
		}
	}
	/**
	 *  public method getType that overrides inherited abstract method from hand class.
	 *  
	 *  Returns a String variable according to the name of the class. 
	 *  
	 *  @return a string value (for Pair class "Pair")
	 * 
	 */
	public String getType() {
		return "Pair";
	}
	
	
	
	
}
