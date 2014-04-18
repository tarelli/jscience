/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number;

import javolution.testing.AssertionException;
import javolution.testing.TestCase;
import javolution.testing.TestContext;

/**
 * A {@link TestContext} that also prints messages to {@link System#out}.
 * @since 23.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 */
public class LoggingTestContext extends TestContext {

    private static final long serialVersionUID = 6442141685516583299L;

    /** The instance of the context. */
    public static final Class<LoggingTestContext> LOGGINGTESTCONTEXT = LoggingTestContext.class;

    /**
     * Execution of the testcase.
     * @see javolution.testing.TestContext#doTest(javolution.testing.TestCase)
     */
    @Override
    public void doTest(TestCase testCase) {
        testCase.prepare();
        try {
            testCase.execute();
            testCase.validate();
        } finally {
            testCase.cleanup();
        }
    }

    /**
     * Throws an {@link AssertionException} if the comparison fails.
     * @see javolution.testing.TestContext#doAssertEquals(java.lang.String, java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean doAssertEquals(String message, Object expected, Object actual) {
        if (((expected == null) && (actual != null)) || ((expected != null) && (!expected.equals(actual)))) { throw new AssertionException(
                message, expected, actual); }
        return true;
    }

    /**
     * @see javolution.context.LogContext#isInfoLogged()
     */
    @Override
    public boolean isInfoLogged() {
        return true;
    }

    /**
     * Logs to {@link System#out}.
     * @see javolution.context.LogContext#logInfo(java.lang.CharSequence)
     */
    @Override
    public void logInfo(CharSequence message) {
        System.out.print("[info] ");
        System.out.println(message);
    }

    /**
     * @see javolution.context.LogContext#isWarningLogged()
     */
    @Override
    public boolean isWarningLogged() {
        return true;
    }

    /**
     * Logs to {@link System#out}.
     * @see javolution.context.LogContext#logWarning(java.lang.CharSequence)
     */
    @Override
    public void logWarning(CharSequence message) {
        System.out.print("[warning] ");
        System.out.println(message);
    }

    /**
     * @see javolution.context.LogContext#isErrorLogged()
     */
    @Override
    public boolean isErrorLogged() {
        return true;
    }

    /**
     * Logs to {@link System#out}.
     * @see javolution.context.LogContext#logError(java.lang.Throwable, java.lang.CharSequence)
     */
    @Override
    public void logError(Throwable error, CharSequence message) {
        System.out.print("[error] ");
        if (error != null) {
            System.out.print(error.getClass().getName());
            System.out.print(" - ");
        }
        String description = (message != null) ? message.toString() : (error != null) ? error.getMessage() : "";
        System.out.println(description);
        if (error != null) {
            error.printStackTrace();
        }
    }

}
