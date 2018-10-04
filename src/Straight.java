import java.util.ArrayList;
/**
 * Subclass of the Hand class, and is used to model a hand of Straight.
 * 
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class Straight extends Hand {
	/**
	 * Constructor for building a hand of Straight
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
		}
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the Straight object by 
	 * sorting out the card lists of hand and checking if 5 cards are in ascending order with difference one.
	 * 
	 * Give ace and two +13 here to check for straights with aces and twos.
	 * 
	 * @return a boolean value that shows if the hand is a Straight or not.
	 * 
	 */
	public boolean isValid() {
		this.sort();
		boolean truth=true;
		
		if(this.size()==5) {
			for(int i=0;i<4;i++) {
				int now=this.getCard(i).getRank();
				int next=this.getCard(i+1).getRank();
				
				//Let Ace or Two be +13 in value
				if(now==0||now==1) {
					now+=13;
				}
				if(next==0||next==1) {
					next+=13;
				}
				if(now!=next-1) {
							truth=false;
						}
				}
			if(truth) {
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
	 *  @return a string value (for Straight class "Straight")
	 * 
	 */
	public String getType() {
		return "Straight";
	}
	
}
