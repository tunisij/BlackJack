import java.util.ArrayList;

public class BlackJackMain {
	
	BlackJackGameEngine engine = new BlackJackGameEngine();
	ArrayList<RoundResult> roundResults = engine.playRounds(200000);
	
	Integer blackjacks = 0;
	Integer wins = 0;
	Integer losses = 0;
	Integer pushes = 0;

	public void run() {
		for (RoundResult roundResult : roundResults) {
			for (Hand hand : roundResult.getPlayerHands()) {
				addToCount(hand);
				
				//System.out.println(hand.toString());
			}
			//System.out.println(roundResult.getDealerHand());
		}
		
		printCounts();
	}
	
	private void addToCount(Hand hand) {
		switch (hand.getResult()) {
			case BLACKJACK:
				blackjacks++;
				wins++;
				break;
			case WIN:
				wins++;
				break;
			case PUSH:
				pushes++;
				break;
			case LOSE:
				losses++;
				break;
		}
	}
	
	private void printCounts() {
		System.out.println("Blackjacks: " + blackjacks);
		System.out.println("Wins: " + wins);
		System.out.println("Pushes: " + pushes);
		System.out.println("Losses: " + losses);
	}

	public static void main(String[] args) {
		BlackJackMain main = new BlackJackMain();
		main.run();
	 }
}
