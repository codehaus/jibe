package org.codehaus.jibe;

public class MajorityAgreeTerminationTest
    extends JibeTestCase
{
    public void testConstruct()
    {
        MajorityAgreeTermination term = new MajorityAgreeTermination( 9 );

        assertEquals( 9,
                      term.getPopulationSize() );
    }

    public void testShouldTerminate_NoAgreement()
    {
        MajorityAgreeTermination term = new MajorityAgreeTermination( 5 );

        MutableResponseSet responses = new DefaultResponseSet();

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "a" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "b" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "c" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "c" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "c" ) );

        assertTrue( term.shouldTerminate( responses ) );
    }

    public void testShouldTerminate_EarlyAgreement()
    {
        MajorityAgreeTermination term = new MajorityAgreeTermination( 5 );

        MutableResponseSet responses = new DefaultResponseSet();

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "a" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "a" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "a" ) );

        assertTrue( term.shouldTerminate( responses ) );
    }

    public void testShouldTerminate_LateAgreement()
    {
        MajorityAgreeTermination term = new MajorityAgreeTermination( 5 );

        MutableResponseSet responses = new DefaultResponseSet();

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "a" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "a" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "b" ) );

        assertFalse( term.shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "a" ) );

        assertTrue( term.shouldTerminate( responses ) );
    }
}
