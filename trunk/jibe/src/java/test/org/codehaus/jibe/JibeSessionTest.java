package org.codehaus.jibe;

public class JibeSessionTest
    extends JibeTestCase
{
    public void testConstruct()
    {
        Transport transport = new MockTransport();

        JibeSession session = new JibeSession( transport );

        assertSame( transport,
                    session.getTransport() );
    }

    public void testSolicitationHandler()
    {
        Transport transport = new MockTransport();

        JibeSession session = new JibeSession( transport );

        SolicitationHandler handler = new MockSolicitationHandler();

        session.setSolicitationHandler( handler );

        assertSame( handler,
                    session.getSolicitationHandler() );
    }

    public void testPropose()
        throws Exception
    {
        MockTransport transport = new MockTransport();

        JibeSession session = new JibeSession( transport );

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
}
