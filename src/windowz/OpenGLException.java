package windowz;

import static org.lwjgl.opengl.GL30C.*;
import static org.lwjgl.opengl.GL45C.GL_CONTEXT_LOST;

public class OpenGLException extends RuntimeException
{
    public OpenGLException (int errorCode)
    {
        super("Errorcode: "+errorCode+" | "+getErrorCodeMessage(errorCode));
    }

    public static String getErrorCodeMessage(int errorCode)
    {
        switch (errorCode)
        {
            case GL_INVALID_ENUM: return "GL_INVALID_ENUM";
            case GL_INVALID_VALUE: return "GL_INVALID_VALUE";
            case GL_INVALID_OPERATION: return "GL_INVALID_OPERATION";
            case GL_INVALID_FRAMEBUFFER_OPERATION: return "GL_INVALID_FRAMEBUFFER_OPERATION";
            case GL_STACK_OVERFLOW: return "GL_STACK_OVERFLOW";
            case GL_STACK_UNDERFLOW: return "GL_STACK_UNDERFLOW";
            case GL_OUT_OF_MEMORY: return "GL_OUT_OF_MEMORY";
            case GL_CONTEXT_LOST: return "GL_CONTEXT_LOST";
        }

        return "UNKNOWN ERROR CODE";
    }
}
