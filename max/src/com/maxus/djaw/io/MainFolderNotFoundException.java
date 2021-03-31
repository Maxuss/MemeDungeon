package com.maxus.djaw.io;

/**
 * Occurs when is not possible to create a new main folder <b>and</b> it doesn't exist
 */
public class MainFolderNotFoundException extends DJawException{
    public MainFolderNotFoundException(String m, Throwable e){
        super(m, e);
    }
}
