package ro.igpr.tickets.util;

import java.io.*;

/**
 * Created by vlad4800@gmail.com on 19-Jan-17.
 */
public class StreamUtil {


    public static final void copyInOutStream(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[8192];
        int len;

        while ((len = input.read(buffer, 0, buffer.length)) >= 0) {
            output.write(buffer, 0, len);
        }
    }

    public static final void copyInputStreamToDisk(InputStream is, String target) {
        OutputStream os = null;
        try {
            File targetFile = new File(target);
            os = new FileOutputStream(targetFile);
            StreamUtil.copyInOutStream(is, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
