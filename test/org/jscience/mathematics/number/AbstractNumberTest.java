/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2007 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package org.jscience.mathematics.number;

import static javolution.testing.TestContext.assertEquals;
import static javolution.testing.TestContext.assertTrue;

import java.lang.reflect.InvocationTargetException;

import javolution.lang.MathLib;
import javolution.testing.TestCase;
import javolution.testing.TestContext;

import org.jscience.mathematics.number.Number;

/**
 * A testcase that executes an operation and compares the {@link Number#doubleValue()} of the result with a given
 * expected result. If the expected result is 0 we check that the result should be less than {@link #EPSILON}, otherwise
 * the quotient should differ by at most {@link #EPSILON} from 1.
 * @since 22.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 * @param <T> the type of number to test
 */
public abstract class AbstractNumberTest<T extends Number<T>> extends TestCase {

    /** The maximum allowable relative difference of the result from the expected result. */
    protected double EPSILON = 1e-9;

    final double _expected;
    final NumberHelper<T> _helper;
    final String _description;
    T _value;
    Exception _exception;

    /**
     * Sets the expected values.
     * @param description a description that can be printed in failures etc.
     * @param helper helper to be used in the test
     */
    public AbstractNumberTest(String description, double expected, NumberHelper<T> helper) {
        _expected = expected;
        _helper = helper;
        _description = description;
    }

    /**
     * Calls {@link #operation()} and catches Exceptions for later validation.
     * @see javolution.testing.TestCase#execute()
     */
    @Override
    public final void execute() {
        try {
            _value = operation();
        } catch (InvocationTargetException e) {
            _exception = (Exception) e.getTargetException();
        } catch (Exception e) {
            _exception = e;
        }
    }

    /**
     * Checks that there was no exception and that the result is approximately equal to the expected result.
     * @see javolution.testing.TestCase#validate()
     */
    @Override
    public final void validate() {
        super.validate();
        if (null != _exception) {
            _exception.printStackTrace();
        }
        assertEquals(getDescription().toString(), null, _exception);
        assertTrue(getDescription() + ": no value received", null != _value);
        compareresult();
    }

    /**
     * Compares {@link #_value} and {@link #_expected} after normalizing them with {@link #_suite}'s
     * {@link AbstractNumberTestSuite#normalize(Number)} or {@link AbstractNumberTestSuite#normalizeExpected(double)}.
     * The result of the comparison is {@link TestContext#assertTrue(String, boolean)}ed.
     */
    void compareresult() {
        final double result = _value.doubleValue();
        final double expected = _expected;
        if (0 == _expected) {
            assertTrue(getDescription().toString() + " but got " + result, EPSILON > MathLib.abs(result));
        } else {
            assertTrue(getDescription().toString() + " but got " + result,
                    EPSILON > MathLib.abs(result / expected - 1));
        }
    }

    /**
     * @see javolution.testing.TestCase#getDescription()
     */
    @Override
    public CharSequence getDescription() {
        return _description + " expecting " + _expected;
    }

    /** Should return the value of the operation to test and set _expected to the expected value. */
    abstract T operation() throws Exception;
}
