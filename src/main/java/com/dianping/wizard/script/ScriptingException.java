package com.dianping.wizard.script;

/**
 * @author ltebean
 */
public class ScriptingException extends RuntimeException{

    public ScriptingException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ScriptingException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ScriptingException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public ScriptingException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
