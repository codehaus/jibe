package org.codehaus.jibe.group;

import org.codehaus.jibe.Termination;
import org.codehaus.jibe.Response;
import org.codehaus.jibe.ResponseSet;

import java.util.Iterator;

public class OneAckTermination
    implements Termination
{
    public boolean shouldTerminate(ResponseSet responses)
    {
        for ( Iterator responseIter = responses.iterator();
              responseIter.hasNext(); )
        {
            Response response = (Response) responseIter.next();

            if ( response.getStatus() == Response.NORMAL )
            {
                if ( response.getPayload() instanceof AckResponse )
                {
                    return true;
                }
            }
        }

        return false;
    }
}
