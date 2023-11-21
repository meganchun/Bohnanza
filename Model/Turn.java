//Megan Chun

package Model;

public interface Turn {
	
	boolean plant(Card bean);
	
	public String discard();
	
	public String extendedTurn();
	
	public boolean purchaseBeanField(int coins);
	
	public boolean sell(int fieldNum);
	
}
