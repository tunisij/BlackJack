
public class Card {
	private Rank rank;
	private Suit suit;
	
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public int getValue() {
		return rank.getValue();
	}
	
	public boolean isAce() {
		return rank.equals(Rank.ACE);
	}
	
	public String toString() {
		return rank + " of " + suit;
	}
	
}
