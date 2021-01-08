package machine;

import java.awt.Color;

// draw menu items and return the serial number of the selected item
public class RightSideMenu {
    View view;
    int widthInBoardSquare = 4;     // Width  of the menu = 4 * squareSize (4 chessboard square)
    int buttonCount = 4;     // forward, backward, fast-forward, fast-backward
    int x0, y0;             // menu top-left coordinates
    ControlButton buttonFastBackward;
    ControlButton buttonBackward;
    ControlButton buttonForward;
    ControlButton buttonFastForward;

    public RightSideMenu(View view) {
        this.view = view;
        buttonFastBackward = new ControlButton();
        buttonBackward = new ControlButton();
        buttonForward = new ControlButton();
        buttonFastForward = new ControlButton();
    }

    public ControlButtonType getButtonType(int xPixel, int yPixel) {
        if (buttonFastBackward.isButtonClicked(xPixel, yPixel)) return buttonFastBackward.type;
        if (buttonBackward.isButtonClicked(xPixel, yPixel)) return buttonBackward.type;
        if (buttonForward.isButtonClicked(xPixel, yPixel)) return buttonForward.type;
        if (buttonFastForward.isButtonClicked(xPixel, yPixel)) return buttonFastForward.type;

        return null;
    }

    public int convertCoordinate(int baseCoordinate, int translation, int baseCoordinateScalerInPercent) {
        double baseScaler = baseCoordinateScalerInPercent / 100.0;
        double retValue;
        retValue = baseCoordinate;
        retValue *= baseScaler;
        retValue += translation;
        return (int) (retValue);
    }

    public void paintButtons() {
        int allButtonWidth = view.size * (widthInBoardSquare - 1);
        int buttonWidth = allButtonWidth / buttonCount;
        int buttonHeight = view.size / 2;
        view.g.setColor(Color.BLACK);

        buttonFastBackward.init(this.x0, this.y0, buttonWidth, buttonHeight, ControlButtonType.FAST_BACKWARD);
        paintButtonBackground(buttonWidth, buttonHeight, this.x0);
        drawLineButton(buttonFastBackward, 19, 30, 19, 70);
        drawLineButton(buttonFastBackward, 20, 30, 20, 70);
        drawLineButton(buttonFastBackward, 21, 30, 21, 70);
        drawTriangle(buttonFastBackward, 20, 50, 50, 30, 50, 70);
        drawLineButton(buttonFastBackward, 50, 50, 80, 30);
        drawLineButton(buttonFastBackward, 50, 50, 80, 70);
        drawTriangle(buttonFastBackward, 50, 50, 80, 30, 80, 70);

        buttonBackward.init(this.x0 + buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.BACKWARD);
        paintButtonBackground(buttonWidth, buttonHeight, this.x0 + buttonWidth);
        drawTriangle(buttonBackward, 35, 50, 65, 30, 65, 70);

        buttonForward.init(this.x0 + 2 * buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.FORWARD);
        paintButtonBackground(buttonWidth, buttonHeight, this.x0 + 2 * buttonWidth);
        drawTriangle(buttonForward, 35, 30, 65, 50, 35, 70);

        buttonFastForward.init(this.x0 + 3 * buttonWidth, this.y0, buttonWidth, buttonHeight, ControlButtonType.FAST_FORWARD);
        paintButtonBackground(buttonWidth, buttonHeight, this.x0 + 3 * buttonWidth);
        drawLineButton(buttonFastForward, 80, 30, 80, 70);
        drawLineButton(buttonFastForward, 81, 30, 81, 70);
        drawLineButton(buttonFastForward, 82, 30, 82, 70);
        drawLineButton(buttonFastForward, 83, 30, 83, 70);
        drawTriangle(buttonFastForward, 20, 30, 50, 50, 20, 70);
        drawTriangle(buttonFastForward, 50, 30, 80, 50, 50, 70);
    }

    public void paint() {
        this.x0 = (int) (view.size * 8.5);
        this.y0 = (int) (view.size * 3.75);
        paintButtons();
    }

    private void paintButtonBackground(int buttonWidth, int buttonHeight, int x0) {
        view.g.setColor(new Color(225, 225, 170));
        view.g.fillRect(x0, this.y0, buttonWidth, buttonHeight);
    }

    private void drawLineButton(ControlButton lineButton, int x1, int y1, int x2, int y2) {
        view.g.setColor(Color.BLACK);
        x1 = convertCoordinate(x1, lineButton.x0, lineButton.buttonWidth);
        y1 = convertCoordinate(y1, lineButton.y0, lineButton.buttonHeight);
        x2 = convertCoordinate(x2, lineButton.x0, lineButton.buttonWidth);
        y2 = convertCoordinate(y2, lineButton.y0, lineButton.buttonHeight);
        view.g.drawLine(x1, y1, x2, y2);
    }

    private void drawTriangle(ControlButton lineButton, int x1, int y1, int x2, int y2, int x3, int y3) {
        view.g.setColor(Color.BLACK);
        x1 = convertCoordinate(x1, lineButton.x0, lineButton.buttonWidth);
        y1 = convertCoordinate(y1, lineButton.y0, lineButton.buttonHeight);
        x2 = convertCoordinate(x2, lineButton.x0, lineButton.buttonWidth);
        y2 = convertCoordinate(y2, lineButton.y0, lineButton.buttonHeight);
        x3 = convertCoordinate(x3, lineButton.x0, lineButton.buttonWidth);
        y3 = convertCoordinate(y3, lineButton.y0, lineButton.buttonHeight);
        int[] x = {x1, x2, x3};
        int[] y = {y1, y2, y3};
        view.g.fillPolygon(x, y, 3);

        view.g.setColor(new Color(0, 0, 0));
        view.g.drawPolygon(x, y, 3);

    }
}

class ControlButton {
    int x0, y0;         // top-left coordinates
    int buttonWidth, buttonHeight;
    ControlButtonType type;

    public ControlButton() {
    }

    public ControlButton(int x0, int y0, int buttonWidth, int buttonHeight, ControlButtonType type) {
        this.x0 = x0;
        this.y0 = y0;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.type = type;
    }

    public void init(int x0, int y0, int buttonWidth, int buttonHeight, ControlButtonType controlButtonType) {
        this.x0 = x0;
        this.y0 = y0;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.type = controlButtonType;
    }

    /**
     * check whether mouse click is between button coordinates
     */
    public boolean isButtonClicked(int xPixel, int yPixel) {
        return (this.x0 <= xPixel) &&
                (xPixel <= (this.x0 + this.buttonWidth)) &&
                (this.y0 <= yPixel) &&
                (yPixel <= (this.y0 + this.buttonHeight));
    }
}

enum ControlButtonType {
    FAST_BACKWARD,
    BACKWARD,
    FORWARD,
    FAST_FORWARD
}
