package renderz.texturez;

public class TextureLoadingException extends RuntimeException
{
    public TextureLoadingException (String msg)
    {
        super(msg);
    }

    public TextureLoadingException () {}
}
