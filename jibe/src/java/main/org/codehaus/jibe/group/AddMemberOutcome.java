package org.codehaus.jibe.group;

public class AddMemberOutcome
    extends GroupNodeMessage
{
    public AddMemberOutcome(String groupId,
                            String nodeId)
    {
        super( groupId,
               nodeId );
    }
}
