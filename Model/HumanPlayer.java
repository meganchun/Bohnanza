// Megan Chun

package Model;

import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;

import View.PlayerPanel;

public class HumanPlayer extends Player implements Turn {

	public HumanPlayer(String name, Queue<Card> hand, Map<Card, Integer> field, boolean thirdFieldOwned,
			int score, int currentStage) {
		super(name, hand, field, thirdFieldOwned, score, currentStage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startTurn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean plant() {
		// TODO Auto-generated method stub
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
	public void sell() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnEnd() {
		// TODO Auto-generated method stub
		
	}

}
