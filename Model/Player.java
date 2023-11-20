//Megan Chun

package Model;

import java.util.Map;
import java.util.Queue;

import View.PlayerPanel;

public class Player {
	
	private String name;
	private Queue<Card> hand;
	private Map<Card, Integer> field;
	private boolean thirdFieldOwned;
	private int score;
	private int currentStage;
	private PlayerPanel panel;

	public Player(String name, Queue<Card> hand, Map<Card, Integer> field, boolean thirdFieldOwned, int score,
			int currentStage) {
		super();
		this.name = name;
		this.hand = hand;
		this.field = field;
		this.thirdFieldOwned = thirdFieldOwned;
		this.score = score;
		this.currentStage = currentStage;

	}

	//GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Queue<Card> getHand() {
		return hand;
	}

	public void setHand(Queue<Card> hand) {
		this.hand = hand;
	}

	public Map<Card, Integer> getField() {
		return field;
	}

	public void setField(Map<Card, Integer> field) {
		this.field = field;
	}

	public boolean isThirdFieldOwned() {
		return thirdFieldOwned;
	}

	public void setThirdFieldOwned(boolean thirdFieldOwned) {
		this.thirdFieldOwned = thirdFieldOwned;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(int currentStage) {
		this.currentStage = currentStage;
	}
	
	public PlayerPanel getPanel() {
		return panel;
	}

	public void setPanel(PlayerPanel panel) {
		this.panel = panel;
	}


}
