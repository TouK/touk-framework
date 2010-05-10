/*
 * Copyright (c) (2005 - 2009) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */
package pl.touk.framework.delay;

public class DelayException extends RuntimeException {
    public DelayException() {
    }

    public DelayException(String message) {
        super(message);
    }

    public DelayException(String message, Throwable cause) {
        super(message, cause);
    }

    public DelayException(Throwable cause) {
        super(cause);
    }
}
