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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.TreeMap;

import org.jscience.mathematics.number.Number;

import javolution.testing.TestCase;
import javolution.testing.TestContext;
import javolution.testing.TestSuite;

/**
 * Base class for test suites that run all methods starting with "test" to create the actual tests. This is advisable
 * iff there are many tests - so one cannot forget to call any of them.
 * @since 22.12.2008
 * @author <a href="http://www.stoerr.net/">Hans-Peter Störr</a>
 */
public abstract class AbstractTestSuite extends TestSuite {

    /**
     * Calls all methods in the class that start with "test", just like in JUnit. This way we cannot forget any of them. <br>
     * This is declared final such that this is not accidentially overridden.
     */
    @Override
    public final void run() {
        info(getClass().toString());
        runTestMethods();
    }

    /**
     * Runs all methods of this class that start with "test" by reflection. All methods starting with "test" must have
     * noarguments.
     */
    protected void runTestMethods() {
        Map<String, Method> testMethods = new TreeMap<String, Method>();
        Class<?> clazz = getClass();
        while (null != clazz) {
            for (Method m : clazz.getDeclaredMethods()) {
                final String name = m.getName();
                if (name.startsWith("test") && !name.equals("test") && !testMethods.containsKey(name)) {
                    testMethods.put(name, m);
                }
            }
            clazz = clazz.getSuperclass();
        }
        for (Map.Entry<String, Method> e : testMethods.entrySet()) {
            try {
                // info(getClass() + " invoking " + e.getValue());
                e.getValue().invoke(this);
            } catch (IllegalAccessException e1) {
                throw new RuntimeException(e1.toString(), e1);
            } catch (InvocationTargetException e1) {
                throw new RuntimeException(e1.toString(), e1.getTargetException());
            }
        }
    }
    
    /** Gives the test to the {@link TestContext}. This method is here to provide a hook to do other things here. */
    protected void doTest(TestCase t) {
        TestContext.test(t);
    }

}
