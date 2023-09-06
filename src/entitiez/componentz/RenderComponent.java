package entitiez.componentz;

import entitiez.Component;
import entitiez.Transform;
import renderz.Shape;

public class RenderComponent extends Component
{
    private Sprite sprite;
    private float[] vertices;
    private Transform transform;
    private Shape shape;

    public RenderComponent (Sprite sprite)
    {
        this.sprite = sprite;
        this.vertices = sprite.getVertices();
    }

    public RenderComponent (Sprite sprite, float[] vertices)
    {
        this.sprite = sprite;
        this.vertices = vertices;
    }

    public Sprite getSprite ()
    {
        return sprite;
    }

    public float[] getVertices ()
    {
        return vertices;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == null) return false;
        if (!(o instanceof RenderComponent comp)) return false;

        return comp.sprite.equals(this.sprite);
    }

    @Override
    @SuppressWarnings("all")
    public <T extends Component> T copy ()
    {
        return (T) new RenderComponent(sprite.copy());
    }

    @Override
    public void dispose ()
    {
        sprite.dispose();
    }

    @Override
    public void update (float dt)
    {
        this.transform = entity.getTransform().copy();
    }

    @Override
    public void start ()
    {

    }
}
