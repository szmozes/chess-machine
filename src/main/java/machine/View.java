package machine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class View extends JPanel {
    Graphics g;
    final Table table;
    int size;               // length of a field in pixels
    int menuWidth;          // menu width (at the right of the board)
    MenuItems rightMenu;    // menu at the right of the chessboard
    MenuHandler handleMenu; // execute the clicked menu item

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

    public View(Table table) {
        size = 60;      // initial value
        rightMenu = new MenuItems();
        handleMenu = new MenuHandler();
        menuWidth = rightMenu.getMenuWidth(size);
        setPreferredSize(new Dimension(8 * size + menuWidth, 8 * size));
        this.table = table;
    }

    public static void main(String[] args) {

        Game game = new StandardGameAgainstMachine();
        View view = new View(game.table);
        view.init();
        view.addMouseListener(new MyMouseListener(view, game));

        JFrame f = new JFrame("Chess Program");
        f.setContentPane(view);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void init() {
        String imagePath = "images";

        try {
            blackBishopImage = ImageIO.read(new File(imagePath, "black_bishop.png"));
            blackKingImage = ImageIO.read(new File(imagePath, "black_king.png"));
            blackKnightImage = ImageIO.read(new File(imagePath, "black_knight.png"));
            blackPawnImage = ImageIO.read(new File(imagePath, "black_pawn.png"));
            blackQueenImage = ImageIO.read(new File(imagePath, "black_queen.png"));
            blackRookImage = ImageIO.read(new File(imagePath, "black_rook.png"));
            whiteBishopImage = ImageIO.read(new File(imagePath, "white_bishop.png"));
            whiteKingImage = ImageIO.read(new File(imagePath, "white_king.png"));
            whiteKnightImage = ImageIO.read(new File(imagePath, "white_knight.png"));
            whitePawnImage = ImageIO.read(new File(imagePath, "white_pawn.png"));
            whiteQueenImage = ImageIO.read(new File(imagePath, "white_queen.png"));
            whiteRookImage = ImageIO.read(new File(imagePath, "white_rook.png"));
        } catch (IOException e) {
            System.out.println("some of the pictures couldn't be loaded");
        }
    }

    public PieceEnum askUserForPiece() {
        Object[] pieceEnums = {PieceEnum.QUEEN, PieceEnum.BISHOP, PieceEnum.KNIGHT, PieceEnum.ROOK};
        PieceEnum chosenPieceKind = (PieceEnum) JOptionPane.showInputDialog(null,
                "Choose the piece you want", "Choosing piece",
                JOptionPane.QUESTION_MESSAGE, null, pieceEnums, pieceEnums[0]);
        if (chosenPieceKind == null) {
            chosenPieceKind = PieceEnum.QUEEN;
        }
        return chosenPieceKind;
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.g = g;

        // recalculate the current size of a chessboard square
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
                paintField(table.fields[i][j]);
            }
        }
    }

    private void paintField(Field field) {

        // paint the piece
        if (field.getPiece() != null) {
            paintPiece(field.getPiece());
        }

        // and the little sign of opportunity
        if (field.isCanBeSteppedOn()) {
            g.setColor(new java.awt.Color(200, 200, 255));
            int column = field.getColumn();
            int row = field.getRow();
            g.fillOval(column * size + size * 3 / 8, row * size + size * 3 / 8, size / 4, size / 4);
            g.setColor(new java.awt.Color(0, 0, 0));
            g.drawOval(column * size + size * 3 / 8, row * size + size * 3 / 8, size / 4, size / 4);
        }
    }

    private void paintPiece(Piece piece) {
        BufferedImage pieceImage = getImageByPiece(piece);
        Field field = piece.field;
        g.drawImage(pieceImage, field.column * size, field.row * size, size, size, null);
    }

    private BufferedImage getRead(String imagePath, String filename) {
        try {
            return ImageIO.read(new File(imagePath, filename));
        } catch (IOException e) {
            System.out.println(filename + " at " + imagePath + " couldn't be loaded");
            return null;
        }
    }

    private BufferedImage getImageByPiece(Piece piece) {
        if (piece instanceof Bishop) {
            return piece.color == Color.BLACK ? blackBishopImage : whiteBishopImage;
        } else if (piece instanceof King) {
            return piece.color == Color.BLACK ? blackKingImage : whiteKingImage;
        } else if (piece instanceof Knight) {
            return piece.color == Color.BLACK ? blackKnightImage : whiteKnightImage;
        } else if (piece instanceof Pawn) {
            return piece.color == Color.BLACK ? blackPawnImage : whitePawnImage;
        } else if (piece instanceof Queen) {
            return piece.color == Color.BLACK ? blackQueenImage : whiteQueenImage;
        } else if (piece instanceof Rook) {
            return piece.color == Color.BLACK ? blackRookImage : whiteRookImage;
        } else {
            return null;
        }
    }
}
