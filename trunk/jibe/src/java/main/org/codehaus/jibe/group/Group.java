package org.codehaus.jibe.group;

import org.codehaus.jibe.Adjudicator;
import org.codehaus.jibe.JibeSession;
import org.codehaus.jibe.JibeSessionFactory;
import org.codehaus.jibe.Outcome;
import org.codehaus.jibe.Proposal;
import org.codehaus.jibe.ResponseSet;
import org.codehaus.jibe.Solicitation;
import org.codehaus.jibe.SolicitationHandler;
import org.codehaus.jibe.OneResponseTermination;
import org.codehaus.jibe.JibeException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

public class Group
    implements SolicitationHandler, Adjudicator
{
    private String nodeId;
    private String groupId;

    private JibeSession session;

    private boolean isJoining;

    private List members;
    private List suspects;

    public Group(String nodeId,
                 String groupId,
                 JibeSession session)
        throws JibeException
    {
        this.nodeId  = nodeId;
        this.groupId = groupId;
        this.session = session;

        this.members  = new ArrayList();
        this.suspects = new ArrayList();

        this.isJoining = true;

        this.session.setSolicitationHandler( this );

        this.session.propose( new Proposal( new JoinMessage( this.nodeId,
                                                             this.groupId ) ),
                              OneResponseTermination.getInstance(),
                              this );
    }

    protected boolean isJoining()
    {
        return this.isJoining;
    }

    protected JibeSession getSession()
    {
        return this.session;
    }

    protected String getNodeId()
    {
        return this.nodeId;
    }

    protected String getGroupId()
    {
        return this.groupId;
    }

    public void handle(Solicitation solicitation)
        throws Exception
    {
        Object payload = solicitation.getProposal().getPayload();
        
        if ( payload instanceof JoinMessage )
        {
            JoinMessage message = (JoinMessage) payload;

            if ( getNodeId().equals( message.getNodeId() ) )
            {
                // abstain, don't answer out own.
                solicitation.abstain();
                return;
            }
            
            if ( isJoining() )
            {
                // abtain, we haven't completely joined yet.
                solicitation.abstain();
                return;
            }

            solicitation.respond( new GroupListMessage( getMembers() ) );
        }
    }

    public Member[] getMembers()
    {
        return (Member[]) this.members.toArray( Member.EMPTY_ARRAY );
    }
    
    public void handle(Outcome outcome)
    {
        Object payload = outcome.getPayload();

        if ( payload instanceof GroupListMessage )
        {
            return;
        }
    }

    public Outcome adjudicate(ResponseSet responses)
    {
        return null;
    }

    public static Group join(String nodeId,
                             String groupId,
                             Properties props)
        throws JibeException
    {
        Properties finalProps = new Properties( props );

        finalProps.setProperty( JibeSessionFactory.FACTORY_NAME_PROPERTY,
                                groupId );

        JibeSessionFactory factory = JibeSessionFactory.newSessionFactory( finalProps );

        JibeSession session = factory.newSession();

        return new Group( nodeId,
                          groupId,
                          session ); 
    }
}
