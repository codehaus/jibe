package org.codehaus.jibe;

public class JibeSessionTest
    extends JibeTestCase
{
    public void testConstruct()
    {
        Transport transport = new MockTransport();

        JibeSession session = new JibeSession( transport,
                                               "session" );

        assertSame( transport,
                    session.getTransport() );
    }

    public void testSolicitationHandler()
    {
        Transport transport = new MockTransport();

        JibeSession session = new JibeSession( transport,
                                               "session"  );

        SolicitationHandler handler = new MockSolicitationHandler();

        session.setSolicitationHandler( handler );

        assertSame( handler,
                    session.getSolicitationHandler() );
    }

    public void testPropose()
        throws Exception
    {
        MockTransport transport = new MockTransport();

        JibeSession session = new JibeSession( transport,
                                               "session"  );

        Proposal proposal = new Proposal( "cheese" );

        Termination termination = new MockTermination( true );

        Adjudicator adjudicator = new MockAdjudicator( null );

        session.propose( proposal,
                         termination,
                         adjudicator );

        assertLength( 1,
                      transport.getDistributed() );

        assertContains( proposal,
                        transport.getDistributed() );
    }

    public void testPropose_TimeOut()
        throws Exception
    {
        MockTransport transport = new MockTransport();

        JibeSession session = new JibeSession( transport,
                                               "session"  );

        Proposal proposal = new Proposal( "cheese" );

        Termination termination = new MockTermination( true );

        MockAdjudicator adjudicator = new MockAdjudicator( null );

        session.propose( proposal,
                         termination,
                         adjudicator,
                         1000 );

        Thread.sleep( 1500 );

        ResponseSet responses = adjudicator.getResponses();

        assertTrue( responses.isTimedOut() );
    }
}
