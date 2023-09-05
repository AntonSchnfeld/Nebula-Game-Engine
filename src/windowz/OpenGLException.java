package windowz;

import util.OpenGLUtils;

public class OpenGLException extends RuntimeException
{
    public OpenGLException (int errorCode)
    {
        super("Errorcode: "+errorCode+" | "+ OpenGLUtils.getErrorCodeMessage(errorCode));
    }
}
