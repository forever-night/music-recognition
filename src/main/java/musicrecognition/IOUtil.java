package musicrecognition;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class IOUtil {
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8129];     // 8 kB, by default size of buffer
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) > 0)
            outputStream.write(buffer, 0, bytesRead);

        return outputStream.toByteArray();
    }
}
