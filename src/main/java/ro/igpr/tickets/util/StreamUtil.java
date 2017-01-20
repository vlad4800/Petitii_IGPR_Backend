package ro.igpr.tickets.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restexpress.exception.ServiceException;
import ro.igpr.tickets.config.Configuration;
import ro.igpr.tickets.config.Constants;

import java.io.*;

/**
 * Utilities to write binary files that come from InputStreams to disk
 */
public class StreamUtil {

    private static final Logger LOG = LogManager.getLogger("StreamUtil");

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
            throw new ServiceException(Constants.Messages.CANNOT_WRITE_TO_DISK);
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
            }
        }
    }

    public final static String writeFileFromStreamToDisk(Long ticketId, String originalFileName, InputStream is) {
        String extension = FilenameUtils.getExtension(originalFileName); // get file extension from name
        String newFileName = ticketId + RandomStringUtils.random(6, true, true) + "." + extension; // generate a random file name
        String folderName = Configuration.getAttachmentsPath() + File.separator + AttachmentUtil.getFolderFromFileName(newFileName);

        File theDir = new File(folderName);
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                throw new ServiceException(Constants.Messages.CANNOT_WRITE_TO_DISK);
            }
            if (result) {
                String newFilePath = folderName + File.separator + newFileName;
                StreamUtil.copyInputStreamToDisk(is, newFilePath);
            } else {
                throw new ServiceException(Constants.Messages.CANNOT_WRITE_TO_DISK);
            }
        }
        return newFileName;
    }
}
