package org.codehaus.jibe.group;

public class JoinMessage
    extends BaseMessage
{
    private String nodeId;
    private String groupId;

    public JoinMessage(String nodeId,
                       String groupId)
    {
        this.nodeId  = nodeId;
        this.groupId = groupId;
    }

    public String getNodeId()
    {
        return this.nodeId;
    }

    public String getGroupId()
    {
        return this.groupId;
    }
}
