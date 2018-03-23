package com.cenobitor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import oracle.jdbc.driver.DatabaseError;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,calendar.get(Calendar.HOUR_OF_DAY)+2);
//        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY,8);
        System.out.println(calendar1.get(Calendar.HOUR_OF_DAY));

        /*calendar.set(Calendar.HOUR_OF_DAY,2);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));*/
    }

}
