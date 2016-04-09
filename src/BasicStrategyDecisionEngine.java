public class BasicStrategyDecisionEngine implements DecisionEngine {
	public Decision decide(Hand playerHand, Card dealerCard, int count) {
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
	
}
