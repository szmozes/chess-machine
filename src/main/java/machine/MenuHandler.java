package machine;

public class MenuHandler {
    Controller controller;

    public MenuHandler() {
        // TODO Auto-generated constructor stub
    }

    public void buttonFastBackward() {
        System.out.println("Fast Backward button clicked");
    }

    public void buttonBackward() {
        System.out.println("Backward button clicked");
        controller.game.undoMove();
    }

    public void buttonForward() {
        System.out.println("Forward button clicked");
    }

    public void buttonFastForward() {
        System.out.println("Fast Forward button clicked");
    }

    public void buttonHandler(ControlButtonType controlButtonType) {
        switch (controlButtonType) {
            case FAST_BACKWARD:
                buttonFastBackward();
                break;
            case BACKWARD:
                buttonBackward();
                break;
            case FORWARD:
                buttonForward();
                break;
            case FAST_FORWARD:
                buttonFastForward();
                break;
        }
    }
}
