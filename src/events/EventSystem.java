package events;

import java.util.ArrayList;
import java.util.List;

public class EventSystem
{
    private static final List<EventListener> listeners = new ArrayList<>();

    private EventSystem () {}

    protected static void addListener (EventListener listener)
    {
        listeners.add(listener);
    }

    protected static void removeListener (EventListener listener)
    {
        listeners.remove(listener);
    }

    protected static void fireEvent (Event event)
    {
        for (EventListener listener : listeners)
        {
            listener.react(event);
        }
    }
}
