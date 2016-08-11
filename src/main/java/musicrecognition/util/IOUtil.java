package musicrecognition.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class IOUtil {
    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8129];     // 8 kB
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) > 0)
            outputStream.write(buffer, 0, bytesRead);

        return outputStream.toByteArray();
    }
    
    /**
     * Gets a specified number of bytes from an input stream.
     *
     * @param limit maximum number of bytes read
     * */
    public static byte[] inputStreamToByteArray(InputStream inputStream, int limit) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8129];     // 8 kB
        int bytesRead, total = 0;
        
        while(total < limit - buffer.length && (bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
            total += bytesRead;
        }
        
        return outputStream.toByteArray();
    }
}
