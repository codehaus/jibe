package org.codehaus.jibe;

public class MockTermination
    implements Termination
{
    private boolean shouldTerminate;
    private ResponseSet responses;

    public MockTermination(boolean shouldTerminate)
    {
        this.shouldTerminate = shouldTerminate;
    }

    public boolean shouldTerminate(ResponseSet responses)
    {
        this.responses = responses;

        return this.shouldTerminate;
    }

    public ResponseSet getResponses()
    {
        return this.responses;
    }
}
