package windowz;

import interfacez.Disposable;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import renderz.materialz.EBO;
import renderz.materialz.VAO;
import renderz.materialz.VBO;
import renderz.shaderz.ShaderProgram;
import renderz.texturez.Texture2D;
import util.OpenGLUtils;
import windowz.managerz.KeyListener;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window implements Disposable
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
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

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

        glfwSetFramebufferSizeCallback(windowObject, WindowResizeListener.getInstance());
        glfwSetKeyCallback(windowObject, KeyListener.getInstance());

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

        System.out.println("Hello Nebula! Using OpenGL version: "+glGetString(GL_VERSION));

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
        ShaderProgram testShader = ShaderProgram.createShaderProgram("assets\\triangle.vert", "assets\\triangle.frag");

        try {

            camera = new Camera2D(0, 0);

            float[] vertices = {
                  //X       Y       Z
                    -0.5f,  0.5f,   0f, // Top Left
                    0.5f,   0.5f,   0f, // Top Right
                    -0.5f,  -0.5f,  0f, // Bottom Left
                    0.5f,  -0.5f,   0f  // Bottom Right
            };
            
            float[] colors = {
                  //R       G       B       A
                    1f,     1f,     1f,     1f, // Top Left
                    1f,     1f,     1f,     1f, // Top Right
                    1f,     1f,     1f,     1f, // Bottom Left
                    1f,     1f,     1f,     1f  // Bottom Right
            };

            float[] uvs = {
                    1, 0,
                    0, 0,
                    1, 1,
                    0, 1
            };

            int[] indices = {
                    0, 3, 2,
                    1, 0, 3
            };

            Texture2D tex = Texture2D.createTexture2D("assets/goldenBricks.png");

            VAO vao = new VAO();
            vao.bind();
            EBO ebo = new EBO(indices, 3);
            ebo.bind();
            VBO vertexVBO = new VBO(vertices, 3);
            VBO colorVBO = new VBO(colors, 4);
            VBO uvVBO = new VBO(uvs, 2);
            vao.vertexAttribPtr(vertexVBO, 0);
            vao.vertexAttribPtr(colorVBO, 1);
            vao.vertexAttribPtr(uvVBO, 2);

            IntBuffer screenWidth = BufferUtils.createIntBuffer(4), screenHeight = BufferUtils.createIntBuffer(4);

            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

            long now = System.currentTimeMillis();
            long another = System.currentTimeMillis();

            int error = 0;

            int frame = 0;

            while (!glfwWindowShouldClose(windowObject)) {
                moveCamera();
                frame++;

                error = Math.max(glGetError(), error);

                // Prints camera position every second
                now = System.currentTimeMillis();
                System.out.print("\rCurrent Camera Position: X: " + camera.position.x + " Y: " + camera.position.y +
                            " | Rendering time: "+(now-another)+ " ms | frame: "+frame + " | Error code: "+OpenGLUtils.getErrorCodeMessage(error) + " " + error);

                another = System.currentTimeMillis();

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
                glClearColor(0f, 0, 0, 1f);
                glfwGetFramebufferSize(windowObject, screenWidth, screenHeight);

                screenWidth.clear();
                screenHeight.clear();

                width = screenWidth.get(0);
                height = screenHeight.get(0);

                glViewport(0, 0, width, height);

                vao.bind();
                testShader.bind();
                tex.use(1);
                testShader.uploadUniformTexture2D("texture_1", 1);
                testShader.uploadUniform1f("texture_id", 1);
                vao.enableAttribute(0);
                vao.enableAttribute(1);
                vao.enableAttribute(2);
                glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
                OpenGLUtils.checkForException();
                vao.disableAttribute(0);
                vao.disableAttribute(1);
                vao.disableAttribute(2);
                vao.unbind();
                glUseProgram(0);
                OpenGLUtils.checkForException();

                glfwSwapBuffers(windowObject);

                // Poll for window events. The key callback above will only be invoked during this call.
                glfwPollEvents();

                OpenGLUtils.checkForException();
            }

            tex.dispose();
            vao.dispose();
            colorVBO.dispose();
            vertexVBO.dispose();
            uvVBO.dispose();
            ebo.dispose();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            testShader.dispose();
            dispose();
        }
    }

    private void moveCamera ()
    {
        final int camSpeed = 5;
        if (KeyListener.isKeyPressed(GLFW_KEY_A))
        {
            camera.position.x -= camSpeed;
        }
        else if (KeyListener.isKeyPressed(GLFW_KEY_D))
        {
            camera.position.x += camSpeed;
        }
        if (KeyListener.isKeyPressed(GLFW_KEY_W))
        {
            camera.position.y += camSpeed;
        }
        else if (KeyListener.isKeyPressed(GLFW_KEY_S))
        {
            camera.position.y -= camSpeed;
        }
    }

    public void dispose ()
    {
        glfwDestroyWindow(windowObject);
        glfwTerminate();
    }

    public static Window get ()
    {
        return window == null ? new Window() : window;
    }
}
