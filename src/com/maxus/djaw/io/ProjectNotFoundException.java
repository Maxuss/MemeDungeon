package com.maxus.djaw.io;

/**
 * Occurs when cant find requested project folder
 */
public class ProjectNotFoundException extends DJawException{
    public ProjectNotFoundException(String m, Throwable e) {
        super(m, e);
    }
}
