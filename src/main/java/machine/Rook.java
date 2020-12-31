package machine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Rook extends Piece {
	
	boolean hasNotMovedYet;
	
	public Rook(Field field, Color color) {
		super(field, color);
		hasNotMovedYet = true;
	}
	public ArrayList<int[]> opportunities() {
		
		// the opportunities (x-y pairs) are stored here
		ArrayList<int[]> opportunities = new ArrayList<int[]>();
		
		// to north
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row changes, the column stays
			int oppRow = field.row - i;
			int oppCol = field.column;
			
			// make sure not to exceed the edges
			if(oppRow < 0) {
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

		// to south
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row changes, the column stays
			int oppRow = field.row + i;
			int oppCol = field.column;

			// make sure not to exceed the edges
			if(oppRow >= field.table.height) {
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
		
		// to west
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row stays, the column changes
			int oppRow = field.row;
			int oppCol = field.column - i;

			// make sure not to exceed the edges
			if(oppCol < 0) {
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

		// to east
		for(int i = 1; i < 8; i++) {

			// to get the opportunity's spot, we add the iterator to the initial spot
			// the row stays, the column changes
			int oppRow = field.row;
			int oppCol = field.column + i;

			// make sure not to exceed the edges
			if(oppCol >= field.table.width) {
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
		switch(color) {
		case WHITE:
			BufferedImage img1;
			try {
				img1 = ImageIO.read(new File("white_rook.png"));
				g.drawImage(img1, field.column*size, field.row*size, size, size, null);
			} catch (IOException e) {
				System.out.println("white_rook.png didn't found");
			}
			break;
		case BLACK: 
			BufferedImage img2;
			try {
				img2 = ImageIO.read(new File("black_rook.png"));
				g.drawImage(img2, field.column*size, field.row*size, size, size, null);
			} catch (IOException e) {
				System.out.println("black_rook.png didn't found");
			}
			break;
		}
	}

	public double getValue() {
		return 5;
	}
	
}
