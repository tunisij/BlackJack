import java.util.ArrayList;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

/*
 * http://mnemstudio.org/path-finding-q-learning-tutorial.htm
 */
public class BlackJackMain {
	
	BlackJackGameEngine engine = new BlackJackGameEngine();
	ArrayList<RoundResult> randomResults = engine.playRounds(20000, new RandomDecisionEngine());
	ArrayList<RoundResult> basicStrategyResults = engine.playRounds(20000, new BasicStrategyDecisionEngine());
	
	Integer blackjacks = 0;
	Integer wins = 0;
	Integer losses = 0;
	Integer pushes = 0;

	public void run() {
//		for (RoundResult roundResult : randomResults) {
//			for (Hand hand : roundResult.getPlayerHands()) {
//				addToCount(hand);
//				
//				System.out.println(hand.toString());
//			}
//			System.out.println(roundResult.getDealerHand());
//		}
		
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
