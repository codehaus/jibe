package org.codehaus.jibe;

public class QuorumTerminationTest
    extends JibeTestCase
{
    public void testRequired_Percentage()
    {
        QuorumTermination term = new QuorumTermination( 11,
                                                        0.4 );

        assertEquals( 5,
                      term.getRequiredResponses() );
    }

    public void testShouldTerminate_Percentage()
    {
        QuorumTermination term = new QuorumTermination( 11,
                                                        0.4 );

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
