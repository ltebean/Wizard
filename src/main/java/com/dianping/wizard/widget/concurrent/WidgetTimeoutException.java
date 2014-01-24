package com.dianping.wizard.widget.concurrent;

/**
 * @author ltebean
 */
public class WidgetTimeoutException extends RuntimeException{

    public WidgetTimeoutException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WidgetTimeoutException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WidgetTimeoutException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WidgetTimeoutException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

}
