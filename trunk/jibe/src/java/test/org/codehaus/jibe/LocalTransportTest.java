package org.codehaus.jibe;

public class LocalTransportTest
    extends JibeTestCase
{
    public void testConstruct()
    {
        LocalTransport transport = new LocalTransport();

        assertEmpty( transport.getRegisteredSessions() );
    }

    public void testRegistration()
        throws Exception
    {
        LocalTransport transport = new LocalTransport();

        JibeSession session = new JibeSession( transport,
                                               "session"  );

        transport.register( session );

        assertLength( 1,
                      transport.getRegisteredSessions() );

        assertContains( session,
                        transport.getRegisteredSessions() );

        JibeSession anotherSession = new JibeSession( transport,
                                                      "sessionToo"  );

        transport.register( anotherSession );

        assertLength( 2,
                      transport.getRegisteredSessions() );

        assertContains( session,
                        transport.getRegisteredSessions() );

        assertContains( anotherSession,
                        transport.getRegisteredSessions() );

        transport.unregister( session );

        assertLength( 1,
                      transport.getRegisteredSessions() );

        assertContains( anotherSession,
                        transport.getRegisteredSessions() );

        transport.unregister( anotherSession );

        assertEmpty( transport.getRegisteredSessions() );
    }

    public void testDistribute_Proposal()
        throws Exception
    {
        LocalTransport transport = new LocalTransport();

        JibeSession responder = new JibeSession( transport,
                                                 "session" );

        MockSolicitationHandler handler = new MockSolicitationHandler();

        responder.setSolicitationHandler( handler );

        transport.register( responder );

        Proposal proposal = new Proposal( "cheese" );

        transport.distribute( proposal,
                              null );

        Thread.sleep( 1000 );

        assertSame( proposal,
                    handler.getSolicitation().getProposal() );
                    
    }

    public void testRespond()
        throws Exception
    {
        LocalTransport transport = new LocalTransport();

        MockResponseHandler handler = new MockResponseHandler();

        Proposal proposal = new Proposal( "cheese" );

        Solicitation solicitation = new DefaultSolicitation( transport,
                                                             proposal );

        transport.registerResponseHandler( solicitation,
                                           handler );

        transport.respond( solicitation,
                           Response.NORMAL,
                           "goober" );

        Thread.sleep( 1000 );

        Response[] responses = handler.getResponses();

        assertLength( 1,
                      responses );

        Response resp = responses[0];

        assertNotNull( resp );

        assertEquals( Response.NORMAL,
                      resp.getStatus() );

        assertEquals( "goober",
                      resp.getPayload() );
                      
    }
}
