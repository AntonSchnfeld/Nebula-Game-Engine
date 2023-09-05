package windowz.managerz;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LAST;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class KeyListener extends GLFWKeyCallback
{
    private KeyListener () {}

    private static final boolean[] keys = new boolean[GLFW_KEY_LAST];

    @Override
    public void invoke (long window, int key, int scancode, int action, int mods)
    {
        keys[key] = action == GLFW_PRESS;
    }

    public static boolean isKeyPressed (int key)
    {
        return keys[key];
    }

    private static class Holder
    {
        private static final KeyListener instance = new KeyListener();
    }

    public static KeyListener getInstance()
    {
        return Holder.instance;
    }
}
