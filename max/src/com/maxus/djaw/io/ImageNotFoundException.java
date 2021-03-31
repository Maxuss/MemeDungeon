package com.maxus.djaw.io;

/**
 * Occurs when trying to refer to image that does not exists or is outside of src folder
 */
public class ImageNotFoundException extends DJawException{
    public ImageNotFoundException(String m, Throwable e){
        super(m, e);
    }
}
