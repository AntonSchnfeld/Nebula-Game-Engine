package renderz.shaderz;

public class ShaderCompileException extends RuntimeException
{
    public ShaderCompileException (String msg)
    {
        super(msg);
    }

    public ShaderCompileException ()
    {
        super();
    }
}
