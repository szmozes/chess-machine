package machine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TablePanel extends JPanel {

    Table table;
    int size;               // length of a field in pixels
    int menuWidth;          // menu width (at the right of the board)
    MenuItems rightMenu;    // menu at the right of the chessboard
    MenuHandler handleMenu; // execute the clicked menu item
    Graphics g;

    BufferedImage blackBishopImage;
    BufferedImage blackKingImage;
    BufferedImage blackKnightImage;
    BufferedImage blackPawnImage;
    BufferedImage blackQueenImage;
    BufferedImage blackRookImage;
    BufferedImage whiteBishopImage;
    BufferedImage whiteKingImage;
    BufferedImage whiteKnightImage;
    BufferedImage whitePawnImage;
    BufferedImage whiteQueenImage;
    BufferedImage whiteRookImage;

    public TablePanel(Table table) {
        size = 60;    // initial value of 'size'
        rightMenu = new MenuItems();    /** menu at the right of the chessboard */
        handleMenu = new MenuHandler(); /** execute the clicked menu item		*/
        menuWidth = rightMenu.getMenuWidth(size); /** menu width */
        setPreferredSize(new Dimension(8 * size + menuWidth, 8 * size));
        this.table = table;
    }

    public void init() {
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;

        /** recalculate the current size of a chessboard square */
        size = rightMenu.getSquareSize(this.getWidth(), this.getHeight());
        rightMenu.paint(g);

        // background
        for (int i = 0; i < table.height; i++) {
            for (int j = 0; j < table.width; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new java.awt.Color(235, 235, 127));
                } else {
                    g.setColor(new java.awt.Color(150, 150, 90));
                }
                g.fillRect(j * size, i * size, size, size);
            }
        }
        g.setColor(java.awt.Color.BLACK);
        g.drawRect(0, 0, size * table.width - 1, size * table.height - 1);

        // fields
        for (int i = 0; i < table.height; i++) {
            for (int j = 0; j < table.width; j++) {
                table.fields[i][j].paint(g, size, this);
            }
        }

    }

    public static void main(String[] args) {

        Game game = new StandardGameAgainstMachine();
        TablePanel p = new TablePanel(game.table);
        p.addMouseListener(new MyMouseListener(p, game));

        JFrame f = new JFrame();
        f.setContentPane(p);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void paintPiece(Piece piece) {

    }

    private BufferedImage getImageByPiece(Piece piece) {
        if (piece instanceof Bishop) {
            return piece.color == Color.BLACK ? blackBishopImage : whiteBishopImage;
        } else {
            return null;
        }
    }
}
