package org.codehaus.jibe;

import java.util.Properties;

public class InProgressProposalTest
    extends JibeTestCase
{
    public void testConstruct()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        JibeSessionFactory factory = JibeSessionFactory.newSessionFactory( props );

        JibeSession session = factory.newSession();

        Proposal proposal = new Proposal( "cheese" );

        Termination termination = new MockTermination( true );

        Adjudicator adjudicator = new MockAdjudicator( new MockOutcome( "goober" ) );

        InProgressProposal inProg = new InProgressProposal( session,
                                                            proposal,
                                                            termination,
                                                            adjudicator );

        assertSame( session,
                    inProg.getSession() );

        assertSame( proposal,
                    inProg.getProposal() );

        assertSame( termination,
                    inProg.getTermination() );

        assertSame( adjudicator,
                    inProg.getAdjudicator() );

        assertFalse( inProg.isTerminated() );
    }

    public void testHandle()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        JibeSessionFactory factory = JibeSessionFactory.newSessionFactory( props );

        JibeSession session = factory.newSession();

        Proposal proposal = new Proposal( "cheese" );

        Termination termination = new MockTermination( true );

        Adjudicator adjudicator = new MockAdjudicator( new MockOutcome( "goober" ) );

        InProgressProposal inProg = new InProgressProposal( session,
                                                            proposal,
                                                            termination,
                                                            adjudicator );

        Response response = new DefaultResponse( Response.NORMAL,
                                                 "tacos" );

        assertFalse( inProg.isTerminated() );

        inProg.handle( response );

        assertTrue( inProg.isTerminated() );

        assertEquals( 1,
                      inProg.getResponses().size() );

        assertContains( response,
                        inProg.getResponses().iterator() );

        Response anotherResponse = new DefaultResponse( Response.NORMAL,
                                                        "cat" );

        inProg.handle( anotherResponse );

        assertTrue( inProg.isTerminated() );

        assertEquals( 1,
                      inProg.getResponses().size() );

        assertContains( response,
                        inProg.getResponses().iterator() );
    }
}
