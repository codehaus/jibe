package org.codehaus.jibe.group;

public class AddMemberProposal
    extends GroupNodeMessage
{
    public AddMemberProposal(String groupId,
                             String nodeId)
    {
        super( groupId,
               nodeId );
    }
}
