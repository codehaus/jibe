package org.codehaus.jibe;

public class MockSolicitationHandler
    implements SolicitationHandler
{
    private Solicitation solicitation;
    private Outcome outcome;

    public MockSolicitationHandler()
    {
    }
    
    public void handle(Solicitation solicitation)
    {
        this.solicitation = solicitation;
    }

    public void handle(Outcome outcome)
    {
        this.outcome = outcome;
    }

    public Solicitation getSolicitation()
    {
        return this.solicitation;
    }

    public Outcome getOutcome()
    {
        return this.outcome;
    }
}
