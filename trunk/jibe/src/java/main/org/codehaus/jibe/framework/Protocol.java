package org.codehaus.jibe.framework;

import org.codehaus.jibe.JibeSession;
import org.codehaus.jibe.Outcome;
import org.codehaus.jibe.Solicitation;
import org.codehaus.jibe.SolicitationHandler;
import org.codehaus.jibe.JibeException;

import java.util.Map;
import java.util.HashMap;

public class Protocol
    implements SolicitationHandler
{
    private String protocolName;
    private JibeSession session;

    private Map solicitationHandlers;

    public Protocol(String protocolName,
                    JibeSession session)
    {
        this.protocolName = protocolName;
        this.session      = session;

        this.session.setSolicitationHandler( this );

        this.solicitationHandlers = new HashMap();
    }

    protected JibeSession getSession()
    {
        return this.session;
    }

    protected void registerSolicitationHandler(Class payloadClass,
                                               SolicitationHandler handler)
    {
        this.solicitationHandlers.put( payloadClass,
                                       handler );
    }

    protected SolicitationHandler lookupSolicitationHandler(Class payloadClass)
    {
        return (SolicitationHandler) this.solicitationHandlers.get( payloadClass );
    }

    public void handle(Solicitation solicitation)
        throws Exception
    {
        Object payload = solicitation.getProposal().getPayload();

        if ( payload != null )
        {
            SolicitationHandler handler = lookupSolicitationHandler( payload.getClass() );

            if ( handler != null )
            {
                handler.handle( solicitation );
            }
        }
    }
    
    public void handle(Outcome outcome)
    {
    }
}
