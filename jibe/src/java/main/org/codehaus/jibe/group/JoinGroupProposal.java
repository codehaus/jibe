package org.codehaus.jibe.group;

public class JoinGroupProposal
    extends GroupNodeMessage
{
    public JoinGroupProposal(String nodeId,
                             String groupId)
    {
        super( nodeId,
               groupId );
    }
}
