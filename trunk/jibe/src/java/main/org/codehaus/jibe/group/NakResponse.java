package org.codehaus.jibe.group;

public class NakResponse
    extends GroupNodeMessage
{
    public NakResponse(String groupId,
                       String nodeId)
    {
        super( groupId,
               nodeId );
    }
}
