/*
 * JScience - Java(TM) Tools and Libraries for the Advancement of Sciences.
 * Copyright (C) 2006 - JScience (http://jscience.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package javax.measure.quantity;

import javax.measure.unit.ProductUnit;
import javax.measure.unit.SI;
import javax.measure.unit.Unit;

/**
 * This interface represents the dynamic viscosity. 
 * The system unit for this quantity is "Pa·s" (Pascal-Second).
 * 
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 3.0, March 2, 2006
 * @see <a href="http://en.wikipedia.org/wiki/Viscosity">
 *      Wikipedia: Viscosity</a>
 */
public interface DynamicViscosity extends Quantity {

    /**
     * Holds the SI unit (Système International d'Unités) for this quantity.
     */
    public final static Unit<DynamicViscosity> UNIT
       = new ProductUnit<DynamicViscosity>(SI.PASCAL.times(SI.SECOND));

}