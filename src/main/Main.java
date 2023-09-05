package main;

import windowz.Window;

public class Main
{
    public static void main(String[] args)
    {
        int a = 0;
        System.out.println(++a);
        System.out.println(a);
        Window window = Window.get();
        window.loop();
    }
}
