package org.codehaus.jibe.group;

public class NoOpOutcome
    extends GroupNodeMessage
{
    public NoOpOutcome(String groupId,
                       String nodeId)
    {
        super( groupId,
               nodeId );
    }
}
