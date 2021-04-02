package com.maxus.djaw.io;

/**
 * Occurs when file data is corrupted or isn't available for parsing
 */
public class DeadDataException extends DJawException{
    public DeadDataException(String m, Throwable e){
        super(m, e);
    }
}
