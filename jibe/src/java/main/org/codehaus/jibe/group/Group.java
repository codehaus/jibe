package org.codehaus.jibe.group;

import org.codehaus.jibe.Adjudicator;
import org.codehaus.jibe.JibeSession;
import org.codehaus.jibe.JibeSessionFactory;
import org.codehaus.jibe.Outcome;
import org.codehaus.jibe.DefaultOutcome;
import org.codehaus.jibe.Proposal;
import org.codehaus.jibe.Response;
import org.codehaus.jibe.ResponseSet;
import org.codehaus.jibe.Solicitation;
import org.codehaus.jibe.SolicitationHandler;
import org.codehaus.jibe.OneResponseTermination;
import org.codehaus.jibe.JibeException;
import org.codehaus.jibe.TransportException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Iterator;

public class Group
    implements SolicitationHandler, Adjudicator
{
    private String nodeId;
    private String groupId;

    private JibeSession session;

    private boolean isJoining;

    private List members;
    private List suspects;

    private boolean seniorSeen;

    public Group(String nodeId,
                 String groupId,
                 JibeSession session)
        throws JibeException
    {
        this.nodeId  = nodeId;
        this.groupId = groupId;
        this.session = session;

        this.members  = new ArrayList();

        this.members.add( new Member( nodeId ) );

        this.suspects = new ArrayList();

        this.isJoining = true;

        this.session.setSolicitationHandler( this );

        attemptJoin();
    }

    protected void attemptJoin()
        throws TransportException
    {
        System.err.println( getNodeId() + " propose to join" );
        this.session.propose( new Proposal( new JoinMessage( this.nodeId,
                                                             this.groupId ) ),
                              OneResponseTermination.getInstance(),
                              this,
                              2000 );
        System.err.println( getNodeId() + " proposal sent" );
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

    public synchronized void handle(Solicitation solicitation)
        throws Exception
    {
        Object payload = solicitation.getProposal().getPayload();

        System.err.println( getNodeId() + " handle solicitation " + payload );
        
        if ( payload instanceof JoinMessage )
        {
            JoinMessage message = (JoinMessage) payload;

            if ( getNodeId().equals( message.getNodeId() ) )
            {
                System.err.println( getNodeId() + " abstain response from self" );
                // abstain, don't answer out own.
                solicitation.abstain();
                return;
            }
            
            if ( isJoining() )
            {
                System.err.println( getNodeId() + " abstain response while joining" );
                // abtain, we haven't completely joined yet.

                if ( getNodeId().compareTo( message.getNodeId() ) < 0 )
                {
                    System.err.println( getNodeId() + " senior seen" );
                    this.seniorSeen = true;
                    solicitation.abstain();
                }
                else
                {
                    System.err.println( getNodeId() + " sending senority notice" );
                    solicitation.abstain( new SeniorityMessage( getNodeId() ) );
                }

                return;
            }

            System.err.println( getNodeId() + " joined, sending response" );

            //this.members.add( new Member( message.getNodeId() ) );

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

        System.err.println( "###### " + getNodeId() + "     OUTCOME     " + payload );

        //if ( payload instanceof GroupListMessage )
        //{
        //    return;
        //}

        if ( payload instanceof JoinedMessage )
        {
            JoinedMessage message = (JoinedMessage) payload;

            System.err.println( getNodeId() + " adding " + message.getNodeId() );
            this.members.add( new Member( message.getNodeId() ) );
        }
    }

    public synchronized Outcome adjudicate(Proposal proposal,
                                           ResponseSet responses)
    {
        System.err.println( getNodeId() + " adjudicating" );
        Object payload = proposal.getPayload();

        if ( payload instanceof JoinMessage )
        {
            JoinMessage message = (JoinMessage) payload;

            if ( responses.isTimedOut() )
            {
                System.err.println( getNodeId() + " timedout" );

                if ( ! this.seniorSeen )
                {
                    for ( Iterator responseIter = responses.iterator();
                          responseIter.hasNext(); )
                    {
                        Response response = (Response) responseIter.next();
                        
                        if ( response.getStatus() == Response.ACTIVE_ABSTAIN
                             &&
                             response.getPayload() instanceof SeniorityMessage )
                        {
                            this.seniorSeen = true;
                        }
                    }
                }
            }
            else
            {
                System.err.println( getNodeId() + " non-timeout responses: " + responses );

                for ( Iterator responseIter = responses.iterator();
                      responseIter.hasNext(); )
                {
                    Response response = (Response) responseIter.next();

                    if ( response.getStatus() == Response.NORMAL )
                    {
                        GroupListMessage groupListMessage = (GroupListMessage) response.getPayload();

                        Member[] members = groupListMessage.getMembers();

                        for ( int i = 0 ; i < members.length ; ++i )
                        {
                            if ( ! members[i].getId().equals( getNodeId() ) )
                            {
                                this.members.add( members[i] );
                            }
                        }
                        
                        return new DefaultOutcome( new JoinedMessage( getNodeId(),
                                                                      getGroupId() ) );
                    }
                }

                return new DefaultOutcome( "deadbeef #1" );
            }

            if ( this.seniorSeen )
            {
                this.seniorSeen = false;
                
                try
                {
                    System.err.println( getNodeId() + " attempt to join again" );
                    attemptJoin();
                }
                catch (TransportException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.err.println( getNodeId() + " no senior, considering self to be group" );
                this.isJoining  = false;
            }
        }

        return new DefaultOutcome( "deadbeef #2" );
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

        JibeSession session = factory.newSession( groupId + "." + nodeId );

        return new Group( nodeId,
                          groupId,
                          session ); 
    }
}
