/**
 *  BigTwoCard class is a subclass of Card class. 
 *  Used to model cards being used in a Big Two card game.
 *  
 *  Overrides the compareTo() method it inherited from the Card class to
	reflect the ordering of cards used in a Big Two card game. (Comparing Rank of Ace and two)
 * 
 * 	
 * @author hataemin
 *
 */
public class BigTwoCard extends Card {
	
	/**
	 * a constructor for building a card with the specified
		suit and rank. suit is an integer between 0 and 3, and rank is an integer between 0 and 12.
	 * @param suit
	 * 				suit of a Card (Diamond, Club, Heart, Spade) in order. 0 - 3
	 * 			  <p>
	 *            0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
	 * @param rank
	 * 				Rank of a Card from 0 - 12 ( ace to K )
	 * 				<p>
	 *        		    0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J', 11
	 *        		    = 'Q', 12 = 'K'
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
		
	}
	
	/**
	 *  Public method compareTo is a method for comparing this card with the specified card for
		order. Returns a negative integer, zero, or a positive integer as this card is less than, equal
		to, or greater than the specified card.
	 *  
	 *  For BigTwoCard, Ace and Two should add up +13 so that their rank is higher than K. 
	 *  Make Ace and Two the highest in rank. 
	 * 	Then Compare the rank. 
	 *  @param card 
	 *  				card object that is being compared.
	 *  @return integer value - negative integer, zero, or a positive integer as this card is
	 *         less than, equal to, or greater than the specified card
	 *  
	 */
	public int compareTo(Card card) {
		int comprank=0;
		int paramrank=0;
		
		//Let Ace or Two be +13 in value
		if(this.getRank()==0||this.getRank()==1) {
			comprank=this.rank+13; 
		}
		if(card.getRank()==0||card.getRank()==1) {
			paramrank=card.rank+13;
		}
		//Rank comparison
		if (comprank > paramrank) {
			return 1;
		} else if (comprank < paramrank) {
			return -1;
		} else if (this.getRank() > card.getRank()) {
			return 1;
		} else if(this.getRank()< card.getRank()) {
			return -1;
			//Suit comparison
		}else if (this.getSuit() > card.getSuit()) {
			return 1;
		} else if (this.getSuit() < card.getSuit()) {
			return -1;
		} else {
			return 0;
		}
			
	}
	
	
	
}
