package events;

@FunctionalInterface
public interface EventListener
{
    void react (Event event);
}
