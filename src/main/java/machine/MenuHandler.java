package machine;

public class MenuHandler {
    Controller controller;

    public MenuHandler() {
        // TODO Auto-generated constructor stub
    }

    public void buttonFastBackward() {
        System.out.println();
        System.out.print("Fast Backward button clicked");
    }

    public void buttonBackward() {
        System.out.println();
        System.out.print("Backward button clicked");
    }

    public void buttonForward() {
        System.out.println();
        System.out.print("Forward button clicked");
    }

    public void buttonFastForward() {
        System.out.println();
        System.out.print("Fast Forward button clicked");
    }

    public void buttonHandler(int buttonId) {
        switch (buttonId) {
            case 1:
                buttonFastBackward();
                break;
            case 2:
                controller.game.undoMove();
                buttonBackward();
                break;
            case 3:
                buttonForward();
                break;
            case 4:
                buttonFastForward();
                break;
        }
    }
}
