package org.codehaus.jibe;

import java.util.List;
import java.util.ArrayList;

public class MockTransport
    implements Transport
{
    private List registered;
    private List unregistered;
    private List distributed;
    private List outcomes;

    public MockTransport()
    {
        this.registered = new ArrayList();
        this.unregistered = new ArrayList();
        this.distributed = new ArrayList();
        this.outcomes = new ArrayList();
    }

    public void register(JibeSession session)
        throws TransportException
    {
        this.registered.add( session );
    }

    public JibeSession[] getRegistered()
    {
        return (JibeSession[]) this.registered.toArray( JibeSession.EMPTY_ARRAY );
    }

    public void unregister(JibeSession session)
        throws TransportException
    {
        this.unregistered.add( session );
    }

    public JibeSession[] getUnregistered()
    {
        return (JibeSession[]) this.unregistered.toArray( JibeSession.EMPTY_ARRAY );
    }

    public void distribute(Proposal proposal,
                           ResponseHandler responseHandler)
        throws TransportException
    {
        this.distributed.add( proposal );
    }

    public void distribute(Outcome outcome)
        throws TransportException
    {
        this.outcomes.add( outcome );
    }

    public Proposal[] getDistributed()
    {
        return (Proposal[]) this.distributed.toArray( Proposal.EMPTY_ARRAY );
    }

    public Outcome[] getOutcomes()
    {
        return (Outcome[]) this.outcomes.toArray( Outcome.EMPTY_ARRAY );
    }


    public void respond(Solicitation solicitation,
                        int status,
                        Object responsePayLoad)
        throws TransportException
    {

    }
}
