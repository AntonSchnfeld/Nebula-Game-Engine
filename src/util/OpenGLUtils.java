package util;

import windowz.OpenGLException;

import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.opengl.GL45C.GL_CONTEXT_LOST;

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


    public static String getErrorCodeMessage(int errorCode)
    {
        return switch (errorCode) {
            case GL_INVALID_ENUM -> "GL_INVALID_ENUM";
            case GL_INVALID_VALUE -> "GL_INVALID_VALUE";
            case GL_INVALID_OPERATION -> "GL_INVALID_OPERATION";
            case GL_INVALID_FRAMEBUFFER_OPERATION -> "GL_INVALID_FRAMEBUFFER_OPERATION";
            case GL_STACK_OVERFLOW -> "GL_STACK_OVERFLOW";
            case GL_STACK_UNDERFLOW -> "GL_STACK_UNDERFLOW";
            case GL_OUT_OF_MEMORY -> "GL_OUT_OF_MEMORY";
            case GL_CONTEXT_LOST -> "GL_CONTEXT_LOST";
            case GL_NO_ERROR -> "NO ERROR";
            default -> "UNKNOWN ERROR CODE";
        };

    }
}
