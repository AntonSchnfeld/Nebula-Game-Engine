package entitiez;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * <br>
 * A {@link Transform} is used to store and manipulate all Information about position, scale and more.
 *
 * @author AntonSchnfeld
 */
public class Transform
{
    private Vector3f position;
    private Vector2f scale;

    /**
     * <br>
     * Creates a new {@link Transform} object.
     *
     * @param position the position
     * @param scale the width and height
     */
    public Transform (Vector3f position, Vector2f scale)
    {
        this.position = new Vector3f(position);
        this.scale = new Vector2f(scale);
    }

    /**
     * <br>
     * Sets the position of the {@link Transform}.
     *
     * @param position the new position
     */
    public void setPosition (Vector3f position)
    {
        this.position = position;
    }

    /**
     * <br>
     * Sets the scale of the {@link Transform}.
     *
     * @param scale
     */
    public void setScale (Vector2f scale)
    {
        this.scale = scale;
    }

    /**
     * <br>
     * Getter
     *
     * @return the position
     */
    public Vector3f getPosition ()
    {
        return position;
    }

    /**
     * <br>
     * Getter
     *
     * @return the scale
     */
    public Vector2f getScale ()
    {
        return scale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals (Object o)
    {
        if (o == null) return false;
        if (!(o instanceof Transform transform)) return false;
        return this.position.equals(transform.position) && this.scale.equals(transform.scale);
    }

    /**
     * <br>
     * Creates and returns a copy of the {@link Transform} Object.
     *
     * @return a copy of this {@link Transform}
     */
    public Transform copy ()
    {
        return new Transform(new Vector3f(position), new Vector2f(scale));
    }
}
