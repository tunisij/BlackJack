
//0 - stay
//1 - hit
//2 - split
//3 - double down
public class DecisionRecorder {
	private int[][][] dealingHandOptions;
	private int[][][] dealingHandOptionsMem;
	private int[][] otherOptionsMem;
	private Hand hand;
	public DecisionRecorder(Hand hand) {
		dealingHandOptions = new int[12][12][4];
		dealingHandOptionsMem = new int [12][12][4];
		otherOptionsMem = new int[22][2];
		this.hand = hand;
		
		setupOptionMaps();
	}
	
	private void setupOptionMaps() {
		for(int i = 1; i < dealingHandOptions.length; i++) {
			for(int j = 1; j < dealingHandOptions[i].length; j++) {
				for(int k = 0; k < dealingHandOptions[i][j].length; k++) {
					if(i == j) {
						dealingHandOptions[i][j][k] = 1;
					} else if((i + j) == 21) {
						switch(k) {
							//stay
							case 0:
								dealingHandOptions[i][j][k] = 100;
								break;
							//hit
							case 1:
								dealingHandOptions[i][j][k] = 0;
								break;
							//split
							case 2:
								dealingHandOptions[i][j][k] = 0;
								break;
							//double down
							case 3:
								dealingHandOptions[i][j][k] = 0;
								break;
							default:
						}						
					} else {
						switch(k) {
							//stay
							case 0:
								dealingHandOptions[i][j][k] = 1;
								break;
							//hit
							case 1:
								dealingHandOptions[i][j][k] = 1;
								break;
							//split
							case 2:
								dealingHandOptions[i][j][k] = -1;
								break;
							//double down
							case 3:
								dealingHandOptions[i][j][k] = -1;
								break;
							default:
							
						}
					}
				}
			}
		}
	}
	
	public Decision getDecision() {
		if (hand.getSize() < 3) {
			return Decision.HIT;
		} else {
			return Decision.STAND;
		}
	}
	
			
}
