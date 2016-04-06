import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	
	private ArrayList<Card> deck = new ArrayList<>();
	private int count = 0;
	
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
		Card card = deck.remove(0);
		updateCount(card);
		return card;
	}
	
	public int getSize() {
		return deck.size();
	}
	
	private void updateCount(Card card) {
		if (card.getValue() <= 6) {
			count++;
		} else if (card.getValue() >= 10) {
			count--;
		}
	}
	
	public int getCount() {
		return count;
	}
}
