/* Aaron Su
 * 
 */
// sourced from https://www.geeksforgeeks.org/map-interface-java-examples/

package Controller;

import java.util.*;
import Controller.GUIController;
import Model.Card;

public class DeckController {

	private final int ENDLIMIT = 98;
	// instance variables...
	private Map<Integer, Card> numCardMap;
	private Stack<Card> cardList = new Stack<Card>();
	private Stack<Card> discardList = new Stack<Card>();
	private int garbageKey = 0;
	
	private final String[] NAMELIST = {"Red","Black-eyed","Soy","Green","Stink","Chili","Blue"};

	// initialize array constants for the coins
	private int[] redArr = { 0, 0, 1, 2, 3, 5 };
	private int[] blackArr = { 0, 0, 1, 0, 2, 3, 5 };
	private int[] soyArr = { 0, 0, 1, 0, 2, 0, 3, 4 };
	private int[] greenArr = { 0, 0, 0, 1, 0, 2, 3, 4 };
	private int[] stinkArr = { 0, 0, 0, 1, 0, 2, 0, 3, 5 };
	private int[] chiliArr = { 0, 0, 0, 1, 0, 0, 2, 0, 3, 4 };
	private int[] blueArr = { 0, 0, 0, 0, 1, 0, 2, 0, 3, 0, 4 };

	public DeckController() {

		// call the setCards method to set up card objects...
		// method will also add them to the card stack
		setCards();

		// shuffle the cards
		Collections.shuffle(cardList);
	}

	public void setCards() {
		// create a hashmap for the card objects
		numCardMap = new HashMap<Integer, Card>();
	
		// fill the hashmap with number of beans as the key and card objects as the
		// value
		numCardMap.put(1, new Card("Red", 8, redArr, "Red.png"));
		numCardMap.put(2, new Card("Black-eyed", 10, blackArr, "Black-eyed.png"));
		numCardMap.put(3, new Card("Soy", 12, soyArr, "Soy.png"));
		numCardMap.put(4, new Card("Green", 14, greenArr, "Green.png"));
		numCardMap.put(5, new Card("Stink", 16, stinkArr, "Stink.png"));
		numCardMap.put(6, new Card("Chili", 18, chiliArr, "Chili.png"));
		numCardMap.put(7, new Card("Blue", 20, blueArr, "Blue.png"));

	

		// loop through the bean map using a for-each
		for (Map.Entry<Integer, Card> map : numCardMap.entrySet()) {

			// loop through the total number of beans of the current iteration
			for (int i = 1; i <= map.getValue().getNumOfBean(); i++) {
				// add the Card object to the stack list
				cardList.add(map.getValue());
			}
			
		}
	}
	

	
	public Stack<Card> getCardList() {
		return cardList;
	}

	public void setCardList(Stack<Card> cardList) {
		this.cardList = cardList;
	}

	public Stack<Card> getDiscardList() {
		return discardList;
	}

	public void setDiscardList(Stack<Card> discardList) {
		this.discardList = discardList;
	}

	// method to discard card (WIP)
	public void addToDiscard(Card bean) {
		
		garbageKey += 1;
		// create a hashmap for the card objects
		Map<Integer, Card> numCardMap = new HashMap<Integer, Card>();
		
		numCardMap.put(garbageKey, bean);
		discardList.add(numCardMap.get(garbageKey));
	}

	public Card[] drawTwoCards() {
		// initialize the cardArray to hold 3 of
		// the topmost cards from the deck (from pop)
		Card[] cardArr = new Card[2];

		cardArr[0] = cardList.pop();
		cardArr[1] = cardList.pop();

		return cardArr;
	}

	public Card[] drawThreeCards() {
		// initialize the cardArray to hold 3 of
		// the topmost cards from the deck (from pop)
		Card[] cardArr = new Card[3];

		cardArr[0] = cardList.pop();
		cardArr[1] = cardList.pop();
		cardArr[2] = cardList.pop();

		return cardArr;
	}

	public boolean endOfGame() {
		// check if the stack is empty...
		// used to determine the end of the game
		if (cardList.size() < 98 - ENDLIMIT)
			return true;
		
		return false;
	}
	
	public int findKeyNum(String beanName) {
		
		int index = 0;
		
		for (String bean : NAMELIST) {
			
			if (bean.equals(beanName)) 
				return index+1;
			
			index++;
		}
		
		return index;
	}

	public Card pop() {

		// pop a card from the card list stack
		return cardList.pop();
	}

	public Card popFromDiscard() {
		
		// pop a card from the garbage list stack
		return discardList.pop();
	}

	public Map<Integer, Card> getNumCardMap() {
		return numCardMap;
	}

	public void setNumCardMap(Map<Integer, Card> numCardMap) {
		this.numCardMap = numCardMap;
	}
}
