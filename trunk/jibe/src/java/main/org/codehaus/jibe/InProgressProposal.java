package org.codehaus.jibe;

/*
 $Id: InProgressProposal.java,v 1.3 2003-07-01 19:13:18 bob Exp $

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

/** Book-keeping for in-progress <code>Proposal</code>s.
 *
 *  @see Proposal
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: InProgressProposal.java,v 1.3 2003-07-01 19:13:18 bob Exp $
 */
class InProgressProposal
    implements ResponseHandler
{
    // ----------------------------------------------------------------------
    //     Instance members
    // ----------------------------------------------------------------------

    /** Session. */
    private JibeSession session;

    /** Proposal. */
    private Proposal proposal;

    /** Termination determiner. */
    private Termination termination;

    /** Outcome adjudicator. */
    private Adjudicator adjudicator;

    /** Responses. */
    private MutableResponseSet responses;

    /** Termination state flag. */
    private boolean terminated;

    /** Timeout. */
    private long timeout;

    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------

    /** Construct.
     *
     *  @param session The session.
     *  @param proposal The proposal.
     *  @param termination The termination determiner.
     *  @param adjudicator The outcome adjudicator.
     */
    InProgressProposal(JibeSession session,
                       Proposal proposal,
                       Termination termination,
                       Adjudicator adjudicator)
    {
        this( session,
              proposal,
              termination,
              adjudicator,
              -1 );
    }

    /** Construct.
     *
     *  @param session The session.
     *  @param proposal The proposal.
     *  @param termination The termination determiner.
     *  @param adjudicator The outcome adjudicator.
     *  @param long Timeout.
     */
    InProgressProposal(JibeSession session,
                       Proposal proposal,
                       Termination termination,
                       Adjudicator adjudicator,
                       long timeout)
    {
        this.session     = session;
        this.proposal    = proposal;
        this.termination = termination;
        this.adjudicator = adjudicator;
        this.responses   = new DefaultResponseSet();
        this.terminated  = false;
        this.timeout     = timeout;

        registerTimeout();
    }

    // ----------------------------------------------------------------------
    //     Instance methods
    // ----------------------------------------------------------------------

    long getTimeout()
    {
        return this.timeout;
    }

    void registerTimeout()
    {
        if ( getTimeout() <= 0 )
        {
            return;
        }
    }

    /** Retrieve the <code>JibeSession</code>.
     *
     *  @return The session.
     */
    JibeSession getSession()
    {
        return this.session;
    }

    /** Retrieve the <code>Proposal</code>.
     *
     *  @return The proposal.
     */
    Proposal getProposal()
    {
        return this.proposal;
    }

    /** Retrieve the <code>Termination</code> determiner.
     *
     *  @return The termination determiner.
     */
    Termination getTermination()
    {
        return this.termination;
    }

    /** Retrieve the outcome <code>Adjudicator</code>.
     *
     *  @return The adjudicator.
     */
    Adjudicator getAdjudicator()
    {
        return this.adjudicator;
    }

    boolean isTerminated()
    {
        return this.terminated;
    }

    ResponseSet getResponses()
    {
        return this.responses;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

    /** @see ResponseHandler
     */
    public void handle(Response response)
    {
        synchronized ( this )
        {
            if ( this.terminated )
            {
                return;
            }
            
            this.responses.add( response );

            if ( getTermination().shouldTerminate( this.responses ) )
            {
                this.terminated = true;
            }
        }

        if ( this.terminated )
        {
            Outcome outcome = getAdjudicator().adjudicate( this.responses );

            try
            {
                getSession().getTransport().distribute( outcome );
            }
            catch (TransportException e)
            {
                e.printStackTrace();
            }
        }
    }
}
