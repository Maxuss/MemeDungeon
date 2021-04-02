package com.maxus.djaw.io;

import com.maxus.djaw.DJaw;

/**
 * Main class for exceptions. Used by all other DJaw exceptions
 */
public class DJawException extends RuntimeException{
    public DJawException(String m, Throwable e) {
        super(m, e);
        DJaw.DJMessage("A DJaw exception has occurred! Error:" + m, 3);
    }
}
