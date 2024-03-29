/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * exception class to wrap
 */
package com.jsonflare.lib.jsonflare.common.exceptions;

/**
 * Author: NUWAN
 * Date: 2024-01-06
 * Description:
 * json flare exception
 */
public class JsonFlareException extends Exception {

    /**
     * Adding a run time exception wrapper for the exception wrapping
     *
     * @param message   custom string error message
     * @param exception exception which originally throws
     */
    public JsonFlareException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * Adding a run time exception wrapper for the exception wrapping
     *
     * @param message custom string error message
     */
    public JsonFlareException(String message) {
        super(message);
    }

}
