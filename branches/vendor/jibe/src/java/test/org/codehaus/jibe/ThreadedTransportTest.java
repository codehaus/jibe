package org.codehaus.jibe;

import java.util.Properties;

public class ThreadedTransportTest
    extends JibeTestCase
{
    JibeSessionFactory factory;

    public void setUp()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        this.factory = JibeSessionFactory.newSessionFactory( props );
    }

    public void tearDown()
        throws Exception
    {
        this.factory = null;
    }

    protected JibeSession newSession()
        throws Exception
    {
        return this.factory.newSession();
    }

    public void testHandleSolicitation()
        throws Exception
    {
        JibeSession session = newSession();

        final StringBuffer touched = new StringBuffer();

        session.setSolicitationHandler( new SolicitationHandler()
            {
                public void handle(Solicitation solicitation)
                    throws Exception
                {
                    touched.append( "touched:" + solicitation.getProposal().getPayload() );
                }
            } );

        Proposal proposal = new Proposal( "cheese" );
        
        Solicitation solicitation = new DefaultSolicitation( session.getTransport(),
                                                             proposal );

        ((LocalTransport)session.getTransport()).handle( session.getSolicitationHandler(),
                                                         solicitation );

        Thread.sleep( 1000 );

        assertEquals( "touched:cheese",
                      touched.toString() );
    }

    public void testHandleResponse()
        throws Exception
    {
        JibeSession session = newSession();

        final StringBuffer touched = new StringBuffer();

        ResponseHandler handler = new ResponseHandler()
            {
                public void handle(Response response)
                {
                    touched.append( "touched:" + response.getPayload() );
                }
            };
        DefaultResponse response = new DefaultResponse( Response.NORMAL,
                                                        "cheese" );

        ((LocalTransport)session.getTransport()).handle( handler,
                                                         response );

        Thread.sleep( 1000 );

        assertEquals( "touched:cheese",
                      touched.toString() );
    }
}
