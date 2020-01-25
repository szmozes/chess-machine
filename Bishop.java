package machine;

import java.awt.Graphics;
import java.util.ArrayList;

public class Bishop extends Piece {
	
	public Bishop(Field field, Color color) {
		super(field, color);
	}
	
	public ArrayList<int[]> opportunities() {
		
		// the opportunities (x-y pairs) are stored here
		ArrayList<int[]> opportunities = new ArrayList<int[]>();
		
		// to north-west
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row decreases, the column decreases
			int oppRow = field.row - i;
			int oppCol = field.column - i;
			
			// make sure not to exceed the edges
			if(oppRow < 0 || oppCol < 0) {
				break;
			}
			
			// get to know what is on the field
			Color color = field.table.getPieceColor(oppRow, oppCol);
			
			// if it's empty, we add the opportunity, and go further
			if(color == null) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				continue;
			}
			
			// if it's not empty, we stop, and add the opportunity if needed
			if(color != this.color) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				break;
			}
			
			// if it's neither empty nor enemy, then we can't go further
			break;
		}

		// to north-east
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row decreases, the column increases
			int oppRow = field.row - i;
			int oppCol = field.column + i;

			// make sure not to exceed the edges
			if(oppRow < 0 || oppCol >= field.table.width) {
				break;
			}
			
			// get to know what is on the field
			Color color = field.table.getPieceColor(oppRow, oppCol);
			
			// if it's empty, we add the opportunity, and go further
			if(color == null) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				continue;
			}
			
			// if it's not empty, we stop, and add the opportunity if needed
			if(color != this.color) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				break;
			}
			
			// if it's neither empty nor enemy, then we can't go further
			break;
		}
		
		// to south-west
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row increases, the column decreases
			int oppRow = field.row + i;
			int oppCol = field.column - i;

			// make sure not to exceed the edges
			if(oppRow >= field.table.height || oppCol < 0) {
				break;
			}
			
			// get to know what is on the field
			Color color = field.table.getPieceColor(oppRow, oppCol);
			
			// if it's empty, we add the opportunity, and go further
			if(color == null) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				continue;
			}
			
			// if it's not empty, we stop, and add the opportunity if needed
			if(color != this.color) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				break;
			}
			
			// if it's neither empty nor enemy, then we can't go further
			break;
		}

		// to south-east
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row increases, the column increases
			int oppRow = field.row + i;
			int oppCol = field.column + i;

			// make sure not to exceed the edges
			if(oppRow >= field.table.height || oppCol >= field.table.width) {
				break;
			}
			
			// get to know what is on the field
			Color color = field.table.getPieceColor(oppRow, oppCol);
			
			// if it's empty, we add the opportunity, and go further
			if(color == null) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				continue;
			}
			
			// if it's not empty, we stop, and add the opportunity if needed
			if(color != this.color) {
				int[] opportunity = new int[2];
				opportunity[0] = oppRow;
				opportunity[1] = oppCol;
				opportunities.add(opportunity);
				break;
			}
			
			// if it's neither empty nor enemy, then we can't go further
			break;
		}
		
		return opportunities;
	}

	public void paint(Graphics g, int size) {
		ColoredPiece("B", 21, 17, g, size);
		ColoredPiece("i", 23, 30, g, size);
		ColoredPiece("shop", 12, 43, g, size);
	}
	
	public double getValue() {
		return 3;
	}
}
