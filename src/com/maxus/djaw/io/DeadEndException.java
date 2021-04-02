package com.maxus.djaw.io;

/**
 * Occurs when its taking too long to access files
 */
public class DeadEndException extends DJawException{
    public DeadEndException(String m, Throwable e){
        super(m, e);
    }
}
