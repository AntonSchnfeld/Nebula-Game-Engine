package windowz;

import org.lwjgl.glfw.GLFWFramebufferSizeCallback;

public class WindowResizeListener extends GLFWFramebufferSizeCallback
{
    private WindowResizeListener() { }

    private static int width = 0;
    private static int height = 0;

    @Override
    public void invoke(long window, int width, int height)
    {
        WindowResizeListener.width = width;
        WindowResizeListener.height = height;
    }

    public static int getHeight ()
    {
        return WindowResizeListener.height;
    }

    public static int getWidth ()
    {
        return WindowResizeListener.width;
    }

    private static class Holder
    {
        private static final WindowResizeListener instance = new WindowResizeListener();
    }

    public static WindowResizeListener getInstance()
    {
        return WindowResizeListener.Holder.instance;
    }
}
