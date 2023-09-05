package entitiez;

import interfacez.Disposable;
import interfacez.Starteable;
import interfacez.Updateable;

public abstract class Component implements Updateable, Starteable, Disposable
{
    public Entity entity;
    public void update (float dt) {}
    public abstract <T extends Component> T copy();
}
