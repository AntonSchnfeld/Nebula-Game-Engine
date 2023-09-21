package entitiez.componentz;

import entitiez.Component;
import renderz.materialz.Shape;
import renderz.materialz.ShapeType;

public class RenderComponent extends Component
{
    private final Shape shape;
    private Sprite sprite;
    private float[] vertices;
    private boolean dirty;

    public RenderComponent (Shape shape, Sprite sprite)
    {
        this.shape = shape;
        this.sprite = sprite;

        this.vertices = new float[]
                {

                };

        this.dirty = false;
    }

    public synchronized void setVertices (float[] vertices)
    {
        dirty = true;
        shape.setVertices(vertices);
    }

    public Sprite getSprite ()
    {
        return sprite;
    }

    public synchronized void setSprite (Sprite sprite)
    {
        this.sprite = sprite;
        this.dirty = true;
    }

    public boolean isDirty ()
    {
        return dirty;
    }

    public void setClean ()
    {
        this.dirty = false;
    }

    public ShapeType getShapeType ()
    {
        return shape.getShapeType();
    }

    public float[] getVertices ()
    {
        return vertices;
    }

    @Override
    public <T extends Component> T copy() {
        return null;
    }

    @Override
    public void dispose()
    {
        vertices = null;
        sprite.dispose();
    }

    @Override
    public void start()
    {

    }
}
