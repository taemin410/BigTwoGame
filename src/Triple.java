import java.util.ArrayList;
/**
 * Subclass of the Hand class, and is used to model a hand of Triple.
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class Triple extends Hand{
	/**
	 * Constructor for building a hand of Triple
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the Triple object by 
	 * Checking if 3 cards are in the list, then
	 * check if their ranks are the same. If same return true, else false.
	 * 
	 * @return a boolean value that shows if the hand is a Triple or not.
	 * 
	 */
	public boolean isValid() {
		//Check for size=3, then check if their ranks are same.
		if(this.size()==3) {
			if(this.getCard(0).getRank()==this.getCard(1).getRank()) {
				if(this.getCard(1).getRank()==this.getCard(2).getRank()){
					return true;
					}
				else {
					return false;
				}
			}
			else {
				return false;
			}
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
	 *  @return a string value (for Triple class "Triple")
	 * 
	 */
	public String getType() {
		return "Triple";
	}
	
}
