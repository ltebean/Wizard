package com.dianping.wizard.repo;

/**
 * @author ltebean
 */
public class WizardRepoException extends RuntimeException {
    public WizardRepoException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WizardRepoException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WizardRepoException(String message, Throwable cause) {
        super(message, cause);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public WizardRepoException(Throwable cause) {
        super(cause);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
