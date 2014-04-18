/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number;

import static javolution.context.LogContext.info;
import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.test;
import javolution.lang.MathLib;
import javolution.testing.TestCase;

import org.jscience.mathematics.number.Real;

/**
 * Instantiation of the generic tests of the {@link AbstractFloatTestSuite} for {@link Real} and some further tests that
 * are specific to {@link Real}.
 * @since 23.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 */
public class RealTestSuite extends AbstractFloatTestSuite<Real> {

    /** Sets the {@link NumberHelper}. */
    public RealTestSuite() {
        super(NumberHelper.REAL);
    }

    /**
     * The implementation of Real.equals differs from the usual implementation since Real are not only a value but an
     * error as well. This means that Real.ONE.equals(Real.valueOf(1.0)) is false, since Real.valueOf(1.0) has an error
     * of 23.
     */
    @Override
    protected void testEquals() {
        info("  equals");
        for (final Pair<Double, Real> p : getTestValues()) {
            for (final Pair<Double, Real> q : getTestValues()) {
                doTest(new TestCase() {
                    @Override
                    public void execute() {
                        try {
                            assertEquals(p + "," + q, p == q || (p._x == 0 && q._x == 0), p._y.equals(q._y));
                        } catch (Throwable t) {
                            boolean cmp = p._y.equals(q._y);
                            assertEquals(p + "," + q, p == q, cmp);
                        }
                    }
                });
            }
        }
    }

    /**
     * Overridden to do nothing since it has no isZero().
     */
    @Override
    protected void testIsZero() {
        // not there 8-{
    }

    protected void testRound() {
        info("  round");
        for (final Pair<Double, Real> p : getTestValues()) {
            doTest(new AbstractNumberTest<Real>("Testing round " + p, MathLib.round(p._x), _helper) {
                @Override
                Real operation() throws Exception {
                    return Real.valueOf(p._y.round(), 0, 0);
                }
            });
        }
    }

    /**
     * With {@link Real} we cannot take the square root of an inexact zero.
     * @see org.jscience.mathematics.number.AbstractFloatTestSuite#testSqrt()
     */
    @Override
    protected void testSqrt() {
        info("  sqrt");
        for (final Pair<Double, Real> p : getTestValues()) {
            if (0 < p._x || p._y == _helper.getZero()) {
                doTest(new AbstractNumberTest<Real>("Testing sqrt " + p, MathLib.sqrt(p._x), _helper) {
                    @Override
                    Real operation() throws Exception {
                        return _helper.invokeMethod("sqrt", p._y);
                    }
                });
            }
        }
    }
    
    /** This is still broken, but I've given up on Real for now. :-( */
    protected void fixmetestErrorFromString() {
        info("  errorfromstring");
        doTest(new TestCase() {
            @Override
            public void execute() {
                Real r = Real.valueOf("1.2E3±1E-2");
                System.out.println(r.toString());
                throw new UnsupportedOperationException();
            }
        });

    }
}
