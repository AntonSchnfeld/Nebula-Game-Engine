package renderz.shaderz;

import interfacez.Bindable;
import interfacez.Disposable;
import org.joml.Matrix4f;

import java.util.HashMap;

import static org.lwjgl.opengl.GL30C.*;


public class ShaderProgram implements Disposable, Bindable
{
    private static ShaderProgram boundShader;
    private final FragmentShader fragment;
    private final VertexShader vertex;
    private final int id;
    private final HashMap<String, Integer> uniformPositions;
    private String key;

    private final static HashMap<String, ShaderProgram> shaderPrograms = new HashMap<>();
    private final static ShaderProgram defaultProgram = new ShaderProgram("assets/default.vert", "assets/default.frag");
    static
    {
        shaderPrograms.put(defaultProgram.key, defaultProgram);
    }

    private ShaderProgram (VertexShader vertex, FragmentShader fragment)
    {
        this.vertex = vertex;
        this.fragment = fragment;

        this.id = glCreateProgram();

        uniformPositions = new HashMap<>();

        init();
    }

    private ShaderProgram (String vertexFilePath, String fragmentFilePath)
    {
        vertex = VertexShader.createVertexShader(vertexFilePath);
        fragment = FragmentShader.createFragmentShader(fragmentFilePath);

        this.id = glCreateProgram();

        uniformPositions = new HashMap<>();

        init();
    }

    public static ShaderProgram getDefaultShaderProgram()
    {
        return defaultProgram;
    }

    private void init ()
    {
        glAttachShader(id, vertex.getId());
        glAttachShader(id, fragment.getId());

        int success = glGetProgrami(id, GL_ATTACHED_SHADERS);
        if (success == GL_FALSE)
        {
            String errorMessage = glGetProgramInfoLog(id);

            throw new ShaderAttachmentException(errorMessage);
        }

        glLinkProgram(id);

        success = glGetProgrami(id, GL_LINK_STATUS);
        if (success == GL_FALSE)
        {
            String errorMessage = glGetProgramInfoLog(id);

            throw new ShaderLinkException(errorMessage);
        }

        vertex.dispose();
        fragment.dispose();
    }

    public void bind ()
    {
        glUseProgram(id);
        boundShader = this;
    }

    public void unbind ()
    {
        if (boundShader == this) boundShader = null;
        glUseProgram(0);
    }

    public void dispose ()
    {
        if (boundShader == this) boundShader = null;
        glDeleteProgram(id);
        shaderPrograms.remove(key);
    }

    public int getUniformLocation (String uniform)
    {
        if (uniformPositions.containsKey(uniform))
        {
            return uniformPositions.get(uniform);
        }

        bind();
        final int loc = glGetUniformLocation(id, uniform);
        uniformPositions.put(uniform, loc);
        return loc;
    }

    public void uploadUniform1f (String uniform, float value)
    {
        final int loc = getUniformLocation(uniform);
        glUniform1f(loc, value);
    }

    public void uploadUniform1i (String uniform, int value)
    {
        final int loc = getUniformLocation(uniform);
        glUniform1i(loc, value);
    }

    public void uploadUniformTexture2D (String uniform, int slot)
    {
        final int loc = getUniformLocation(uniform);
        glUniform1i(loc, slot);
    }

    public void uploadMat4f(String uniform, Matrix4f value)
    {
        int loc = getUniformLocation(uniform);
        glUniformMatrix4fv(loc, false, value.get(new float[16]));
    }

    public static ShaderProgram createShaderProgram (String vertexFilePath, String fragmentFilePath)
    {
        final String key = vertexFilePath + fragmentFilePath;

        if (shaderPrograms.containsKey(key))
        {
            return shaderPrograms.get(key);
        }

        ShaderProgram newProgram = new ShaderProgram(vertexFilePath, fragmentFilePath);
        shaderPrograms.put(key, newProgram);
        newProgram.key = key;
        return newProgram;
    }

    public static ShaderProgram createShaderProgram (VertexShader vertex, FragmentShader fragment)
    {
        final String key = vertex.getFilePath() + fragment.getFilePath();

        if (shaderPrograms.containsKey(key))
        {
            return shaderPrograms.get(key);
        }

        ShaderProgram newProgram = new ShaderProgram(vertex, fragment);
        shaderPrograms.put(key, newProgram);
        newProgram.key = key;
        return newProgram;
    }

    /**
     * @return the currently bound shader
     */
    public static ShaderProgram getBoundShader ()
    {
        return boundShader;
    }
}
