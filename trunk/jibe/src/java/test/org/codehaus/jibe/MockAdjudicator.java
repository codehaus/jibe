package org.codehaus.jibe;

import java.util.List;
import java.util.ArrayList;

public class MockAdjudicator
    implements Adjudicator
{
    private Outcome outcome;
    private List proposals;
    private ResponseSet responses;

    public MockAdjudicator(Outcome outcome)
    {
        this.outcome = outcome;
        this.proposals = new ArrayList();
    }

    public Outcome adjudicate(Proposal proposal,
                              ResponseSet responses)
    {
        this.proposals.add( proposal );
        this.responses = responses;
        return this.outcome;
    }

    public ResponseSet getResponses()
    {
        return this.responses;
    }

    public Proposal[] getProposals()
    {
        return (Proposal[]) this.proposals.toArray( Proposal.EMPTY_ARRAY );
    }
}
