package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.assertTrue;

import java.util.Arrays;
import java.util.List;

import javolution.testing.TestCase;

/**
 * Testsuite for {@link Complex}. It inherits many tests from {@link AbstractFloatTestSuite} that test the operations
 * for {@link Complex} numbers with zero real value - these are basically simple smoketests. <br>
 * TODO: more tests are needed.
 * @author hps
 * @since 02.02.2009
 */
public class ComplexTestSuite extends AbstractFloatTestSuite<Complex> {

    protected static final double EPSILON = 1e-14;
    private static final Complex[] TESTVALUES = new Complex[] { Complex.valueOf(3.2, 1.7), Complex.valueOf(-3.72, 2.8),
            Complex.valueOf(-95.3, -4.2), Complex.valueOf(0.3, -4.2), Complex.valueOf(0, 1.7), Complex.valueOf(0, -5),
            Complex.valueOf(3, 0), Complex.valueOf(-42, 0) };

    ComplexTestSuite() {
        super(NumberHelper.COMPLEX);
    }

    @Override
    protected void testAbs() {
        // Complex has no abs, which is a bug in itself. 8-)
    }

    @Override
    protected void testIsNegative() {
        // not implemented.
    }

    @Override
    protected void testIsPositive() {
        // not implemented.
    }

    @Override
    protected void testIsZero() {
        // not implemented.
    }

    protected List<Complex> getComplexTestvalues() {
        return Arrays.asList(TESTVALUES);
    }

    protected void testEquals2() {
        info("  equals2");
        for (final Complex c : getComplexTestvalues()) {
            for (final Complex d : getComplexTestvalues()) {
                doTest(new TestCase() {
                    @Override
                    public void execute() {
                        if (c == d) {
                            assertTrue(c.toString() + ", " + d.toString(), c.equals(d, EPSILON));
                        } else {
                            assertTrue(c.toString() + ", " + d.toString(), !c.equals(d, EPSILON));
                        }
                    }
                });
            }
        }
    }

    /** Compares c and d and asserts that the difference or the relative difference is less than EPSILON */
    protected void assertNear(Complex a, Complex b) {
        double d = a.minus(b).magnitude();
        assertTrue(a + " versus " + b + " differ by " + d, d < EPSILON || d / a.magnitude() < EPSILON);
    }

    protected void testToString2() {
        info("  toString2 ");
        for (final Complex c : getComplexTestvalues()) {
            doTest(new TestCase() {
                @Override
                public void execute() {
                    Complex cn = Complex.valueOf(c.toString());
                    assertNear(cn, c);
                }
            });
        }
    }

    /**
     * We execute quite a lot of operations in one go and compare with the known result. Not the best test, but quick to
     * write.
     */
    protected void testOperationChain() {
        info("  operationChain");
        doTest(new TestCase() {
            @Override
            public void execute() {
                Complex m = Complex.valueOf(0.8, 0.8);
                Complex s = Complex.ZERO;
                Complex f = Complex.ONE;
                for (int i = 0; i < 10; ++i) {
                    s = s.plus(f);
                    f = f.times(m);
                }
                final Complex scmp = m.pow(10).minus(Complex.ONE).divide(m.minus(Complex.ONE));
                assertTrue(s.toString(), s.equals(scmp, EPSILON));
                m = m.conjugate();
                Complex mi = Complex.ONE;
                for (int i = 0; i < 10; ++i) {
                    mi = mi.times(m);
                    s = s.minus(f.divide(mi));
                }
                assertTrue(s.toString(), s.equals(Complex.valueOf(scmp.getReal(), 0).times(2), EPSILON));
            }
        });
    }

    protected void testPow2() {
        info("  pow2");
        for (final Complex c : getComplexTestvalues()) {
            doTest(new TestCase() {
                @Override
                public void execute() {
                    Complex cx = c.times(c).times(c);
                    assertNear(cx, c.pow(3));
                    assertNear(cx, c.pow(3.0));
                    assertNear(cx, c.pow(Complex.valueOf(3, 0)));
                }
            });
        }
    }

    protected void testExponentials() {
        info("  exponentials");
        for (final Complex c : getComplexTestvalues()) {
            for (final Complex d : getComplexTestvalues()) {
                doTest(new TestCase() {
                    @Override
                    public void execute() {
                        Complex cx = c.pow(d);
                        Complex cy = c.log().times(d).exp();
                        assertNear(cx, cy);
                    }
                });
            }
        }
    }

    protected void testSqrt2() {
        info("  sqrt2");
        for (final Complex c : getComplexTestvalues()) {
            doTest(new TestCase() {
                @Override
                public void execute() {
                    assertNear(c, c.sqrt().times(c.sqrt()));
                }
            });
        }
    }
}
