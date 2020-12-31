package machine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(Field field, Color color) {
		super(field, color);
	}

	public ArrayList<int[]> opportunities() {
		
		// the opportunities (x-y pairs) are stored here
		ArrayList<int[]> opportunities = new ArrayList<int[]>();

		// here we have the relative spots
		int[] rowPos = {-1, -2, -2, -1, 1, 2, 2, 1};
		int[] colPos = {-2, -1, 1, 2, 2, 1, -1, -2};
		
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
		switch(color) {
		case WHITE:
			BufferedImage img1;
			try {
				img1 = ImageIO.read(new File("white_knight.png"));
				g.drawImage(img1, field.column*size, field.row*size, size, size, null);
			} catch (IOException e) {
				System.out.println("white_knight.png didn't found");
			}
			break;
		case BLACK: 
			BufferedImage img2;
			try {
				img2 = ImageIO.read(new File("black_knight.png"));
				g.drawImage(img2, field.column*size, field.row*size, size, size, null);
			} catch (IOException e) {
				System.out.println("black_knight.png didn't found");
			}
			break;
		}
	}
	
	public double getValue() {
		return 3;
	}
}
