package machine;

import java.awt.Graphics;
import java.util.ArrayList;

public class Piece {
	
	Field field;
	Color color;
	boolean grabbed;
	
	public Piece(Field field, Color color) {
		this.field = field;
		this.color = color;
		grabbed = false;
	}
	
	public ArrayList<int[]> opportunities() {
		return null;
	}

	public void paint(Graphics g, int size) {
		
	}

	public void ColoredPiece(String str, int xOffset, int yOffset, Graphics g, int size) {
		switch(color) {
		case WHITE:
			 g.setColor(java.awt.Color.white);
			 for(int i = xOffset-4; i <= xOffset+4; i++) {
				 for(int j = yOffset-4; j <= yOffset+4; j++) {
					 g.drawString(str, field.column*size+ i, field.row*size+j);
 				 }
 			 }
			 g.setColor(java.awt.Color.black);
			 g.drawString(str, field.column*size+xOffset, field.row*size+yOffset);
			 break;
		case BLACK: 
			 g.setColor(java.awt.Color.black);
			 for(int i = xOffset-4; i <= xOffset+4; i++) {
		 		 for(int j = yOffset-4; j <= yOffset+4; j++) {
	 				 g.drawString(str, field.column*size+ i, field.row*size+j);
 				 }
			 }
			 g.setColor(java.awt.Color.white);
			 g.drawString(str, field.column*size+xOffset, field.row*size+yOffset);
			 break;
		}
	}

	public void write() {
		System.out.print(this.getClass().toString().subSequence(14, 16));
	}
	
	public double getValue() {
		return 0;
	}
}
