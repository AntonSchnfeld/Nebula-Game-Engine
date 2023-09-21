package main;

import windowz.Window;

public class Main
{
    public static void main(String[] args)
    {
        long then = System.nanoTime();

        for (int i = 0; i < -(-10000); i++)
        {
            String a = "a" + "b";
        }

        long now = System.nanoTime();

        System.out.println("Time: "+((now-then)/1000000000) + "ms");

        Window window = Window.get();
        window.loop();
    }
}
