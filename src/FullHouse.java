import java.util.ArrayList;
/**
 * Subclass of the Hand class, and is used to model a hand of FullHouse.
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class FullHouse extends Hand {

	/**
	 * Constructor for building a hand of FullHouse
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
		}
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the FullHouse by 
	 * sorting out the card lists of hand and checking if 5 cards are in the list.
	 * 
	 * Compare cards in the middle positioned at 1 and 2. If same compare the structure of 3 same card and 2 same card order.
	 * If different, the FullHouse is in structure of 2 same card and 3 same card order. Check for three cards being same rank and
	 * two cards having same rank. If true, return true, else return false.
	 * 
	 * @return a boolean value that shows if the hand is a FullHouse or not.
	 * 
	 */
	public boolean isValid() {
		this.sort();
		boolean truth=true;
		
		if(this.size()==5) {
			//Check for structure of FullHouse (3,2) or (2,3)
			if(this.getCard(1).getRank()==this.getCard(2).getRank()) {
				for(int i=0;i<3;i++) {
					if(this.getCard(0).getRank()!=this.getCard(i).getRank()) {
						truth=false;
					}
				}
				for(int j=4;j>2;j--) {
					if(this.getCard(4).getRank()!=this.getCard(j).getRank()) {
						truth=false;
					}
				}
				//Case for (2,3)
		}else if(this.getCard(1).getRank()!=this.getCard(2).getRank()) {
				for(int i=0;i<2;i++) {
					if(this.getCard(0).getRank()!=this.getCard(i).getRank()) {
						truth=false;
					}
				}
				for(int j=4;j>1;j--) {
					if(this.getCard(4).getRank()!=this.getCard(j).getRank()) {
						truth=false;
					}
				}
			}
			return truth;
			
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
	 *  @return a string value (for FullHouse class "FullHouse")
	 * 
	 */
	public String getType() {
		return "FullHouse";
	}
	
}
