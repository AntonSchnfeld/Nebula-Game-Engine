package events;

public interface EventSource
{
    default void addListener (EventListener listener)
    {
        EventSystem.addListener(listener);
    }
    default void removeListener (EventListener listener)
    {
        EventSystem.removeListener(listener);
    }

    default void fireEvent (Event event)
    {
        EventSystem.fireEvent(event);
    }
}
