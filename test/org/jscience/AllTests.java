package org.jscience;

import static javolution.context.Context.enter;
import static javolution.context.Context.exit;
import javolution.testing.TestSuite;

import org.jscience.mathematics.number.*;

/**
 * Entrypoint for the execution of all regression testsuites for jscience.
 * @author hps
 * @since 27.01.2009
 */
public class AllTests extends TestSuite {

    /** Here all test suites of the package org.jscience.mathematics.number should be executed. */
    @Override
    public void run() {
        new AllNumberTests().run();
    }

    /** Runs all tests and throws an Error in the case of a failure. */
    public static void main(String[] args) {
        enter(LoggingTestContext.LOGGINGTESTCONTEXT);
        try {
            new AllTests().run();
        } finally {
            exit();
        }
    }
}
