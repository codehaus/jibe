package org.codehaus.jibe;

/*
 $Id: LocalTransport.java,v 1.3 2003-07-01 19:13:18 bob Exp $

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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/** Local (single JVM) transport.
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: LocalTransport.java,v 1.3 2003-07-01 19:13:18 bob Exp $
 */
public class LocalTransport
    extends ThreadedTransport
{
    // ----------------------------------------------------------------------
    //     Instance members
    // ----------------------------------------------------------------------

    /** Registered sessions. */
    private List sessions;

    /** Response-handlers, indexed by solicitation. */
    private Map responseHandlers;

    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------

    /** Construct.
     */
    protected LocalTransport()
    {
        this.sessions         = new ArrayList();
        this.responseHandlers = new HashMap();
    }

    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------

    /** @see Transport
     */
    public synchronized void register(JibeSession session)
        throws TransportException
    {
        this.sessions.add( session );
    }

    /** @see Transport
     */
    public synchronized void unregister(JibeSession session)
        throws TransportException
    {
        while ( this.sessions.remove( session ) )
        {
            // intentionally left blank
        }
    }

    public JibeSession[] getRegisteredSessions()
    {
        return (JibeSession[]) this.sessions.toArray( JibeSession.EMPTY_ARRAY );
    }

    /** @see Transport
     */
    public synchronized void distribute(Proposal proposal,
                                        ResponseHandler responseHandler)
        throws TransportException
    {
        for ( Iterator sessionIter = this.sessions.iterator();
              sessionIter.hasNext(); )
        {
            JibeSession session = (JibeSession) sessionIter.next();
            DefaultSolicitation solicitation = new DefaultSolicitation( this,
                                                                        proposal );

            registerResponseHandler( solicitation,
                                     responseHandler );

            SolicitationHandler handler = session.getSolicitationHandler();

            if ( handler != null )
            {
                handle( handler,
                        solicitation );
            }
        }
    }

    /** @see Transport
     */
    public synchronized void respond(Solicitation solicitation,
                                     int status,
                                     Object responsePayload)
        throws TransportException
    {
        ResponseHandler responseHandler = lookupResponseHandler( solicitation );

        if ( responseHandler == null )
        {
            throw new TransportException( this,
                                          "no response handler for " + solicitation );
        }

        unregisterResponseHandler( solicitation );

        handle( responseHandler,
                new DefaultResponse( status,
                                     responsePayload ) );
    }

    /** @see Transport
     */
    public synchronized void distribute(Outcome outcome)
        throws TransportException
    {
        for ( Iterator sessionIter = this.sessions.iterator();
              sessionIter.hasNext(); )
        {
            JibeSession session = (JibeSession) sessionIter.next();

            SolicitationHandler handler = session.getSolicitationHandler();

            if ( handler != null )
            {
                handle( handler,
                        outcome );
            }
        }
    }
    

    protected void registerResponseHandler(Solicitation solicitation,
                                           ResponseHandler handler)
    {
        this.responseHandlers.put( solicitation,
                                   handler );
    }

    protected ResponseHandler lookupResponseHandler(Solicitation solicitation)
    {
        return (ResponseHandler) this.responseHandlers.get( solicitation );
    }

    protected void unregisterResponseHandler(Solicitation solicitation)
    {
        this.responseHandlers.remove( solicitation );
    }
}
