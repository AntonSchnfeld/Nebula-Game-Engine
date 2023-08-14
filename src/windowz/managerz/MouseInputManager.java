package windowz.managerz;

public interface MouseInputManager 
{
    public void handleMouseButtonInput (int button, int action);
    public void handleMouseMovement (double cursorX, double cursorY);
}