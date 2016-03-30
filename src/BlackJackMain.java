
public class BlackJackMain {
	public static void main(String[] args) {
		BlackJackGameEngine engine = new BlackJackGameEngine();
	    
		RoundResult result = engine.playRound();
		
		for (Hand hand : result.getPlayerHands()) {
			System.out.println(hand.toString());
		}
		
		System.out.println(result.getDealerHand());
		
	  }
}
