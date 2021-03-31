package com.maxus.djaw.io;

/**
 * Exception that occurs when something is broken inside DJaw source files.
 */
public class FatalDjawBreakException extends DJawException{
    public FatalDjawBreakException(String m, Throwable e){
        super(m, e);
    }
}
