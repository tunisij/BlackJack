import java.util.Random;

public class RandomDecisionEngine implements DecisionEngine {
	public Decision decide(Hand playerHand, Card dealerCard, int count) {
		Random random = new Random();
		
		int n = random.nextInt(4);
		
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
}
