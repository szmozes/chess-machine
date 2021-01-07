package machine;

import java.awt.*;

// draw menu items and return the serial number of the selected item
public class MenuItems {

    int menuWidth;          // Width  of the menu at the right of the board (in pixels)
    int menuSquare = 4;     // Width  of the menu = 4 * squareSize (4 chessboard square)
    int squareSize;         // Size of one chessboard square in pixels
    int menuButton = 4;     // forward, backward, fast-forward, fast-backward
    int x0, y0;             // menu top-left coordinates
    int xSize, ySize;       // menu sizes
    ControlButton buttonFastBackward;
    ControlButton buttonBackward;
    ControlButton buttonForward;
    ControlButton buttonFastForward;

    public MenuItems() {
        buttonFastBackward = new ControlButton();
        buttonBackward = new ControlButton();
        buttonForward = new ControlButton();
        buttonFastForward = new ControlButton();
    }

    public int getMenuWidth(int squareSize) {
        this.menuWidth = squareSize * menuSquare; // size of the menu at the right of the board
        return this.menuWidth;
    }

    public int getSquareSize(int panelWidth, int panelHeight) {
        // panelWidth, panelHeight: size of the main panel in pixels
        int xSize, ySize; // max size of a chessboard square in pixels
        xSize = panelWidth / (8 + menuSquare); // 8 chessboard square + 4 space width for the menu
        ySize = panelHeight / 8;
        this.squareSize = Math.min(xSize, ySize);  // squareSize: size of a chessboard square
        this.menuWidth = getMenuWidth(squareSize); // Width of the menu

        return this.squareSize;
    }

    public boolean buttonClicked(ControlButton button1, int xPixel, int yPixel) {
        // check whether mouse click is between button coordinates
        boolean bClicked;
        bClicked = (button1.x0 <= xPixel) && (xPixel <= (button1.x0 + button1.xSize)) &&
                (button1.y0 <= yPixel) && (yPixel <= (button1.y0 + button1.ySize));

        return bClicked;
    }

    public int getButtonID(int xPixel, int yPixel) {
        int bID = 0; // the ID of the clicked button (0: no button clicked)
        if (buttonClicked(buttonFastBackward, xPixel, yPixel)) bID = buttonFastBackward.buttonID;
        if (buttonClicked(buttonBackward, xPixel, yPixel)) bID = buttonBackward.buttonID;
        if (buttonClicked(buttonForward, xPixel, yPixel)) bID = buttonForward.buttonID;
        if (buttonClicked(buttonFastForward, xPixel, yPixel)) bID = buttonFastForward.buttonID;

        return bID;
    }

    public int convertCoordinate(int x1, int x0, int xSize) {
        double xRatio = xSize / 100.0;
        double x;
        x = x1;
        x *= xRatio;
        x += x0;
        return (int) (x);
    }

    private void drawLineButton(Graphics g, ControlButton lineButton, int x1, int y1, int x2, int y2) {
        g.setColor(java.awt.Color.BLACK);
        x1 = convertCoordinate(x1, lineButton.x0, lineButton.xSize);
        y1 = convertCoordinate(y1, lineButton.y0, lineButton.ySize);
        x2 = convertCoordinate(x2, lineButton.x0, lineButton.xSize);
        y2 = convertCoordinate(y2, lineButton.y0, lineButton.ySize);
        g.drawLine(x1, y1, x2, y2);
    }

    private void drawTriangle(Graphics g, ControlButton lineButton, int x1, int y1, int x2, int y2, int x3, int y3) {
        g.setColor(java.awt.Color.BLACK);
        x1 = convertCoordinate(x1, lineButton.x0, lineButton.xSize);
        y1 = convertCoordinate(y1, lineButton.y0, lineButton.ySize);
        x2 = convertCoordinate(x2, lineButton.x0, lineButton.xSize);
        y2 = convertCoordinate(y2, lineButton.y0, lineButton.ySize);
        x3 = convertCoordinate(x3, lineButton.x0, lineButton.xSize);
        y3 = convertCoordinate(y3, lineButton.y0, lineButton.ySize);
        int[] x = {x1, x2, x3};
        int[] y = {y1, y2, y3};
        g.fillPolygon(x, y, 3);

        g.setColor(new java.awt.Color(0, 0, 0));
        g.drawPolygon(x, y, 3);

    }

    public void button1(Graphics g, ControlButton button1, int x0, int y0, int xSize, int ySize, int bID) {
        button1.x0 = x0;
        button1.y0 = y0;
        button1.xSize = xSize;
        button1.ySize = ySize;
        button1.buttonID = bID;
        g.setColor(new java.awt.Color(225, 225, 170));
        g.fillRect(x0, y0, xSize, ySize);
    }

    public void paintButtons(Graphics g) {
        g.setColor(java.awt.Color.BLACK);
        int buttonSize = this.xSize / menuButton;

        button1(g, buttonFastBackward, this.x0, this.y0, buttonSize, this.ySize, 1);
        drawLineButton(g, buttonFastBackward, 19, 30, 19, 70);
        drawLineButton(g, buttonFastBackward, 20, 30, 20, 70);
        drawLineButton(g, buttonFastBackward, 21, 30, 21, 70);
        drawTriangle(g, buttonFastBackward, 20, 50, 50, 30, 50, 70);
        drawLineButton(g, buttonFastBackward, 50, 50, 80, 30);
        drawLineButton(g, buttonFastBackward, 50, 50, 80, 70);
        drawTriangle(g, buttonFastBackward, 50, 50, 80, 30, 80, 70);

        button1(g, buttonBackward, this.x0 + buttonSize, this.y0, buttonSize, this.ySize, 2);
        drawTriangle(g, buttonBackward, 35, 50, 65, 30, 65, 70);

        button1(g, buttonForward, this.x0 + 2 * buttonSize, this.y0, buttonSize, this.ySize, 3);
        drawTriangle(g, buttonForward, 35, 30, 65, 50, 35, 70);

        button1(g, buttonFastForward, this.x0 + 3 * buttonSize, this.y0, buttonSize, this.ySize, 4);
        drawLineButton(g, buttonFastForward, 80, 30, 80, 70);
        drawLineButton(g, buttonFastForward, 81, 30, 81, 70);
        drawLineButton(g, buttonFastForward, 82, 30, 82, 70);
        drawLineButton(g, buttonFastForward, 83, 30, 83, 70);
        drawTriangle(g, buttonFastForward, 20, 30, 50, 50, 20, 70);
        drawTriangle(g, buttonFastForward, 50, 30, 80, 50, 50, 70);
    }

    public void paint(Graphics g) {
        this.x0 = (int) (squareSize * 8.5);
        this.y0 = (int) (squareSize * 3.75);
        this.xSize = squareSize * (menuSquare - 1);
        this.ySize = squareSize / 2;
        paintButtons(g);
    }
}

class ControlButton {
    int x0, y0;         // button top-left coordinates
    int xSize, ySize;   // button sizes
    int buttonID;       // button ID number
}