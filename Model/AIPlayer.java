package Model;

import java.util.Queue;

public class AIPlayer extends Player implements Turn {
	
	private String mode;
	
	public AIPlayer(String name, Queue<Card> hand, Card[] beansInField, int[] numBeansInField, boolean thirdFieldOwned,
			int score, int currentStage, String mode) {
		super(name, hand, beansInField, numBeansInField, thirdFieldOwned, score, currentStage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean plant(Card bean) {
		// TODO Auto-generated method stub
	
		if (this.isThirdFieldOwned()) {
			
			for (int i = 0; i < 3; i++) {
				
				if (bean == this.getBeansInField()[i] || this.getNumBeansInField()[i] == 0) {
					return true;
				}
			}
		}
		else {
			for (int i = 0; i < 2; i++) {
				
				if (bean == this.getBeansInField()[i] || this.getNumBeansInField()[i] == 0) {
					return true;
					
				}
			}
		}
		
		return false;
		
	}


	@Override
	public boolean sell(int fieldNum) {
		
		if (this.getNumBeansInField()[fieldNum] != 0) {
			
			//if you are trying to sell a field with only one bean...
			//make sure the other fields have only one bean
			if (this.getNumBeansInField()[fieldNum] == 1) {
				
				for (int i = 0; i < 3; i++) {
					if (this.getNumBeansInField()[fieldNum] > 1)
						return false;
				}
				
				return true;
			}
			else 
				return true;
		}
		else 
			return false;
	
		
	}

	@Override
	public String extendedTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	

}
