package org.codehaus.jibe.group;

import org.codehaus.jibe.Outcome;
import org.codehaus.jibe.Solicitation;

public class AddMemberSolicitationHandler
    extends GroupSolicitationHandler
{
    public AddMemberSolicitationHandler(Group group)
    {
        super( group );
    }

    public void handle(Solicitation solicitation)
        throws Exception
    {
        ack( solicitation );
    }

    public void handle(Outcome outcome)
    {
        GroupNodeMessage payload = (GroupNodeMessage) outcome.getPayload();

        if ( payload instanceof AddMemberOutcome )
        {
            getGroup().addMember( payload.getNodeId() );
        }
    }
}
