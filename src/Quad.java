import java.util.ArrayList;

/**
 * Subclass of the Hand class, and is used to model a hand of Quad.
 * 
 * 
 * Overrides isValid and getType method appropriately.
 * 
 * @author hataemin
 *
 */
public class Quad extends Hand{
	/**
	 * Constructor for building a hand of Quad
	 * with specified player and card lists.
	 * 
	 * @param player
	 * 				the player who plays Hand
	 * @param cards
	 * 				card list played by the player
	 */
	public Quad(CardGamePlayer player, CardList cards) {
		super(player, cards);
		}
	/**
	 * public method isValid overrides the inherited abstract method from hand class
	 * 
	 * Checks for validity of the Quad object by 
	 * sorting out the card lists of hand and checking if 4 same rank cards are in the list.
	 * 
	 * @return a boolean value that shows if the hand is a quad or not.
	 * 
	 */
	public boolean isValid() {
		this.sort();
		boolean truth=true;
		
		if(this.size()==5) {
			//check for 0 1 index cards being same.
			if(this.getCard(0).getRank()==this.getCard(1).getRank()) {
				for(int i=0;i<4;i++) {
					if(this.getCard(0).getRank()!=this.getCard(i).getRank()) {
						truth=false;
					}
				}
			if(truth) {
			return truth;
			}
			else 
			{return truth;}
		}
			//Check for case 3,4 index cards being same.
		else if(this.getCard(3).getRank()==this.getCard(4).getRank()){
			for(int i=4;i>0;i--) {
				if(this.getCard(4).getRank()!=this.getCard(i).getRank()) {
					truth=false;
				}
			}
		if(truth) {
		return truth;
		}
		else 
		{return truth;}
		}
			
	}
		else {
			truth=false;
			return false;
		}
		
		return false;
			
		
	}
	
	/**
	 *  public method getType that overrides inherited abstract method from hand class.
	 *  
	 *  Returns a String variable according to the name of the class. 
	 *  
	 *  @return a string value (for Quad class "Quad")
	 * 
	 */
	public String getType() {
		return "Quad";
	}
	
	

}
