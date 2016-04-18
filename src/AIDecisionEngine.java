import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * This class implements the Q-Learning algorithm to allow an AI agent to
 * learn how to play blackjack.
 * 
 * More details: http://mnemstudio.org/path-finding-q-learning-tutorial.htm
 * 
 * matrixR:
 * The reward table. Each row corresponds to a total hand value. The columns
 * tell us how desirable a given hand value is. The elements held in this
 * matrix are integers between -1 and 100 inclusive where high numbers indicate
 * a more desirable total hand value. -1 means it is impossible to reach the
 * given hand value from the current value.
 * 
 * For example, if matrixR[8][21] holds a value of 100, we know that if we
 * currently have a total of 8 in our hand, we know the best total hand value
 * we could reach is 21. matrixR[8][5] will hold a -1 because there is no way
 * to get from a total hand value of 8 to 5. Rows 0 and 1 are filled with
 * values of -1 because it is impossible to have a hand totaling these values.
 * 
 * matrixQ:
 * This is the "brain" of our AI agent. It represents the memory of previous
 * actions our agent has taken and their outcome. Each row corresponds to a
 * total hand value. Each column represents the possible actions that can be
 * taken. As our agent plays more rounds, it learns which actions are better
 * in which cirucmstances.
 * 
 * gamma:
 * The learning rate.
 * 
 * @author John Tunisi, Kevin Anderson, Cyril Casapao
 */
public class AIDecisionEngine implements DecisionEngine {
	
	private ArrayList<RoundResult> results = new ArrayList<RoundResult>();
	private Stack<Point> stack = new Stack<Point>();
	
	private Integer[][] matrixR;
	private Integer[][] matrixQ;
	private double gamma;
	
	private Random random;
	
	
	/**
	 * Initializes an AI Decision Engine object with empty reward/memory
	 * matrices.
	 */
	public AIDecisionEngine() {
		matrixR = new Integer[22][22];
		matrixQ = new Integer [22][22];
		random = new Random(System.currentTimeMillis());
		gamma = 0.8;
		
		initializeOptionMap();
		initializeMemoryMap();
	}
	
	
	/**
	 * Makes a decision given the current value of our hand and our knowledge of
	 * the dealer's hand.
	 * 
	 * @param playerhand
	 * 		The cards in the player's hand
	 * @param dealerCard
	 * 		The one card we can see in the dealer's hand
	 * @param count
	 * 		Unused
	 * 
	 * @return A decision where 0 means stand and 1 means hit
	 */
	public Decision decide(Hand playerHand, Card dealerCard, int count) {
		ArrayList<Integer> possibleNextStates = new ArrayList<Integer>();
		ArrayList<Integer> nextStateRewards = new ArrayList<Integer>();
		int currentHandValue = playerHand.getValue();
		
		// Find the possible next states
		for (int i = 0; i < 22; i++) {
			if (matrixR[currentHandValue][i] != -1) {
				possibleNextStates.add(i);
			}
		}
		
		// Randomly select a state to go to next
		int nextState = possibleNextStates.get(random.nextInt(possibleNextStates.size()));
		
		// Fill in the array list of the reward values that can be reached
		// from the next state
		for (int i = 0; i < 22; i++) {
			if (matrixR[nextState][i] != -1) {
				nextStateRewards.add(i);
			}
		}
		
		// Find the best reward possible from the next state
		Integer nextStateMax = Collections.max(nextStateRewards);
		
		// Get the reward value for the randomly chosen state given the
		// value of the current hand
		int reward = matrixR[currentHandValue][nextState];
		
		// Update the agent's brain
		matrixQ[currentHandValue][nextState] = (int) (reward + (gamma * nextStateMax));

		/*
		System.out.println("MATRIX Q -------------------|");
		printMatrix(matrixQ);
		System.out.println("MATRIX R -------------------|");
		printMatrix(matrixR);
		*/
		
		Point thisAction = new Point(currentHandValue, nextState);
		stack.push(thisAction);
		
		// Decide on a best action based on our previous experiences
		ArrayList<Integer> experienceList = new ArrayList<Integer>(Arrays.asList(matrixQ[currentHandValue]));
		Integer max = Collections.max(experienceList);
		if (currentHandValue == experienceList.indexOf(max)) {
			return Decision.STAND;
		} else {
			return Decision.HIT;
		}
		
	}
	
	
	/**
	 * Initializes the memory matrix (matrixQ) with all zeros.
	 */
	private void initializeMemoryMap() {
		for (int i = 0; i < matrixQ.length; i++) {
			for (int j = 0; j < matrixQ[i].length; j++) {
				matrixQ[i][j] = 0;
			}
		}
	}
	
	/**
	 * Initialzes the reward matrix (matrixR). Values of -1 are placed in
	 * impossible to reach states and a 100 is placed in matrixR[21][21].
	 * All other positions are initialized with zeros.
	 */
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
	
	
	/**
	 * Adds the outcome of an action to our memory.
	 * 
	 * @param result
	 * 		The result of an action
	 */
	public void appendResult(RoundResult result) {
		results.add(result);
	}
	
	
	/**
	 * Updates matrixQ based on the outcome of the last round.
	 * 
	 * @param result
	 * 		The result of the last round
	 */
	public void updateMatrixQ(ResultEnum result) {
		Point previousAction = stack.pop();
		int i = previousAction.x;
		int j = previousAction.y;
		
		System.out.println("i: " + i + "\tj: " + j + "\tresult: " + result);
		switch(result) {
			case BLACKJACK: 
				matrixQ[i][j] += 50;
				break;
			case WIN:
				matrixQ[i][j] += 10;
				break;
			case LOSE:
				matrixQ[i][j] -= 10;
				break;
			case PUSH:
			default:
				break;
		}
	}
	
	/**
	 * Prints a matrix. Made specifically for printing matrixQ or matrixR.
	 * 
	 * @param matrix
	 * 		The matrix to print
	 */
	private void printMatrix(Integer[][] matrix) {
		for (Integer[] is : matrix) {
			System.out.print("[");
			for (int i : is) {
				System.out.print(i + ", ");
			}
			System.out.print("]");
			System.out.print("\n");
		}
		System.out.print("\n");
	}
}
