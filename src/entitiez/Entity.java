package entitiez;

import interfacez.Disposable;
import interfacez.Starteable;
import interfacez.Updateable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Entity implements Updateable, Starteable, Disposable
{
    private final List<Component> components;
    private Transform transform;
    private String name;
    private boolean started;

    public Entity (String name, Transform transform)
    {
        this.components = new CopyOnWriteArrayList<>();
        this.transform = transform;
        this.name = name;
        this.started = false;
    }

    @Override
    public void start ()
    {
        for (Component component : components)
        {
            component.start();
        }
        started = true;
    }

    public <T extends  Component> T getComponent (Class<T> componentClass)
    {
        for (Component component : components)
        {
            if (componentClass.isAssignableFrom(component.getClass()))
            {
                try
                {
                    return componentClass.cast(component);
                }
                catch (ClassCastException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }

    public <T extends Component> void removeComponent (Class<T> componentClass)
    {
        for (Component component : components)
        {
            if (componentClass.isAssignableFrom(component.getClass()))
            {
                components.remove(component);
                return;
            }
        }
    }

    public List<Component> getComponents ()
    {
        return components;
    }

    public void addComponent (Component component)
    {
        components.add(component);
        component.entity = this;
    }

    @Override
    public void update(float dt)
    {
        if (!started) start();

        for (Component component : components)
        {
            component.update(dt);
        }
    }

    @Override
    public void dispose ()
    {
        for (Component component : components)
        {
            component.dispose();
        }
    }

    public Entity copy ()
    {
        Entity copy = new Entity(this.name, this.transform);

        for (Component c : components)
        {

            copy.addComponent(Objects.requireNonNull(c.copy()));
        }

        return copy;
    }

    public String getName ()
    {
        return name;
    }

    public Transform getTransform ()
    {
        return transform;
    }
}
