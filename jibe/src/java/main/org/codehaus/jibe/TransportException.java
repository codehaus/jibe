package org.codehaus.jibe;

/*
 $Id: TransportException.java,v 1.1.1.1 2003-06-26 04:27:53 bob Exp $

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

/** Indicates an error with a <code>Transport</code>.
 *
 *  @see Transport
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: TransportException.java,v 1.1.1.1 2003-06-26 04:27:53 bob Exp $
 */
public class TransportException
    extends JibeException
{
    // ----------------------------------------------------------------------
    //     Instance members
    // ----------------------------------------------------------------------

    /** Transport in error. */
    private Transport transport;

    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------

    /** Construct.
     *
     *  @param transport The transport in error.
     */
    public TransportException(Transport transport)
    {
        this.transport = transport;
    }

    /** Construct.
     *
     *  @param transport The transport in error.
     *  @param message The error message.
     */
    public TransportException(Transport transport,
                              String message)
    {
        super( message );
        this.transport = transport;
    }

    /** Construct.
     *
     *  @param transport The transport in error.
     *  @param rootCause The root cause.
     */
    public TransportException(Transport transport,
                              Throwable rootCause)
    {
        super( rootCause );
        this.transport = transport;
    }

    // ----------------------------------------------------------------------
    //    Instance methods
    // ----------------------------------------------------------------------

    /** Retrieve the <code>Transport</code> in error.
     *
     *  @return The transport.
     */
    public Transport getTransport()
    {
        return this.transport;
    }
}
