package org.codehaus.jibe;

/*
 $Id: Transport.java,v 1.2 2003-06-26 13:56:52 bob Exp $

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


/** Message transport.
 *
 *  @see JibeSessionFactory
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: Transport.java,v 1.2 2003-06-26 13:56:52 bob Exp $
 */
public interface Transport
{
    /** Register a <code>JibeSession</code> using this transport.
     *
     *  @param session The session.
     *
     *  @throws TransportException If an error occurs while attempting
     *          to register the session.
     */
    void register(JibeSession session)
        throws TransportException;

    /** Unregister a <code>JibeSession</code> from this transport.
     *
     *  @param session The session.
     *
     *  @throws TransportException If an error occurs while attempting
     *          to unregister the session.
     */
    void unregister(JibeSession session)
        throws TransportException;

    /** Distribute a <code>Proposal</code>.
     *
     *  @param proposal The proposal.
     *  @param responseHandler The response handler.
     *
     *  @throws TransportException If an error occurs while attempting
     *          to distribute the proposal.
     */
    void distribute(Proposal proposal,
                    ResponseHandler responseHandler)
        throws TransportException;

    /** Send a <code>Response</code> to a <code>Soliciation</code>.
     *
     *  @param solicitation The solicitation being responded to.
     *  @param status The response status.
     *  @param responsePaylod The response payload.
     *
     *  @see Response
     *
     *  @throws TransportException If an error occurs while attempting
     *          to distribute the proposal.
     */
    void respond(Solicitation solicitation,
                 int status,
                 Object responsePaylod)
        throws TransportException;

    void distribute(Outcome outcome)
        throws TransportException;
}
