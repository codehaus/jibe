package org.codehaus.jibe;

public class OneResponseTerminationTest
    extends JibeTestCase
{
    public void testEarlyTermination()
    {
        MutableResponseSet responses = new DefaultResponseSet();

        assertFalse( OneResponseTermination.getInstance().shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "cheese" ) );

        assertTrue( OneResponseTermination.getInstance().shouldTerminate( responses ) );
    }

    public void testLateTermination()
    {
        MutableResponseSet responses = new DefaultResponseSet();

        assertFalse( OneResponseTermination.getInstance().shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.ACTIVE_ABSTAIN,
                                            "cheese" ) );

        assertFalse( OneResponseTermination.getInstance().shouldTerminate( responses ) );

        responses.add( new DefaultResponse( Response.NORMAL,
                                            "cheese" ) );
        assertTrue( OneResponseTermination.getInstance().shouldTerminate( responses ) );
    }
}
