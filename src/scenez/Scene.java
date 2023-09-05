package scenez;

import entitiez.Entity;
import interfacez.Disposable;
import interfacez.Starteable;
import interfacez.Updateable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Scene implements Updateable, Starteable, Disposable
{
    public final List<Entity> entities;

    public Scene ()
    {
        this.entities = new CopyOnWriteArrayList<>();
    }

    public void init ()
    {

    }

    public void start ()
    {
        for (Entity entity : entities)
        {
            entity.start();
        }
    }
}
