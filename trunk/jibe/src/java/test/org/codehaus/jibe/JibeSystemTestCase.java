package org.codehaus.jibe;

import org.sysunit.SystemTestCase;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Collection;

public class JibeSystemTestCase
    extends SystemTestCase
{

    public void assertEmpty(Object[] array)
    {
        assertLength( 0,
                      array );
    }

    public void assertEmpty(Collection collection)
    {
        assertTrue( collection.isEmpty() );
    }

    public void assertLength(int len,
                             Object[] array)
    {
        assertEquals( len,
                      array.length );
    }

    public void assertContains(Object object,
                               Object[] array)
    {
        for ( int i = 0 ; i < array.length ; ++i )
        {
            if ( array[i].equals( object ) )
            {
                return;
            }
        }

        fail( object + " not in " + Arrays.asList( array ) );
    }

    public void assertContains(Object object,
                               Iterator iterator)
    {
        while ( iterator.hasNext() )
        {
            if ( iterator.next().equals( object ) )
            {
                return;
            }
        }

        fail( object + " not in iterator" );
    }
}


