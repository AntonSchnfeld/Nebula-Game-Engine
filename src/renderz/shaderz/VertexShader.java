package renderz.shaderz;

import interfacez.Disposable;
import util.FileLoader;

import java.util.HashMap;

import static org.lwjgl.opengl.GL30C.*;

public class VertexShader implements Shader, Disposable
{
    private final String src, filePath;
    private final int id;
    private final static HashMap<String, VertexShader> vertexShaders = new HashMap<>();

    private VertexShader (String path)
    {
        id = glCreateShader(GL_VERTEX_SHADER);

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

    public void dispose ()
    {
        glDeleteShader(id);
        vertexShaders.remove(filePath);
    }

    public String getSource ()
    {
        return src;
    }

    public String getFilePath ()
    {
        return filePath;
    }

    public static VertexShader createVertexShader (String path)
    {
        if (vertexShaders.containsKey(path))
        {
            return vertexShaders.get(path);
        }

        VertexShader newVertex = new VertexShader(path);
        vertexShaders.put(path, newVertex);
        return newVertex;
    }
}
