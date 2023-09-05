package events;

public abstract class Event
{
    protected Object source;
    protected int type;

    public Event (Object source, int type)
    {
        this.source = source;
        this.type = type;
    }

    public Object getSource ()
    {
        return source;
    }

    public int getType ()
    {
        return type;
    }
}
