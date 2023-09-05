package entitiez;

import java.util.Arrays;

public class Transform
{
    private float[] position;
    private float[] scale;

    public Transform (float[] position, float[] scale)
    {
        this.position = position;
        this.scale = scale;
    }

    public void setPosition (float[] position)
    {
        this.position = position;
    }

    public void setScale (float[] scale)
    {
        this.scale = scale;
    }

    public float[] getPosition ()
    {
        return position;
    }

    public float[] getScale ()
    {
        return scale;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == null) return false;
        if (!(o instanceof Transform transform)) return false;
        return Arrays.equals(transform.scale, this.scale) && Arrays.equals(transform.position, this.position);
    }

    public Transform copy ()
    {
        return new Transform(Arrays.copyOfRange(position, 0, position.length),
                Arrays.copyOfRange(position, 0, position.length));
    }
}
