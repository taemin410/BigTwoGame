import java.util.ArrayList;
/**
 * Subclass of the Hand class, and is used to model a hand of Flush.
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class Flush extends Hand {
	/**
	 * Constructor for building a hand of Flush
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the Flush object by 
	 * sorting out the card lists of hand and checking if 5 cards are there.
	 * and checking if they all have same suit.
	 * 
	 * @return a boolean value that shows if the hand is a Flush or not.
	 * 
	 */
	public boolean isValid() {
		this.sort();
		boolean truth=true;
		//Check if the size is five.
		if(this.size()==5) {
			//Check for all suits being same.
			if(this.getCard(0).getSuit()==this.getCard(1).getSuit()) {
				for(int i=0;i<5;i++) {
					if(this.getCard(0).getSuit()!=this.getCard(i).getSuit()) {
						truth=false;
					}
				}
			if(truth) {
			return truth;
			}
			else 
			{
				return truth;
			}
		}
			else 
			{
				return false;
			}
			
	} //else of size if
		else {
			return false;
		}			
		
	}
	/**
	 *  public method getType that overrides inherited abstract method from hand class.
	 *  
	 *  Returns a String variable according to the name of the class. 
	 *  
	 *  @return a string value (for Flush class "Flush")
	 * 
	 */
	public String getType() {
		return "Flush";
	}
}
