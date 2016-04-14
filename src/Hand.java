import java.util.ArrayList;

public class Hand {

	private ArrayList<Card> cards;
	private ResultEnum result;
	
	public Hand(Card first, Card second) {
		cards = new ArrayList<Card>();
		
		cards.add(first);
		cards.add(second);
	}
	
	public int hit(Card card) {
		cards.add(card);
		return getValue();
	}
	
	public Hand split(Card first, Card second) {
		Hand secondHand = new Hand(cards.remove(0), second);
		cards.add(first);
		return secondHand;
	}
	
	public boolean isBust() {
		return getValue() > 21;
	}
	
	public boolean isSoft() {
		for (Card card : cards) {
			if (card.isAce()) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getSize() {
		return cards.size();
	}
	
	public boolean isValidToSplit() {
		return cards.size() == 2 && cards.get(0).getValue() == cards.get(1).getValue();
	}
	
	public boolean isValidToDoubleDown() {
		return cards.size() == 2 && getValue() >= 9 && getValue() <= 11;
	}
	
	public boolean isBlackJack() {
		return getValue() == 21 && cards.size() == 2;
	}
	
	public Card getFirstCard() {
		return cards.get(0);
	}
	
	public int getValue() {
		int value = 0;
		
		for (Card card : cards) {
			value += card.getValue();
		}
		
		if (value > 21) {
			for (Card card : cards) {
				if (card.getRank().equals(Rank.ACE)) {
					value -= 10;
					break;
				}
			}
		}
		
		return value;
	}
	
	public ResultEnum getResult() {
		return result;
	}
	
	public void setResult(ResultEnum result) {
		this.result = result;
	}
	
	public String toString() {
		String string = "";
		
		for (Card card : cards) {
			string += card.toString() + "\n";
		}
		
		string += getValue() + "\n";
		
		if (result != null) {
			string += getResult() + "\n";
		}
		
		return string;
	}
	
	public Card get(int cardIndex) {
		return cards.get(cardIndex);
	}
	
}
