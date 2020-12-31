package machine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Pawn extends Piece {
	
	boolean hasNotMovedYet;
	boolean hasJustJumped;
	
	public Pawn(Field field, Color color) {
		super(field, color);
		hasNotMovedYet = true;
		hasJustJumped = false;
	}	
	public ArrayList<int[]> opportunities() {
		
		// the opportunities (x-y pairs) are stored here
		ArrayList<int[]> opportunities = new ArrayList<int[]>();
		
		int row = field.row;
		int col = field.column;
		
		// first we distinguish between black and white
		switch(color) {
		case WHITE:
			
			// make sure not to exceed the table edge
			if(row-1 < 0) {
				return opportunities;
			}
			
			// the fields before it are empty
			boolean freeNorth1 = field.table.getPieceColor(row-1, col) == null;
			boolean freeNorth2 = field.table.getPieceColor(row-2, col) == null;
			
			// check if it can step 1
			if(freeNorth1) {
				int[] oppurtunity = new int[2];
				oppurtunity[0] = row-1;
				oppurtunity[1] = col;
				opportunities.add(oppurtunity);
			}
			
			// check if it can step 2
			if(row == 6 && freeNorth1 && freeNorth2 ) {
				int[] oppurtunity = new int[2];
				oppurtunity[0] = row-2;
				oppurtunity[1] = col;
				opportunities.add(oppurtunity);
			}
			
			// capture to west
			if(col-1 >= 0) {
				Color westColor = field.table.getPieceColor(row-1, col-1);
				if(westColor != null && westColor != this.color) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row-1;
					oppurtunity[1] = col-1;
					opportunities.add(oppurtunity);
				}
			}
			
			// capture to east
			if(col+1 < field.table.width) {
				Color eastColor = field.table.getPieceColor(row-1, col+1);
				if(eastColor != null && eastColor != this.color) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row-1;
					oppurtunity[1] = col+1;
					opportunities.add(oppurtunity);
				}
			}
			break;
		case BLACK:
			
			// make sure not to exceed the table edge
			if(row+1 >= field.table.height) {
				return opportunities;
			}
			
			// the fields before it are empty
			boolean freeSouth1 = field.table.getPieceColor(row+1, col) == null;
			boolean freeSouth2 = field.table.getPieceColor(row+2, col) == null;
			
			// check if it can step 1
			if(freeSouth1) {
				int[] oppurtunity = new int[2];
				oppurtunity[0] = row+1;
				oppurtunity[1] = col;
				opportunities.add(oppurtunity);
			}
			
			// check if it can step 2
			if(row == 1 && freeSouth1 && freeSouth2) {
				int[] oppurtunity = new int[2];
				oppurtunity[0] = row+2;
				oppurtunity[1] = col;
				opportunities.add(oppurtunity);
			}
			
			// capture to west
			if(col-1 >= 0) {
				Color westColor = field.table.getPieceColor(row+1, col-1);
				if(westColor != null && westColor != this.color) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row+1;
					oppurtunity[1] = col-1;
					opportunities.add(oppurtunity);
				}
			}
			
			// capture to east
			if(col+1 < field.table.width) {
				Color eastColor = field.table.getPieceColor(row+1, col+1);
				if(eastColor != null && eastColor != this.color) {
					int[] oppurtunity = new int[2];
					oppurtunity[0] = row+1;
					oppurtunity[1] = col+1;
					opportunities.add(oppurtunity);
				}
			}
			break;
		}
		
		return opportunities;
	}
	
	public void paint(Graphics g, int size) {
		switch(color) {
		case WHITE:
			BufferedImage img1;
			try {
				img1 = ImageIO.read(new File("white_pawn.png"));
				g.drawImage(img1, field.column*size, field.row*size, size, size, null);
			} catch (IOException e) {
				System.out.println("white_pawn.png not found");
			}
			break;
		case BLACK: 
			BufferedImage img2;
			try {
				img2 = ImageIO.read(new File("black_pawn.png"));
				g.drawImage(img2, field.column*size, field.row*size, size, size, null);
			} catch (IOException e) {
				System.out.println("black_pawn.png not found");
			}
			break;
		}
	}

	public double getValue() {
		return 1;
	}
}
