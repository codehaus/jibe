package org.codehaus.jibe.group;

public class SeniorityMessage
    extends BaseMessage
{
    private String nodeId;

    public SeniorityMessage(String nodeId)
    {
        this.nodeId = nodeId;
    }

    public String getNodeId()
    {
        return this.nodeId;
    }

    public String toString()
    {
        return "[SeniorityMessage: nodeId=" + getNodeId() + "]";
    }
}
