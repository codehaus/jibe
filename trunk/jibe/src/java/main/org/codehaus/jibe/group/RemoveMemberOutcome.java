package org.codehaus.jibe.group;

public class RemoveMemberOutcome
    extends GroupNodeMessage
{
    public RemoveMemberOutcome(String groupId,
                               String nodeId)
    {
        super( groupId,
               nodeId );
    }
}
