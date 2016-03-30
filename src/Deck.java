import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> deck = new ArrayList<>();
	
	public Deck() {
		initializeDeck(1);
	}
	
	public Deck(int numberOfDecks) {
		initializeDeck(numberOfDecks);
	}
	
	private void initializeDeck(int numberOfDecks) {
		for (int i = 0; i < numberOfDecks; i++) {
			for (Suit suit: Suit.values()){
	            for (Rank rank : Rank.values()){
	                deck.add(new Card(rank, suit));
	            }
	        }
		}
		Collections.shuffle(deck);
	}
	
	public Card deal() {
		return deck.remove(0);
	}
}
