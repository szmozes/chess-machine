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

	public void write() {
		System.out.print(this.getClass().toString().subSequence(14, 16));
	}
}
