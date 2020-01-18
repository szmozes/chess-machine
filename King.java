package machine;

import java.awt.Graphics;
import java.util.ArrayList;

public class King extends Piece {
	
	boolean hasNotMovedYet;
	
	public King(Field field, Color color) {
		super(field, color);
		hasNotMovedYet = true;
	}
	public ArrayList<int[]> opportunities() {
		
		// the opportunities (x-y pairs) are stored here
		ArrayList<int[]> opportunities = new ArrayList<int[]>();

		// here we have the relative spots
		int[] rowPos = {-1, -1, -1, 0, 0, 1, 1, 1};
		int[] colPos = {-1, 0, 1, -1, 1, -1, 0, 1};
		
		// we go through one-by-one, to see if we can add it to the possible opportunities
		for(int i = 0; i < 8; i++) {
			
			// to get the exact spot, we add the current and the relative coordinates
			int oppRow = super.field.row + rowPos[i];
			int oppCol = super.field.column + colPos[i];
			
			// first, we have to make sure we don't exceed the table's edges
			if(oppRow < 0 || oppRow >= super.field.table.height || oppCol < 0 || oppCol >= super.field.table.width) {
				// in case we exceed, we ignore the opportunity, and go to the next one
				continue;
			}

			// an then, the other conditions, for example, if there is a same colored piece
			if(super.field.table.getPieceColor(oppRow, oppCol) != super.color) {
				int[] oppurtunity = new int[2];
				oppurtunity[0] = oppRow;
				oppurtunity[1] = oppCol;
				opportunities.add(oppurtunity);
			}
		}
		return opportunities;
	}
	
	public void paint(Graphics g, int size) {
		g.setColor(java.awt.Color.black);
		g.drawString("King", field.column*size, field.row*size+10);
	}
}
