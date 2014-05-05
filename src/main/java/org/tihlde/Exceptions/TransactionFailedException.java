package org.tihlde.Exceptions;

/**
 * Created by bjorn on 5/4/14.
 */
public class TransactionFailedException extends Exception {
    public TransactionFailedException() {};

    public TransactionFailedException(String message) {
        super(message);
    }
}
