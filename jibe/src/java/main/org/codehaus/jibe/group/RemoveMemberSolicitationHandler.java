package org.codehaus.jibe.group;

import org.codehaus.jibe.Outcome;
import org.codehaus.jibe.Solicitation;

public class RemoveMemberSolicitationHandler
    extends GroupSolicitationHandler
{
    public RemoveMemberSolicitationHandler(Group group)
    {
        super( group );
    }

    public void handle(Solicitation solicitation)
        throws Exception
    {
        RemoveMemberProposal message = (RemoveMemberProposal) solicitation;

        if ( getGroup().isResponsibleFor( message.getNodeId() ) )
        {
            if ( getGroup().isSuspected( message.getNodeId() ) )
            {
                ack( solicitation );
            }
            else
            {
                nak( solicitation );
            }
        }
    }

    public void handle(Outcome outcome)
    {
        GroupNodeMessage payload = (GroupNodeMessage) outcome.getPayload();

        if ( payload instanceof RemoveMemberOutcome )
        {
            getGroup().removeMember( payload.getNodeId() );
        }
    }
}
