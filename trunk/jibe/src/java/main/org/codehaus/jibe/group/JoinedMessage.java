package org.codehaus.jibe.group;

public class JoinedMessage
    extends BaseMessage
{
    private String nodeId;
    private String groupId;

    public JoinedMessage(String nodeId,
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

    public String toString()
    {
        return "[JoinedMessage: nodeId=" + getNodeId()
            + "; groupId=" + getGroupId()
            + "]";
    }
}
