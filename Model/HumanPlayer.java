// Megan Chun

package Model;

import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;

import View.PlayerPanel;

public class HumanPlayer extends Player implements Turn {

	public HumanPlayer(String name, Queue<Card> hand, Card[] beansInField, int[] numBeansInField, boolean thirdFieldOwned,
			int score, int currentStage) {
		super(name, hand, beansInField, numBeansInField, thirdFieldOwned, score, currentStage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean plant(Card bean) {
		// TODO Auto-generated method stub
	
		for (int i = 0; i < 3; i++) {
			
			if (bean == this.getBeansInField()[i] || this.getNumBeansInField()[i] == 0) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public String discard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String extendedTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean purchaseBeanField(int coins) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sell(int fieldNum) {
		
		if (this.getNumBeansInField()[fieldNum] != 0) {
			return true;
		}
		else 
			return false;
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		
	}

}
