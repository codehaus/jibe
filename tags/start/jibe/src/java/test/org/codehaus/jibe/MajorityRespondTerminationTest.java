package org.codehaus.jibe;

public class MajorityRespondTerminationTest
    extends JibeTestCase
{
    public void testRequired()
    {
        MajorityRespondTermination term = new MajorityRespondTermination( 9 );

        assertEquals( 5,
                      term.getRequiredResponses() );
    }

    public void testShouldTerminate()
    {
        MajorityRespondTermination term = new MajorityRespondTermination( 9 );

        MutableResponseSet responses = new DefaultResponseSet();

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "cheese" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "cheese" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "cheese" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "cheese" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "cheese" ) );

        assertTrue( term.shouldTerminate( responses ) );
    }
}
