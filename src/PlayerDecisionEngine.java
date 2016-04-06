import java.util.Random;
import java.util.Scanner;

public class PlayerDecisionEngine {

	public Decision decide(Hand playerHand, Card dealerCard, int count) {
		return getBasicStrategyDecision(playerHand, dealerCard);
//		return getRandomDecision();
//		return getDecisionFromHuman(playerHand, dealerCard);
	}
	
	private Decision getBasicStrategyDecision(Hand playerHand, Card dealerCard) {
		if (playerHand.getValue() >= 17) {
			return Decision.STAND;
		} else if (playerHand.isValidToSplit() && (playerHand.getFirstCard().getRank().equals(Rank.ACE) || playerHand.getFirstCard().getRank().equals(Rank.EIGHT))) {
			return Decision.SPLIT;
		} else if (playerHand.isValidToDoubleDown() && playerHand.isSoft() && playerHand.getValue() >=9 && playerHand.getValue() <= 11 && dealerCard.getValue() < 7) {
			return Decision.DOUBLE_DOWN;
		} else if (playerHand.getValue() <= 11 || dealerCard.getValue() > 6) {
			return Decision.HIT;
		} else {
			return Decision.STAND;
		}
	}
	
	private Decision getRandomDecision() {
		Random random = new Random();
		
		int n = random.nextInt(4);
		System.out.println(n);
		
		if (n == 0) {
			return Decision.HIT;
		} else if (n == 1) {
			return Decision.STAND;
		} else if (n == 2) {
			return Decision.SPLIT;
		} else if (n == 3) {
			return Decision.DOUBLE_DOWN;
		} else {
			return Decision.STAND;
		}
	}
	
	private Decision getDecisionFromHuman(Hand playerHand, Card dealerCard) {
		Scanner reader = new Scanner(System.in);
		System.out.println(playerHand.toString());
		System.out.println("Dealer showing: " + dealerCard.toString());
		System.out.println();
		System.out.println("Press 1 to HIT");
		System.out.println("Press 2 to STAND");
		System.out.println("Press 3 to SPLIT");
		System.out.println("Press 4 to DOUBLE DOWN");
		int n = reader.nextInt();
		
		if (n == 1) {
			return Decision.HIT;
		} else if (n == 2) {
			return Decision.STAND;
		} else if (n == 3) {
			return Decision.SPLIT;
		} else if (n == 4) {
			return Decision.DOUBLE_DOWN;
		} else {
			return Decision.STAND;
		}
	}
}
