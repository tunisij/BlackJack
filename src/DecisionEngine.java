public interface DecisionEngine {
	public Decision decide(Hand playerHand, Card dealerCard, int count);
	public void appendResult(RoundResult result);
	public void updateMatrixQ(ResultEnum result);
}
