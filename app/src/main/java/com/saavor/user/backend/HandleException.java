package com.saavor.user.backend;

/**
 * Created by inderbagga on 26/06/16.
 */
public class HandleException {

    private String errorMessage;
    private int errorCode=99;

    public HandleException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
