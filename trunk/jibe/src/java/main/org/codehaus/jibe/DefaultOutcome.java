package org.codehaus.jibe;

public class DefaultOutcome
    implements Outcome
{
    private Object payload;

    public DefaultOutcome(Object payload)
    {
        this.payload = payload;
    }

    public Object getPayload()
    {
        return this.payload;
    }
}
