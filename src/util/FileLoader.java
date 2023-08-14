package util;

import java.io.*;
import java.util.logging.Logger;

public class FileLoader
{
    private final static Logger classLogger = Logger.getLogger(FileLoader.class.getName());

    public static String ReadFile (String path)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null)
            {
                builder.append(line).append(System.lineSeparator());
            }

            reader.close();
            return builder.toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
