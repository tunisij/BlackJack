import java.util.ArrayList;

public class RoundResult {

	private ArrayList<Hand> playerHands;
	private Hand dealerHand;
	
	public RoundResult(ArrayList<Hand> playerHands, Hand dealerHand) {
		this.playerHands = playerHands;
		this.dealerHand = dealerHand;
	}

	public ArrayList<Hand> getPlayerHands() {
		return playerHands;
	}

	public Hand getDealerHand() {
		return dealerHand;
	}
	
}
