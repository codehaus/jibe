package org.codehaus.jibe;

import java.util.Iterator;

public class OneResponseTermination
    implements Termination
{
    private static final OneResponseTermination INSTANCE = new OneResponseTermination();

    public static OneResponseTermination getInstance()
    {
        return INSTANCE;
    }

    public OneResponseTermination()
    {
    }

    public boolean shouldTerminate(ResponseSet responses)
    {
        for ( Iterator respIter = responses.iterator();
              respIter.hasNext(); )
        {
            if ( ((Response)respIter.next()).getStatus() == Response.NORMAL )
            {
                return true;
            }
        }

        return false;
    }
}
