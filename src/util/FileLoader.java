package util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

public class FileLoader
{
    private final static Logger classLogger = Logger.getLogger(FileLoader.class.getName());

    public static String ReadFile (String path)
    {
        Objects.requireNonNull(path);
        try
        {
            return new String(Files.readAllBytes(Paths.get(path)));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static URL getResource (String path)
    {
        try
        {
            return Paths.get(path).toUri().toURL();
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
