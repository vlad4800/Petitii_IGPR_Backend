/*
 * Copyright (c) 2015. Nephos6 Inc.
 */

package ro.igpr.tickets.util;

import ro.igpr.tickets.config.Constants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

public final class AuthUtil {

    /**
     * Generates a SHA1 string to be used as an access token for future API calls
     *
     * @param entityId
     * @return
     */
    public final static String generateAPIToken(String entityId) {

        return encryptPassword(entityId.concat(Constants.Tokens.SEPARATOR).concat(UUID.randomUUID().toString()), Constants.Tokens.ALGORITHM_SHA1);
    }

    private static final String encryptPassword(final String password) {
        return encryptPassword(password, Constants.Tokens.ALGORITHM_SHA1);
    }

    private static final String encryptPassword(String password, final String algorithm) {
        String sha1 = "";
        try {
            final MessageDigest crypt = MessageDigest.getInstance(algorithm);
            crypt.reset();
            crypt.update(password.getBytes(Constants.Tokens.UTF8));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return sha1;
    }

    private static final String byteToHex(final byte[] hash) {
        final Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format(Constants.Tokens.P02X, b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
