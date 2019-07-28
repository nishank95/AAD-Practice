/*
 * Copyright 2018, Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.SimpleCalc;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * JUnit4 unit tests for the calculator logic. These are local unit tests; no device needed
 */
@RunWith(JUnit4.class)
@SmallTest
public class CalculatorTest {

    private Calculator mCalculator;

    /**
     * Set up the environment for testing
     */
    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    /**
     * Test for simple addition
     */
    @Test
    public void addTwoNumbers() {
        double resultAdd = mCalculator.add(1d, 1d);
        assertThat(resultAdd, is(equalTo(2d)));
    }

    // Why not just add more assertions to addTwoNumbers()? Grouping more than one assertion
    // into a single method can make your tests harder to debug if only one assertion fails,
    // and obscures the tests that do succeed. The general rule for unit tests is to provide
    // a test method for every individual assertion.
    @Test
    public void addTwoNumbersNegative() {
        double resultAdd = mCalculator.add(-1d, 2d);
        assertThat(resultAdd, is(equalTo(1d)));
    }

    // The add() method is defined with parameters of type double, so you can call it with a
    // float type, and that number is promoted to a double.
    @Test
    public void addTwoNumbersFloats() {
        double resultAdd = mCalculator.add(1.111f, 1.111d);
        // Does not check the closeness to the value
        // assertThat(resultAdd, is(equalTo(2.222d)));

        // With the closeTo() matcher, rather that testing for exact equality you can test for
        // equality within a specific delta.
        assertThat(resultAdd, is(closeTo(2.222, 0.01)));

    }
    /*
    In this particular case both input arguments to the add() method from the SimpleCalc app will always be type    double, so this is an arbitrary and unrealistic test. However, if your app was written such that the input      to    the add() method could be either double or float, and you only care about some precision, you need to     provide    some wiggle room to the test so that "close enough" counts as a success.
    **/

    /*
    Add a unit test called subTwoNumbers() that tests the sub() method.
    * */
    @Test
    public void subTwoNumbers() {
        double resultSub = mCalculator.sub(3d, 1d);
        assertThat(resultSub, is(equalTo(2d)));
    }

    /*
    Add a unit test called subWorksWithNegativeResults() that tests the sub() method where
    the given calculation results in a negative number.
    * */
    @Test
    public void subWorksWithNegativeResults() {
        double resultSub = mCalculator.sub(3d, 9d);
        assertThat(resultSub, is(equalTo(-6d)));
    }

    /*
    Add a unit test called mulTwoNumbers() that tests the mul() method.
    * */
    @Test
    public void mulTwoNumbers() {
        double resultMul = mCalculator.mul(3d, 9d);
        assertThat(resultMul, is(equalTo(27d)));
    }

    /*
    Add a unit test called mulTwoNumbersZero() that tests the mul() method with at least
    one argument as zero.
    * */
    @Test
    public void mulTwoNumbersZero() {
        double resultMul = mCalculator.mul(0d, 9d);
        assertThat(resultMul, is(equalTo(0d)));
    }

    /*
    Add a unit test called divTwoNumbers() that tests the div() method with two non-zero arguments.
    * */
    @Test
    public void divTwoNumbers() {
        double resultDiv = mCalculator.div(32d,2d);
        assertThat(resultDiv, is(equalTo(16d)));
    }


    /*
    Add a unit test called divTwoNumbersZero() that tests the div() method with a double dividend
    and zero as the divider.
    * */
    @Test
    public void divTwoNumbersZero() {
        double resultDiv = mCalculator.div(32d, 0);
        assertThat(resultDiv,is(equalTo(Double.POSITIVE_INFINITY)));
    }

}