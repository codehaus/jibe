package org.codehaus.jibe.group;

import org.codehaus.jibe.JibeSession;
import org.codehaus.jibe.Proposal;
import org.codehaus.jibe.TransportException;
import org.codehaus.jibe.framework.Protocol;

import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;

public class Group
    extends Protocol
{
    private String groupId;
    private String nodeId;
    private boolean isJoined;

    private Set members;
    private Set suspects;

    private long joinTimeout;

    public Group(String groupid,
                 String nodeId,
                 JibeSession session,
                 long joinTimeout)
        throws TransportException
    {
        super( "group",
               session );

        this.joinTimeout = joinTimeout;
        this.groupId     = groupId;
        this.nodeId      = nodeId;
        this.members     = new TreeSet();
        this.suspects    = new HashSet();
        this.isJoined    = false;

        registerSolicitationHandler( JoinGroupProposal.class,
                                     new JoinGroupSolicitationHandler( this ) );

        registerSolicitationHandler( AddMemberProposal.class,
                                     new AddMemberSolicitationHandler( this ) );

        registerSolicitationHandler( RemoveMemberProposal.class,
                                     new RemoveMemberSolicitationHandler( this ) );

        getSession().propose( new Proposal( new JoinGroupProposal( this.groupId,
                                                                   this.nodeId ) ),
                              new OneAckTermination(),
                              null,
                              this.joinTimeout );
    }

    public String getGroupId()
    {
        return this.groupId;
    }

    public String getNodeId()
    {
        return this.nodeId;
    }

    public boolean isJoined()
    {
        return this.isJoined;
    }

    protected boolean isResponsibleFor(String nodeId)
    {
        return false;
    }

    protected boolean isSuspected(String nodeId)
    {
        return this.suspects.contains( nodeId );
    }

    protected void addMember(String nodeId)
    {
        this.members.add( nodeId );
    }

    protected void removeMember(String nodeId)
    {
        this.members.remove( nodeId );
        this.suspects.remove( nodeId );
    }

    protected void addSuspectedMember(String nodeId)
    {
        this.suspects.add( nodeId );
    }

    protected void removeSuspectedMember(String nodeId)
    {
        this.suspects.remove( nodeId );
    }
}
