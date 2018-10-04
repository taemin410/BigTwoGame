import java.util.ArrayList;
/**
 * Subclass of the Hand class, and is used to model a hand of Single.
 * 
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class Single extends Hand {
	/**
	 * Constructor for building a hand of Single
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);	    
	}
	
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the Single object by 
	 * Checking whether the size of the hand is one.
	 * 
	 * @return a boolean value that shows if the hand is a single or not.
	 * 
	 */	
	public boolean isValid() {
		//Check if hand has size 1.
		if(this.size()==1) {
			return true;
		}
		else
		return false;
	}
	
	/**
	 *  public method getType that overrides inherited abstract method from hand class.
	 *  
	 *  Returns a String variable according to the name of the class. 
	 *  
	 *  @return a string value (for Single class "Single")
	 * 
	 */
	public String getType() {
		return "Single";
	}
	

}