package org.codehaus.jibe;

public class DefaultResponseSetTest
    extends JibeTestCase
{
    public void testConstruct()
    {
        ResponseSet responseSet = new DefaultResponseSet();

        assertTrue( responseSet.isEmpty() );

        assertFalse( responseSet.iterator().hasNext() );

        assertEquals( 0,
                      responseSet.size() );
    }

    public void testAdd()
    {
        MutableResponseSet responseSet = new DefaultResponseSet();

        Response response1 = new DefaultResponse( Response.NORMAL,
                                                  "cheese" );

        responseSet.add( response1 );

        assertFalse( responseSet.isEmpty() );

        assertEquals( 1,
                      responseSet.size() );

        assertContains( response1,
                        responseSet.iterator() );

        responseSet.add( response1 );
        responseSet.add( response1 );
        responseSet.add( response1 );
        responseSet.add( response1 );
        responseSet.add( response1 );
        responseSet.add( response1 );

        assertEquals( 1,
                      responseSet.size() );

        assertContains( response1,
                        responseSet.iterator() );
        
        Response response2 = new DefaultResponse( Response.NORMAL,
                                                  "cheese" );

        responseSet.add( response2 );

        assertEquals( 2,
                      responseSet.size() );

        assertContains( response1,
                        responseSet.iterator() );

        assertContains( response2,
                        responseSet.iterator() );
    }
}

