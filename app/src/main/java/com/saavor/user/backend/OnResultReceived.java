package com.saavor.user.backend;

/**
 * Created by inderbagga on 10/12/16.
 */

public interface OnResultReceived {

    public void dispatchString(RequestSource from, String what);
}
