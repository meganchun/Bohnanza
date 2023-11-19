//Megan Chun

package Model;

import java.util.Map;
import java.util.Queue;

import View.PlayerPanel;

public class Player {
	
	private String name;
	private Queue<Card> hand;
	private Card[] beansInField;
	private int[] numBeansInField;
	private boolean thirdFieldOwned;
	private int score;
	private int currentStage;
	private PlayerPanel panel;

	public Player(String name, Queue<Card> hand, Card[] beansInField, int[] numBeansInField, boolean thirdFieldOwned,
			int score, int currentStage) {
		super();
		this.name = name;
		this.hand = hand;
		this.beansInField = beansInField;
		this.numBeansInField = numBeansInField;
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

	public Card[] getBeansInField() {
		return beansInField;
	}

	public void setBeansInField(Card[] beansInField) {
		this.beansInField = beansInField;
	}

	public int[] getNumBeansInField() {
		return numBeansInField;
	}

	public void setNumBeansInField(int[] numBeansInField) {
		this.numBeansInField = numBeansInField;
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
