package entitiez.componentz;

import entitiez.Component;
import renderz.texturez.Texture2D;

public class Sprite extends Component
{
    private final Texture2D texture;
    private float[] uvs;

    public Sprite (Texture2D texture, float[] uvs)
    {
        this.texture = texture;
        this.uvs = uvs;
    }

    public Texture2D getTexture ()
    {
        return texture;
    }

    public float[] getVertices ()
    {
        return uvs;
    }

    @Override
    @SuppressWarnings("all")
    public <T extends Component> T copy ()
    {
        return (T) new Sprite(texture, uvs);
    }

    @Override
    public void dispose ()
    {
        uvs = null;
        texture.dispose();
    }

    @Override
    public void start ()
    {

    }
}
