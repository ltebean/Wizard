package com.dianping.wizard.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: ltebean
 * Date: 13-4-25
 * Time: 上午10:24
 * To change this template use File | Settings | File Templates.
 */
public class WizardExeption extends RuntimeException{

    public WizardExeption() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WizardExeption(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WizardExeption(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WizardExeption(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getMessage() {
        return super.getMessage();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Throwable getCause() {
        return super.getCause();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        return super.initCause(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return super.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
