import java.util.Scanner;

public class PlayerDecisionEngine implements DecisionEngine {

	public Decision decide(Hand playerHand, Card dealerCard, int count) {
		return getDecisionFromHuman(playerHand, dealerCard);
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
