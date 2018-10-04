import java.util.ArrayList;
/**
 * Subclass of the Hand class, and is used to model a hand of StraightFlush.
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class StraightFlush extends Hand {

	/**
	 * Constructor for building a hand of StraightFlush
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public StraightFlush(CardGamePlayer player, CardList cards) {
		super(player, cards);
		}
	
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the StraightFlush object by 
	 * sorting out the card lists of hand and checking if 5 cards are in ascending order with difference one,
	 * and checking if they all have same suit.
	 * 
	 * Give ace and two +13 here to check for straights with aces and twos.
	 * 
	 * @return a boolean value that shows if the hand is a StraightFlush or not.
	 * 
	 */
	public boolean isValid() {
					
		this.sort();
		boolean truth=true;
		
		if(this.size()==5){
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
			//Compare suits
			if(this.getCard(i).getSuit()!=this.getCard(i+1).getSuit()) {
						truth=false;
					}
				}
				return truth;
			}
		else return false;
	}
	
	/**
	 *  public method getType that overrides inherited abstract method from hand class.
	 *  
	 *  Returns a String variable according to the name of the class. 
	 *  
	 *  @return a string value (for StraightFlush class "StraightFlush")
	 * 
	 */
	public String getType() {
		return "StraightFlush";
	}

}