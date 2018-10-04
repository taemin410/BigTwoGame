/**
 * BigTwoDeck class that is used in BigTwoCard Game
 * 
 * BigTwoDeck is a subclass of Deck class
 * Uses BigTwoCards to initialize the deck of 52 cards.
 * Overrides initialize method inherited from Deck class
 * 
 * @author hataemin
 *
 */
public class BigTwoDeck extends Deck {
	
	/**
	 * Constructor for BigTwoDeck class
	 * 
	 * Creates and returns an instance of the BigTwoDeck class.
	 * 
	 */
	public BigTwoDeck() {
		this.initialize();
	}
	/**
	 * public method for BigTwoDeck class.
	 * Overrides initialize method in Deck class.
	 * remove all cards from the deck, create 52 Big Two cards and add them to the deck.
	 * Return type void.
	 * 
	 * Initializes the deck of cards using BigTwocards.
	 * 
	 */
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				BigTwoCard card = new BigTwoCard(i, j);
				addCard(card);
				}
			}

		}
	
}