package org.codehaus.jibe;

/*
 $Id: DefaultSolicitation.java,v 1.2 2003-07-04 22:42:55 bob Exp $

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

/** Default <code>Solicitation</code> implementation.
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: DefaultSolicitation.java,v 1.2 2003-07-04 22:42:55 bob Exp $
 */
public class DefaultSolicitation
    implements Solicitation
{
    // ----------------------------------------------------------------------
    //     Instance members
    // ----------------------------------------------------------------------

    /** Transport. */
    private Transport transport;

    /** Proposal. */
    private Proposal proposal;

    /** Response status flag. */
    private int status;

    /** Response payload. */
    private Object payload;

    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------

    /** Construct.
     *
     *  @param transport The transport to use.
     *  @param proposal The proposal.
     */
    public DefaultSolicitation(Transport transport,
                               Proposal proposal)
    {
        this.transport = transport;
        this.status    = Response.UNRESPONDED;
        this.proposal  = proposal;
    }

    // ----------------------------------------------------------------------
    //     Instance methods
    // ----------------------------------------------------------------------

    /** Retrieve the <code>Transport</code>.
     *
     *  @return The transport.
     */
    public Transport getTransport()
    {
        return this.transport;
    }

    /** @see Solicitation
     */
    public Proposal getProposal()
    {
        return this.proposal;
    }

    /** @see Solicitation
     */
    public void respond(Object payload)
        throws ResponseException
    {
        setStatus( Response.NORMAL );
        setPayload( payload );

        try
        {
            transportResponse();
        }
        catch (TransportException e)
        {
            throw new ResponseException( getProposal(),
                                         e );
        }
    }

    /** @see Solicitation
     */
    public void abstain()
        throws ResponseException
    {
        setStatus( Response.ACTIVE_ABSTAIN );

        try
        {
            transportResponse();
        }
        catch (TransportException e)
        {
            throw new ResponseException( getProposal(),
                                         e );
        }
    }

    /** @see Solicitation
     */
    public void abstain(Object payload)
        throws ResponseException
    {
        setStatus( Response.ACTIVE_ABSTAIN );
        setPayload( payload );

        try
        {
            transportResponse();
        }
        catch (TransportException e)
        {
            throw new ResponseException( getProposal(),
                                         e );
        }
    }

    /** Signal a passive abstinance.
     *
     *  @throws ResponseException If the status has already been set.
     */
    public void passiveAbstain()
        throws ResponseException
    {
        setStatus( Response.PASSIVE_ABSTAIN );

        try
        {
            transportResponse();
        }
        catch (TransportException e)
        {
            throw new ResponseException( getProposal(),
                                         e );
        }
    }

    /** Signal an error response.
     *
     *  @param message The error message.
     *
     *  @throws ResponseException If the status has already been set.
     */
    public void error(String message)
        throws ResponseException
    {
        setStatus( Response.ERROR );
        setPayload( message );

        try
        {
            transportResponse();
        }
        catch (TransportException e)
        {
            throw new ResponseException( getProposal(),
                                         e );
        }
    }

    /** Set the response status.
     *
     *  @param status The status.
     *
     *  @throws ResponseException If the status has already been set.
     */
    public void setStatus(int status)
        throws ResponseException
    {
        checkFirstResponse();
        this.status = status;
    }

    /** @see Response
     */
    public int getStatus()
    {
        return this.status;
    }

    /** Set the response payload.
     *
     *  @param payload The response payload.
     */
    public void setPayload(Object payload)
    {
        this.payload = payload;
    }

    /** @see Response
     */
    public Object getPayload()
    {
        return this.payload;
    }

    /** Verify that this is the first response.
     *
     *  @throws ResponseException If not.
     */
    protected void checkFirstResponse()
        throws ResponseException
    {
        if ( getStatus() != Response.UNRESPONDED )
        {
            throw new ResponseException( getProposal(),
                                         "already responded" );
        }
    }

    /** Transport the response.
     *
     *  @throws TransportException If an error occurs while attempting
     *          to transport the response.
     */
    protected void transportResponse()
        throws TransportException
    {
        getTransport().respond( this,
                                getStatus(),
                                getPayload() );
    }
}
