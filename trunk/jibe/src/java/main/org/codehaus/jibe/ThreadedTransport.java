package org.codehaus.jibe;

/*
 $Id: ThreadedTransport.java,v 1.3 2003-07-04 22:42:55 bob Exp $

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

/** Abstract <code>Transport</code> which spawns threads to handle
 *  <code>Solicitation</code>s and <code>Response</code>s.
 *
 *  @see #handle(JibeSession,Solicitation)
 *  @see #handle(JibeSession,Solicitation)
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: ThreadedTransport.java,v 1.3 2003-07-04 22:42:55 bob Exp $
 */
public abstract class ThreadedTransport
    implements Transport
{
    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------

    /** Construct.
     */
    protected ThreadedTransport()
    {
        // intentionally left blank
    }

    // ----------------------------------------------------------------------
    //     Instance methods
    // ----------------------------------------------------------------------

    /** Handle a <code>Solicitation</code> in a new thread.
     *
     *  @param session The session.
     *  @param solicitation The solicitation to handle.
     */
    protected void handle(final SolicitationHandler handler,
                          final Solicitation solicitation)
        throws TransportException
    {
        final Barrier barrier = new Barrier();

        Thread handlerThread = new Thread()
            {
                public void run()
                {
                    try
                    {
                        barrier.reach();
                        System.err.println( "solicitationhandler handle solicitation " + solicitation.getProposal().getPayload() );
                        handler.handle( solicitation );
                    }
                    catch (Exception e)
                    {
                        try
                        {
                            solicitation.error( e.getMessage() );
                        }
                        catch (ResponseException e2)
                        {
                            e2.printStackTrace();
                        }
                    }
                }
            };

        handlerThread.start();

        try
        {
            barrier.waitOn();
        }
        catch (InterruptedException e)
        {
            throw new TransportException( this,
                                          e );
        }
    }

    /** Handle a <code>Response</code> in a new thread.
     *
     *  @param responseHandler The response handler.
     *  @param response The response.
     */
    protected void handle(final ResponseHandler responseHandler,
                          final Response response)
        throws TransportException
    {
        final Barrier barrier = new Barrier();

        Thread handlerThread = new Thread()
            {
                public void run()
                {
                    barrier.reach();
                    System.err.println( "responsehandler handle resposne " + response.getPayload() );
                    responseHandler.handle( response );
                }
            };

        handlerThread.start();

        try
        {
            barrier.waitOn();
        }
        catch (InterruptedException e)
        {
            throw new TransportException( this,
                                          e );
        }
    }

    protected void handle(final SolicitationHandler handler,
                          final Outcome outcome)
        throws TransportException
    {
        final Barrier barrier = new Barrier();

        Thread handlerThread = new Thread()
            {
                public void run()
                {
                    barrier.reach();
                    System.err.println( "solicitationhandler handle outcome " + outcome.getPayload() );
                    handler.handle( outcome );
                }
            };

        handlerThread.start();

        System.err.println( "waiting on barrier" );
        try
        {
            barrier.waitOn();
        }
        catch (InterruptedException e)
        {
            throw new TransportException( this,
                                          e );
        }

        System.err.println( "done waiting on barrier" );
    }
}
