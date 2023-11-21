//Megan Chun

package Model;

public interface Turn {
	
	boolean plant(Card bean);
	
	public boolean sell(int fieldNum);
	
	public String extendedTurn();
	
	public boolean purchaseBeanField(int coins);
	
}
