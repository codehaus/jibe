package org.codehaus.jibe;

public class DefaultSolicitationTest
    extends JibeTestCase
{
    public void testConstruct()
    {
        Transport transport = new MockTransport();

        Proposal proposal = new Proposal( "cheese" );

        DefaultSolicitation solicitation = new DefaultSolicitation( transport,
                                                                    proposal );

        assertSame( transport,
                    solicitation.getTransport() );

        assertSame( proposal,
                    solicitation.getProposal() );
    }
}
