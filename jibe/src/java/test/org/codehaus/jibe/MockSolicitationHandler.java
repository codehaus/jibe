package org.codehaus.jibe;

public class MockSolicitationHandler
    implements SolicitationHandler
{
    private Solicitation solicitation;

    public MockSolicitationHandler()
    {
    }
    
    public void handle(Solicitation solicitation)
    {
        this.solicitation = solicitation;
    }

    public Solicitation getSolicitation()
    {
        return this.solicitation;
    }
}
