package org.codehaus.jibe.group;

public class GroupNodeMessage
    extends GroupMessage
{
    private String nodeId;

    public GroupNodeMessage(String groupId,
                            String nodeId)
    {
        super( groupId );

        this.nodeId = nodeId;
    }

    public String getNodeId()
    {
        return this.nodeId;
    }
}
