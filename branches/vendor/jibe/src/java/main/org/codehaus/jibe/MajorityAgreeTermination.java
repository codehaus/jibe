package org.codehaus.jibe;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class MajorityAgreeTermination
    implements Termination
{
    private int populationSize;

    public MajorityAgreeTermination(int populationSize)
    {
        this.populationSize = populationSize;
    }

    public int getPopulationSize()
    {
        return this.populationSize;
    }

    public boolean shouldTerminate(ResponseSet responses)
    {
        if ( responses.size() == getPopulationSize() )
        {
            return true;
        }

        Map answers = new HashMap();

        for ( Iterator responseIter = responses.iterator();
              responseIter.hasNext();  )
        {
            Response response = (Response) responseIter.next();

            Object payload = response.getPayload();

            Integer num = (Integer) answers.get( payload );

            if ( num == null )
            {
                num = new Integer( 1 );
            }
            else
            {
                num = new Integer( num.intValue() + 1 );
            }

            answers.put( payload,
                         num );

            int majority = (getPopulationSize() / 2) + 1;

            if ( num.intValue() >= majority )
            {
                return true;
            }
        }

        return false;
    }
}
