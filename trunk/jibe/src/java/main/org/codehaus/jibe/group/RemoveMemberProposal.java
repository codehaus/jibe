package org.codehaus.jibe.group;

public class RemoveMemberProposal
    extends GroupNodeMessage
{
    public RemoveMemberProposal(String groupId,
                                String nodeId)
    {
        super( groupId,
               nodeId );
    }
}
