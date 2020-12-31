package machine;

public class Game {

	Color whoTurns;
	Table table;
	
	public Game() {
		table = new Table(8, 8);
		whoTurns = Color.WHITE;
	}
	
	
	public void move(int fromRow, int fromColumn, int toRow, int toColumn) {
		
		// set who turns
		switch(whoTurns) {
		case WHITE: whoTurns = Color.BLACK; break;
		case BLACK: whoTurns = Color.WHITE; break;
		}
	}
	
	public void userMove(int fromRow, int fromColumn, int toRow, int toColumn) {
		move(fromRow, fromColumn, toRow, toColumn);
	}
	
	public void wake() {
		
	}
	
	void setOpportunities(int row, int column) {
		
	}
	
}
