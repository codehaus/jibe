package org.codehaus.jibe;

public class MockAdjudicator
    implements Adjudicator
{
    private Outcome outcome;
    private ResponseSet responses;

    public MockAdjudicator(Outcome outcome)
    {
        this.outcome = outcome;
    }

    public Outcome adjudicate(ResponseSet responses)
    {
        this.responses = responses;
        return this.outcome;
    }

    public ResponseSet getResponses()
    {
        return this.responses;
    }
}
