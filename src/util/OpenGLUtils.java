package util;

import windowz.OpenGLException;

import static org.lwjgl.opengl.GL30C.*;

public class OpenGLUtils
{
    public static final void checkForException ()
    {
        final int error;
        if ((error = glGetError()) != GL_NO_ERROR)
        {
            throw new OpenGLException(error);
        }
    }
}
