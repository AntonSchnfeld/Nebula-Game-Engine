package renderz.shaderz;

import interfacez.Disposable;
import util.FileLoader;

import java.util.HashMap;

import static org.lwjgl.opengl.GL30C.*;

public class FragmentShader implements Shader, Disposable
{
    private final int id;
    private final String src, filePath;
    private static final HashMap<String, FragmentShader> fragmentShaders = new HashMap<>();

    private FragmentShader (String path)
    {
        id = glCreateShader(GL_FRAGMENT_SHADER);

        src = FileLoader.ReadFile(path);
        filePath = path;

        glShaderSource(id, src);
        glCompileShader(id);

        final int success = glGetShaderi(id, GL_COMPILE_STATUS);
        if (success == GL_FALSE)
        {
            throw new ShaderCompileException(glGetShaderInfoLog(id));
        }
    }

    public int getId ()
    {
        return id;
    }

    public String getFilePath ()
    {
        return filePath;
    }

    public String getSource ()
    {
        return src;
    }

    public static FragmentShader createFragmentShader (String path)
    {
        if (fragmentShaders.containsKey(path))
        {
            return fragmentShaders.get(path);
        }

        FragmentShader newFragment = new FragmentShader(path);
        fragmentShaders.put(path, newFragment);
        return newFragment;
    }

    public void dispose ()
    {
        glDeleteShader(id);
        fragmentShaders.remove(filePath);
    }
}
