package org.codehaus.jibe.group;

import org.codehaus.jibe.Outcome;
import org.codehaus.jibe.Solicitation;

public class JoinGroupSolicitationHandler
    extends GroupSolicitationHandler
{
    public JoinGroupSolicitationHandler(Group group)
    {
        super( group );
    }

    public void handle(Solicitation solicitation)
        throws Exception
    {
        if ( getGroup().isJoined() )
        {
            // volunteer to represent
            ack( solicitation );
        }
        else
        {
            // don't volunteer, but actively abstain
            // so solicitor knows we're looking also.
            abstain( solicitation );
        }
    }

    public void handle(Outcome outcome)
    {
        Object payload = outcome.getPayload();

        if ( payload instanceof NodeSelectionMessage )
        {
            handle( (NodeSelectionMessage) payload );
        }
    }

    protected void handle(NodeSelectionMessage message)
    {
        if ( message.getSelectedNodeId().equals( getGroup().getNodeId() ) )
        {
            // we were selected to make the proposal
        }
    }
}
