package ro.igpr.tickets.util;

import ro.igpr.tickets.config.Configuration;

/**
 * Created by vlad4800@gmail.com on 19-Jan-17.
 */
public class AttachmentUtil {

    public final static String getFolderFromFileName(String fileName) {
        return fileName.substring(0, 3);
    }

    public final static String getUrlFromFileName(String fileName) {
        return Configuration.getAttachmentsUrl() + "/" + AttachmentUtil.getFolderFromFileName(fileName) + "/" + fileName;
    }
}
