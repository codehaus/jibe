package org.codehaus.jibe;

import java.util.List;
import java.util.ArrayList;

public class MockResponseHandler
    implements ResponseHandler
{
    private List responses;

    public MockResponseHandler()
    {
        this.responses = new ArrayList();
    }

    public void handle(Response response)
    {
        this.responses.add( response );
    }

    public Response[] getResponses()
    {
        return (Response[]) this.responses.toArray( Response.EMPTY_ARRAY );
    }
}
