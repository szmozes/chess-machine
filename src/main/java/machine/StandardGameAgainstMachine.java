package machine;

public class StandardGameAgainstMachine extends StandardGame {
	
	Color machine;

	public StandardGameAgainstMachine() {
		super();
		machine = Color.BLACK;
		wake();
	}
	
	public StandardGameAgainstMachine(Color color) {
		machine = color;
		wake();
	}

	public void makeMove(int depth) {
		int[] bestCoords = new int[4];
		attempt(depth, bestCoords);
		move(bestCoords[0], bestCoords[1], bestCoords[2], bestCoords[3]);
	}
	

	public void userMove(int fromRow, int fromColumn, int toRow, int toColumn) {

		move(fromRow, fromColumn, toRow, toColumn);
		
		if(whoTurns == machine) {
			makeMove(4);
		}
	}
	
	public void wake() {
		if(whoTurns == machine) {
			makeMove(4);
		}
	}
}
