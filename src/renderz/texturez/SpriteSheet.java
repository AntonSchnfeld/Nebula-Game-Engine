package renderz.texturez;

import entitiez.componentz.Sprite;
import interfacez.Disposable;

public class SpriteSheet implements Disposable
{
     private final Sprite[] sprites;
     private final int rows, columns;

     public SpriteSheet (Texture2D texture2D, int rows, int columns)
     {
         this.rows = rows;
         this.columns = columns;

         this.sprites = new Sprite[rows * columns];

         for (int i = 0; i < sprites.length; i++)
         {
             Sprite sprite = sprites[i];

             float wOffset = (1f / rows) * i;
             float hOffset = (1f / columns) * i;

             sprite = new Sprite(texture2D, new float[]
                     {
                             
                     });
         }
     }

     public Sprite get (int index)
     {
         return sprites[index];
     }

     @Override
     public void dispose ()
     {
         for (Sprite sprite : sprites)
         {
             sprite.dispose();
         }
     }
}
