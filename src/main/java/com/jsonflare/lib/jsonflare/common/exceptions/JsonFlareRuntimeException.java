/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * exception class to wrap
 */
package com.jsonflare.lib.jsonflare.common.exceptions;

public class JsonFlareRuntimeException extends RuntimeException {

    /**
     * Adding a run time exception wrapper for the exception wrapping
     *
     * @param message   custom string error message
     * @param exception exception which originally throws
     */
    public JsonFlareRuntimeException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * Adding a run time exception wrapper for the exception wrapping
     *
     * @param message custom string error message
     */
    public JsonFlareRuntimeException(String message) {
        super(message);
    }

}
