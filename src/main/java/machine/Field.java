package machine;

import java.awt.*;

public class Field {

	Table table;
	int column;
	int row;
	Piece piece;
	boolean steppable;
	
	public Field(Table table, int row, int column) {
		this.table = table;
		this.row = row;
		this.column = column;
		steppable = false;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
		if(piece != null) {
			piece.field = this;
		}
	}
	
	public void paint(Graphics g, int size, TablePanel tablePanel) {
		
		// paint the piece
		if(piece != null) {
			piece.paint(g, size);
		}

		tablePanel.paintPiece(piece);
		
		// and the little sign of opportunity
		if(steppable) {
			g.setColor(new java.awt.Color(200, 200, 255));
			g.fillOval(column*size + size*3/8, row*size + size*3/8, size/4, size/4);
			g.setColor(new java.awt.Color(0, 0, 0));
			g.drawOval(column*size + size*3/8, row*size + size*3/8, size/4, size/4);
		}
	}
}