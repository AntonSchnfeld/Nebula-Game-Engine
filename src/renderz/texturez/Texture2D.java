package renderz.texturez;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL30C.*;

/**
 * The <code>renderz.texturez.Texture2D</code> class is meant to abstract over two-dimensional textures in <code>OpenGL</code>
 */
public class Texture2D
{
    private final int id; // Texture id for OpenGL operations
    private final IntBuffer width, height; // Width and height of image
    private final IntBuffer nrChannels;

    /**
     * Constructs a new mipmapped <code>renderz.texturez.Texture2D</code> object.
     * @param filepath the path to the image file
     * @param alpha if the image has alpha
     */
    public Texture2D(String filepath)
    {
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        // Set texture parameters
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT); // Repeat texture when stretched
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        // load and generate the texture
        width = BufferUtils.createIntBuffer(1);
        height = BufferUtils.createIntBuffer(1);
        nrChannels = BufferUtils.createIntBuffer(1);
        ByteBuffer data = stbi_load(filepath, width, height, nrChannels, 4);

        if (data != null)
        {
            final int colorMode = GL_RGBA;

            glTexImage2D(GL_TEXTURE_2D, 0, colorMode, width.get(), height.get(), 0, colorMode, GL_UNSIGNED_BYTE, data);
            stbi_image_free(data);
        }
        else
        {
            throw new TextureLoadingException("Could not load texture from path " + filepath);
        }
    }

    public void use (int slot)
    {
        if (slot >= 0 && slot <= 31)
        {
            glBindTexture(GL_TEXTURE_2D, id);
            glActiveTexture(GL_TEXTURE0+slot);
            return;
        }
        throw new IllegalArgumentException("slot is either smaller than 0 or larger than 31");
    }

    /**
     * @return the <code>OpenGL</code>> texture object id for this <code>renderz.texturez.Texture2D</code>
     */
    public int getId ()
    {
        return id;
    }

    /**
     * @return the Height of the <code>renderz.texturez.Texture2D</code>>
     */
    public int getHeight ()
    {
        return height.get();
    }

    /**
     * @return the Width of the <code>renderz.texturez.Texture2D</code>>
     */
    public int getWidth ()
    {
        return width.get();
    }

    /**
     * Deletes the <code>renderz.texturez.Texture2D</code> from the <code>GPU</code>
     */
    public void delete ()
    {
        glDeleteTextures(id);
    }
}
