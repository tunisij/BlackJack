import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

public class BlackJackMain {
	
	final int rounds = 20000;
	
	BlackJackGameEngine engine = new BlackJackGameEngine();
	ArrayList<RoundResult> randomResults = engine.playRounds(rounds, new RandomDecisionEngine());
	ArrayList<RoundResult> basicStrategyResults = engine.playRounds(rounds, new BasicStrategyDecisionEngine());
	ArrayList<RoundResult> aiResults = engine.playRounds(rounds, new AIDecisionEngine());
	
	Integer blackjacks = 0;
	Integer wins = 0;
	Integer losses = 0;
	Integer pushes = 0;

	public void run() {
		for (RoundResult roundResult : aiResults) {
			for (Hand hand : roundResult.getPlayerHands()) {
				addToCount(hand);
				System.out.println("Player hand\n"+hand.toString());
			}
			System.out.println("Dealer hand\n"+roundResult.getDealerHand());
		}
		
		createGraph();
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
	
	private XYSeries createSeries(String title, ArrayList<RoundResult> results) {
		XYSeries series = new XYSeries(title);

		Integer count = 1;
		Integer wins = 0;
		for (RoundResult roundResult : results) {
			for (Hand hand : roundResult.getPlayerHands()) {
				if (hand.getResult().equals(ResultEnum.BLACKJACK) || hand.getResult().equals(ResultEnum.WIN)) {
					wins++;
				}

				series.add(count.doubleValue(), wins.doubleValue()/count.doubleValue());
				count++;
			}
		}
		
		return series;
	}
	
	private void createGraph() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(createSeries("Basic Strategy", basicStrategyResults));
		dataset.addSeries(createSeries("Random", randomResults));
		dataset.addSeries(createSeries("AI", aiResults));
		
		Chart chart = new Chart(dataset);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
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
