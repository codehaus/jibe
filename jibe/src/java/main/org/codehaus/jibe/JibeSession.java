package org.codehaus.jibe;

/*
 $Id: JibeSession.java,v 1.3 2003-07-04 22:42:55 bob Exp $

 Copyright 2003 (C) The Codehaus. All Rights Reserved.
 
 Redistribution and use of this software and associated documentation
 ("Software"), with or without modification, are permitted provided
 that the following conditions are met:

 1. Redistributions of source code must retain copyright
    statements and notices.  Redistributions must also contain a
    copy of this document.
 
 2. Redistributions in binary form must reproduce the
    above copyright notice, this list of conditions and the
    following disclaimer in the documentation and/or other
    materials provided with the distribution.
 
 3. The name "jibe" must not be used to endorse or promote
    products derived from this Software without prior written
    permission of The Codehaus.  For written permission,
    please contact info@codehaus.org.
 
 4. Products derived from this Software may not be called "jibe"
    nor may "jibe" appear in their names without prior written
    permission of The Codehaus. "jibe" is a registered
    trademark of The Codehaus.
 
 5. Due credit should be given to The Codehaus.
    (http://jibe.codehaus.org/).
 
 THIS SOFTWARE IS PROVIDED BY THE CODEHAUS AND CONTRIBUTORS
 ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 THE CODEHAUS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 OF THE POSSIBILITY OF SUCH DAMAGE.
 
 */

import java.util.Timer;

/** Consensus session.
 *
 *  @see Proposal
 *  @see SolicitationHandler
 *  @see Termination
 *  @see Adjudicator
 *  @see Transport
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: JibeSession.java,v 1.3 2003-07-04 22:42:55 bob Exp $
 */
public class JibeSession
{
    // ----------------------------------------------------------------------
    //     Constants
    // ----------------------------------------------------------------------

    /** Empty <code>JibeSession</code> array. */
    public static final JibeSession[] EMPTY_ARRAY = new JibeSession[0];

    // ----------------------------------------------------------------------
    //     Instance members
    // ----------------------------------------------------------------------

    /** Transport. */
    private Transport transport;

    /** Inbound solicitation-handler. */
    private SolicitationHandler solicitationHandler;

    /** Timeout timer. */
    private Timer timer;

    private String sessionId;

    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------

    /** Construct.
     *
     *  @see JibeSessionFactory
     *
     *  @param transport The messaging transport.
     */
    JibeSession(Transport transport,
                String sessionId)
    {
        this.transport = transport;
        this.timer = new Timer( true );
        this.sessionId = sessionId;
    }

    // ----------------------------------------------------------------------
    //     Instance methods
    // ----------------------------------------------------------------------

    /** Retrieve the <code>Transport</code>.
     *
     *  @return The transport.
     */
    Transport getTransport()
    {
        return this.transport;
    }

    Timer getTimer()
    {
        return this.timer;
    }

    /** Propose a <code>Proposal</code>.
     *
     *  @param proposal The proposal.
     *  @param termination The termination determinor.
     *  @param adjudicator The outcome adjudicator.
     *
     *  @throws TransportException If an error occurs with the underlying
     *          message transport.
     */
    public void propose(Proposal proposal,
                        Termination termination,
                        Adjudicator adjudicator)
        throws TransportException
    {
        InProgressProposal inProgress = new InProgressProposal( this,
                                                                proposal,
                                                                termination,
                                                                adjudicator );
        getTransport().distribute( proposal,
                                   inProgress );
    }

    public void propose(Proposal proposal,
                        Termination termination,
                        Adjudicator adjudicator,
                        long timeout)
        throws TransportException
    {
        InProgressProposal inProgress = new InProgressProposal( this,
                                                                proposal,
                                                                termination,
                                                                adjudicator );

        getTimer().schedule( new Timeout( inProgress ),
                             timeout );

        getTransport().distribute( proposal,
                                   inProgress );
    }

    /** Set the <code>SolicitationHandler</code> to handle
     *  inbound <code>Solicitation</code>s.
     * 
     *  @see #getSolicitationHandler
     *
     *  @param solicitationHandler The solicitation handler.
     */
    public void setSolicitationHandler(SolicitationHandler solicitationHandler)
    {
        this.solicitationHandler = solicitationHandler;
    }

    /** Retrieve the <code>SolicitationHandler</code>.
     *
     *  @see #setSolicitationHandler
     *
     *  @return The solicitation handler.
     */
    public SolicitationHandler getSolicitationHandler()
    {
        return this.solicitationHandler;
    }

    public String toString()
    {
        return "[JibeSession: sessionId=" + this.sessionId + "]";
    }
}
