package machine;

import java.awt.*;
import java.util.ArrayList;

public class Piece {
	
	Field field;
	PieceEnum kind;
	Color color;
	
	public Piece(Field field, Color color) {
		this.field = field;
		this.color = color;
	}
	
	public Piece(PieceEnum kind, Color color) {
		this.kind = kind;
		this.color = color;
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
	
	public void imagePiece(String str, int sizePercentage, Graphics g, int size) {
        str = str+".png";
        int sizeInPixel;
        int xOffset;
        sizeInPixel = size*sizePercentage/100;
        xOffset = (size-sizeInPixel)/2;
		switch(color) {
		case WHITE:
			 Image img1 = Toolkit.getDefaultToolkit().getImage("white_"+str);
		     g.drawImage(img1, field.column*size+xOffset, field.row*size+xOffset, sizeInPixel, sizeInPixel, null);
			 break;
		case BLACK: 
		     Image img2 = Toolkit.getDefaultToolkit().getImage("black_"+str);
		     g.drawImage(img2, field.column*size+xOffset, field.row*size+xOffset, sizeInPixel, sizeInPixel, null);
			 break;
		}
	}

	public double getValue() {
		return 0;
	}
}
