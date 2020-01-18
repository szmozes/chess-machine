package machine;

import java.awt.Graphics;
import java.util.ArrayList;

public class Pawn extends Piece {
	
	boolean hasNotMovedYet;
	
	public Pawn(Field field, Color color) {
		super(field, color);
		hasNotMovedYet = true;
	}	
	public ArrayList<int[]> opportunities() {
		
		// the opportunities (x-y pairs) are stored here
		ArrayList<int[]> opportunities = new ArrayList<int[]>();
		
		int oppRow = field.row;
		int oppCol = field.column;
		
		// first we distinguish between black and white
		switch(color) {
		case WHITE: oppRow--; break;
		case BLACK: oppRow++; break;
		}
		
		// check if it can go straight
		if(field.table.getPieceColor(oppRow, oppCol) == null) {
			int[] oppurtunity = new int[2];
			oppurtunity[0] = oppRow;
			oppurtunity[1] = oppCol;
			opportunities.add(oppurtunity);
		}
	
		// check if it can capture towards west
		oppCol--;
		if(oppCol >= 0) {
			Color westColor = field.table.getPieceColor(oppRow, oppCol);
			if(westColor != null && westColor != this.color) {
				int[] oppurtunity = new int[2];
				oppurtunity[0] = oppRow;
				oppurtunity[1] = oppCol;
				opportunities.add(oppurtunity);
			}
		}
		
		// check if it can capture towards east
		oppCol += 2;
		if(oppCol < field.table.width) {
			Color eastColor = field.table.getPieceColor(oppRow, oppCol);
			if(eastColor != null && eastColor != this.color) {
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
		g.drawString("Pawn", field.column*size, field.row*size+10);
	}

}
