import java.util.ArrayList;
import java.util.Arrays;

public class BlackJackGameEngine {

	private Deck deck;
	private Hand dealerHand;
	private ArrayList<Hand> playerHands;
	private PlayerDecisionEngine playerDecisionEngine;
	
	public BlackJackGameEngine() {
		deck = new Deck(6);
		playerDecisionEngine = new PlayerDecisionEngine();
		
		dealerHand = new Hand(deck.deal(), deck.deal());
		playerHands = new ArrayList<Hand>(Arrays.asList(new Hand(deck.deal(), deck.deal())));
	}
	
	public RoundResult playRound() {
		playerHands = playPlayerHand(playerHands.get(0));

		playDealerHand();
		setResults();
		
		return new RoundResult(playerHands, dealerHand);
	}

	private ArrayList<Hand> playPlayerHand(Hand hand) {
		ArrayList<Hand> hands = new ArrayList<Hand>();
		Decision decision = null;
		
		while (hand.getValue() <= 21 && (decision == null || !decision.equals(Decision.STAND))) {
			decision = playerDecisionEngine.decide(hand, dealerHand.getDealerCard());
			
			switch(decision) {
			case HIT:
				hand.hit(deck.deal());
				break;
			case DOUBLE_DOWN:
				hand.hit(deck.deal());
				hands.add(hand);
				return hands;
			case SPLIT:
				hands.addAll(playPlayerHand(hand.split(deck.deal(), deck.deal())));
				break;
			default:
				break;
			}
		}
		
		hands.add(hand);
		return hands;
	}

	private void playDealerHand() {
		while (dealerHand.getValue() < 17) {
			dealerHand.hit(deck.deal());
		}
	}
	
	private void setResults() {
		for (Hand hand : playerHands) {
			if (hand.getValue() == dealerHand.getValue()) {
				hand.setResult(ResultEnum.PUSH);
			} else if ((hand.getValue() < dealerHand.getValue() && !dealerHand.isBust()) || hand.isBust()) {
				hand.setResult(ResultEnum.LOSE);
			} else if (hand.isBlackJack()) {
				hand.setResult(ResultEnum.BLACKJACK);
			} else {
				hand.setResult(ResultEnum.WIN);
			}
		}
	}
	
}
