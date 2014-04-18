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
import static javolution.testing.TestContext.assertTrue;
import static javolution.testing.TestContext.test;

import java.util.List;

import javolution.context.LocalContext;
import javolution.lang.MathLib;
import javolution.testing.TestCase;
import javolution.testing.TestContext;

/**
 * Instantiation of the generic tests of the {@link AbstractFloatTestSuite} for {@link FloatingPoint} and some further
 * tests that are specific to {@link FloatingPoint}. <br>
 * We omit getExponent, getSignificand, times(long) since these are trivial.
 * @since 23.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 */
public class FloatingPointTestSuite extends AbstractFloatTestSuite<FloatingPoint> {

    /** Sets the needed helper class. */
    public FloatingPointTestSuite() {
        super(NumberHelper.FLOATINGPOINT);
    }

    /**
     * We add a couple of values with different precision.
     * @see org.jscience.mathematics.number.AbstractFloatTestSuite#initTestValues(java.util.List)
     */
    @Override
    protected void initTestValues(List<Pair<Double, FloatingPoint>> values) {
        super.initTestValues(values);
        values.add(Pair.make(0.7234938, FloatingPoint.valueOf("0.7234938")));
        values.add(Pair.make(0.7234938, FloatingPoint.valueOf("0.72349380000000000000000000000000000000")));
    }

    protected void testConstants() {
        info(" constants");
        doTest(new TestCase() {
            @Override
            public void execute() {
                assertEquals(FloatingPoint.valueOf(1), FloatingPoint.ONE);
                assertEquals(FloatingPoint.valueOf(0), FloatingPoint.ZERO);
                assertTrue(FloatingPoint.NaN.isNaN());
            }
        });        
    }
    
    protected void testRound() {
        info("  round");
        for (final Pair<Double, FloatingPoint> p : getTestValues()) {
            doTest(new AbstractNumberTest<FloatingPoint>("Testing round " + p, MathLib.round(p._x), _helper) {
                @Override
                FloatingPoint operation() throws Exception {
                    final LargeInteger rounded = p._y.round();
                    return FloatingPoint.valueOf(rounded, 0);
                }
            });
        }
    }
    
    protected void testSetDigits() {
        info("  setDigits");
        for (final Pair<Double, FloatingPoint> p : getTestValues()) {
            doTest(new TestCase() {
                @Override
                public void execute() {
                    FloatingPoint v1 = FloatingPoint.valueOf(0.123);
                    try {
                        LocalContext.enter();
                        FloatingPoint.setDigits(50);
                        FloatingPoint v2 = v1.inverse();
                        final int dl = v2.getSignificand().digitLength();
                        TestContext.assertTrue("" + dl, 50 == dl);
                    } finally {
                        LocalContext.exit();
                    }
                    // now we should have a different digitlength
                    FloatingPoint v2 = v1.inverse();
                    final int dl = v2.getSignificand().digitLength();
                    TestContext.assertTrue("" + dl, 50 != dl);
                }
            });
        }
    }
}
