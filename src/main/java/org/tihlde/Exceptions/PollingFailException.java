package org.tihlde.Exceptions;

/**
 * Created by bjorn on 5/5/14.
 */
public class PollingFailException extends Exception {

    public PollingFailException() {};

    public PollingFailException(String message) {
        super(message);
    }
}
