package org.codehaus.jibe.group;

public class AckResponse
    extends GroupNodeMessage
{
    public AckResponse(String groupId,
                       String nodeId)
    {
        super( groupId,
               nodeId );
    }
}
