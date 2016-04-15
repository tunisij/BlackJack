import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 *  Actions:
 *  0 == STAND
 *  1 == HIT
 */
public class AIDecisionEngine implements DecisionEngine {
	private ArrayList<RoundResult> results = new ArrayList<RoundResult>();
	
	private Integer[][] matrixR;
	private Integer[][] matrixQ;
	private double gamma;
	
	public AIDecisionEngine() {
		matrixR = new Integer[22][22];
		matrixQ = new Integer [22][22];
		gamma = 0.8;
		
		initializeOptionMap();
		initializeMemoryMap();
	}
	
	public Decision decide(Hand playerHand, Card dealerCard, int count) {
		Random random = new Random();
		ArrayList<Integer> options = new ArrayList<Integer>();
		ArrayList<Integer> nextStateOptions = new ArrayList<Integer>();
		
		for (int index = 0; index < matrixR[playerHand.getValue()].length; index++) {
			if (matrixR[playerHand.getValue()][index] != -1) {
				options.add(index);
			}
		}
		
		int n = options.get(random.nextInt(options.size()));
		
		for (int index = 0; index < matrixR[n].length; index++) {
			if (matrixR[n][index] != -1) {
				nextStateOptions.add(index);
			}
		}
		
		Integer nextStateMax = Collections.max(nextStateOptions);
		
		matrixQ[playerHand.getValue()][n] = (int) (matrixR[playerHand.getValue()][n] + gamma * nextStateMax);
		
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(matrixQ[playerHand.getValue()]));
		Integer max = Collections.max(list);
		
		for (Integer[] is : matrixQ) {
			System.out.print("[");
			for (int i : is) {
				System.out.print(i + ", ");
			}
			System.out.print("]");
			System.out.print("\n");
		}
		System.out.print("\n");
		
		if (playerHand.getValue() == list.indexOf(max)) {
			return Decision.STAND;
		} else {
			return Decision.HIT;
		}
		
	}
	
	private void initializeOptionMap() {
		for (int handValue = 0; handValue < matrixR.length; handValue++) {
			for (int newHandValue = 0; newHandValue < matrixR[handValue].length; newHandValue++) {
				if (handValue < 2) {
					matrixR[handValue][newHandValue] = -1;
				} else if (handValue == 21 && newHandValue == 21) {
					matrixR[handValue][newHandValue] = 100;
				} else if (newHandValue < handValue){
					matrixR[handValue][newHandValue] = -1;
				} else {
					matrixR[handValue][newHandValue] = 0;
				}
			}
		}
	}
	
	private void initializeMemoryMap() {
		for (int i = 0; i < matrixQ.length; i++) {
			for (int j = 0; j < matrixQ[i].length; j++) {
				matrixQ[i][j] = 0;
			}
		}
	}
	
	public void appendResult(RoundResult result) {
		results.add(result);
	}
	
}
