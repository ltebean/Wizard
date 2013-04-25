package com.dianping.wizard.exception;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-24
 * Time: 上午8:57
 * To change this template use File | Settings | File Templates.
 */
public class WidgetException extends RuntimeException {

    public WidgetException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WidgetException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WidgetException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WidgetException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
