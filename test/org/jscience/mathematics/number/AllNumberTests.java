/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number;

import static javolution.context.Context.enter;
import static javolution.context.Context.exit;
import javolution.testing.TestSuite;

/**
 * Executes all testsuites for org.jscience.mathematics.number.
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 * @since 14.12.2008
 */
public class AllNumberTests extends TestSuite {

    /** Here all test suites of the package org.jscience.mathematics.number should be executed. */
    @Override
    public void run() {
        new Integer64TestSuite().run();
        new Float64TestSuite().run();
        new ComplexTestSuite().run();
        new ModuloIntegerTestSuite().run();
        new LargeIntegerTestSuite().run();
        new RationalTestSuite().run();
        new FloatingPointTestSuite().run();
        new RealTestSuite().run();
    }

    /** Executes all testsuites as regression tests. */
    public static void main(String[] args) {
        // enter(REGRESSION);
        enter(LoggingTestContext.LOGGINGTESTCONTEXT);
        try {
            new AllNumberTests().run();
        } finally {
            exit();
        }
    }

}
