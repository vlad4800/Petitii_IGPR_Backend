/*
 * Copyright (c) 2015. Nephos6 Inc.
 */

package ro.igpr.tickets.persistence.types;

/**
 * Created by Vlad on 14-Dec-14.
 */
public enum TokenType {
    device,
    bearer;

    public static String[] names() {

        TokenType[] tokenTypes = values();
        String[] types = new String[tokenTypes.length];
        for (int i = 0; i < tokenTypes.length; i++) {
            types[i] = tokenTypes[i].name();
        }
        return types;
    }

}
