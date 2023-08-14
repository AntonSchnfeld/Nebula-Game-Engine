package renderz.shaderz;

public interface Shader
{
    public String getSource();
    public String getFilePath();
    public int getId();
    public void delete();
}
