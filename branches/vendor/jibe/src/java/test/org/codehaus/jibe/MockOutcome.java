package org.codehaus.jibe;

public class MockOutcome
    implements Outcome
{
    private Object payload;

    public MockOutcome(Object payload)
    {
        this.payload = payload;
    }

    public Object getPayload()
    {
        return this.payload;
    }
}
