package org.codehaus.jibe.group;

public class NodeSelectionMessage
    extends GroupNodeMessage
{
    private String selectedNodeId;

    public NodeSelectionMessage(String groupId,
                                String nodeId,
                                String selectedNodeId)
    {
        super( groupId,
               nodeId );

        this.selectedNodeId = selectedNodeId;
    }

    public String getSelectedNodeId()
    {
        return this.selectedNodeId;
    }
}
