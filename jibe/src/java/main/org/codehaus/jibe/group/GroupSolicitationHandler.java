package org.codehaus.jibe.group;

import org.codehaus.jibe.Solicitation;
import org.codehaus.jibe.SolicitationHandler;
import org.codehaus.jibe.ResponseException;

public abstract class GroupSolicitationHandler
    implements SolicitationHandler
{
    private Group group;

    public GroupSolicitationHandler(Group group)
    {
        this.group = group;
    }

    public Group getGroup()
    {
        return this.group;
    }

    protected void ack(Solicitation solicitation)
        throws ResponseException
    {
        solicitation.respond( new AckResponse( getGroup().getGroupId(),
                                               getGroup().getNodeId() ) );
    }

    protected void nak(Solicitation solicitation)
        throws ResponseException
    {
        solicitation.respond( new NakResponse( getGroup().getGroupId(),
                                               getGroup().getNodeId() ) );
    }

    protected void abstain(Solicitation solicitation)
        throws ResponseException
    {
        solicitation.abstain( new GroupNodeMessage( getGroup().getGroupId(),
                                                    getGroup().getNodeId() ) );
    }
}
