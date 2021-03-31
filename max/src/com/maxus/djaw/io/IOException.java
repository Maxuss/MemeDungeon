package com.maxus.djaw.io;

/**
 * Main non-handleable exception for DJaw
 */
public class IOException extends DJawException {
    public IOException(String m, Throwable e) {
        super(m, e);
    }
}
