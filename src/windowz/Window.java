package windowz;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.system.MemoryStack;
import renderz.materialz.VAO;
import renderz.materialz.VBO;
import renderz.shaderz.ShaderProgram;
import renderz.texturez.Texture2D;
import util.OpenGLUtils;

import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private ShaderProgram defaultShader;
    private long windowObject;
    private int width, height;
    private final String title;

    private static Window window;

    private Window ()
    {
        this.title = WindowConfig.title;
        this.width = WindowConfig.width;
        this.height = WindowConfig.height;

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        boolean success = glfwInit();
        if (!success)
            throw new IllegalStateException("Unable to initialize GLFW");

        init();
    }

    private void init ()
    {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be non-resizable

        // Create the window
        windowObject = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowObject == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush())
        {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowObject, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(windowObject, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        glfwSetFramebufferSizeCallback(windowObject, new WindowResizeCallback());

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowObject);
        // Enable v-sync
        if (WindowConfig.vsync) glfwSwapInterval(1);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance, and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glfwShowWindow(windowObject);
    }

    public int getWidth ()
    {
        return width;
    }

    public int getHeight ()
    {
        return height;
    }

    private Camera2D camera;

    public void loop ()
    {
        defaultShader = ShaderProgram.getDefaultShaderProgram();
        ShaderProgram testShader = ShaderProgram.createShaderProgram("assets\\triangle.vert", "assets\\triangle.frag");

        Texture2D texture2D = new Texture2D("assets\\nebula.png");
        try {

            camera = new Camera2D(0, 0);

            float[] vertices = {
                    //X       Y       Z
                    -75, 75, 0.0f,  //Top left
                    75, 75f, 0.0f, //Top right
                    75, -75, 0.0f,  //Bottom right
                    -75, -75, 0.0f    //Bottom left
            };


            float[] colorArr = {
                  //R  G  B  A
                    1, 0, 0, 1,
                    0, 1, 0, 1,
                    0, 0, 1, 1,
                    0.5f, 1, 0.5f, 1
            };

            float[] uvs = {
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0
            };

            IntBuffer screenWidth = BufferUtils.createIntBuffer(4), screenHeight = BufferUtils.createIntBuffer(4);

            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            long now = System.currentTimeMillis();
            long another = System.currentTimeMillis();

            int error;

            testShader.use();

            VAO vao = new VAO();
            VBO positionVBO = new VBO(vertices, 3, GL_STATIC_DRAW);
            VBO colorVBO = new VBO(colorArr, 4, GL_STATIC_DRAW);
            vao.enableAttribute(positionVBO, 0);
            vao.enableAttribute(colorVBO, 1);

            OpenGLUtils.checkForException();

            vao.bind();
            testShader.use();

            while (!glfwWindowShouldClose(windowObject)) {
                moveCamera();

                // Prints camera position every second
                now = System.currentTimeMillis();
                System.out.print("\r Current Camera Position: X: " + camera.position.x + " Y: " + camera.position.y +
                            " | Rendering time: "+(now-another)+ " ms");

                another = System.currentTimeMillis();

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                glClearColor(0.5f, 0.5f, 0.5f, 1f);
                glfwGetFramebufferSize(windowObject, screenWidth, screenHeight);

                screenWidth.clear();
                screenHeight.clear();

                width = screenWidth.get(0);
                height = screenHeight.get(0);

                glViewport(0, 0, width, height);

                testShader.uploadMat4f("aView", camera.getViewMatrix());
                testShader.uploadMat4f("aProjection", camera.getProjectionMatrix());

                glDrawArrays(GL_QUADS, 0, 4);

                // Draw Background

                glfwSwapBuffers(windowObject); // swap the color buffers

                // Poll for window events. The key callback above will only be invoked during this call.
                glfwPollEvents();


                OpenGLUtils.checkForException();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

            texture2D.delete();
            testShader.delete();
            delete();
        }
    }

    private void moveCamera ()
    {
        final int camSpeed = 5;
        if (glfwGetKey(windowObject, GLFW_KEY_A) == GLFW_PRESS)
        {
            camera.position.x -= camSpeed;
        }
        else if (glfwGetKey(windowObject, GLFW_KEY_D) == GLFW_PRESS)
        {
            camera.position.x += camSpeed;
        }
        if (glfwGetKey(windowObject, GLFW_KEY_W) == GLFW_PRESS)
        {
            camera.position.y += camSpeed;
        }
        else if (glfwGetKey(windowObject, GLFW_KEY_S) == GLFW_PRESS)
        {
            camera.position.y -= camSpeed;
        }
    }

    private void delete ()
    {
        glfwDestroyWindow(windowObject);
        defaultShader.delete();
        glfwTerminate();
    }

    public static Window get ()
    {
        return window == null ? new Window() : window;
    }
}
