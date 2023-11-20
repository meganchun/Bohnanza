//Megan Chun

package Model;

public interface Turn {

	public void startTurn();
	
	public boolean plant();
	
	public String discard();
	
	public String extendedTurn();
	
	public boolean purchaseBeanField(int coins);
	
	public void sell();
	
	public void turnEnd();
}
