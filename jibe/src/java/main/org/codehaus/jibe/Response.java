package org.codehaus.jibe;

/*
 $Id: Response.java,v 1.1.1.1 2003-06-26 04:27:53 bob Exp $

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

/** Response to a <code>Solicitation</code>.
 *
 *  @see Solicitation
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: Response.java,v 1.1.1.1 2003-06-26 04:27:53 bob Exp $
 */
public interface Response
{
    // ----------------------------------------------------------------------
    //     Constants
    // ----------------------------------------------------------------------

    /** Empty <code>Response</code> array. */
    public static final Response[] EMPTY_ARRAY = new Response[0];

    /** Unresponded-thus-far (initial) status. */
    static final int UNRESPONDED = 0;

    /** Normal response status possibly with payload. */
    static final int NORMAL = 1;

    /** Active abstinance response status. */
    static final int ACTIVE_ABSTAIN = 2;

    /** Passive abstinance response status. */
    static final int PASSIVE_ABSTAIN = 3;

    /** Error response status. */
    static final int ERROR = 4;

    // ----------------------------------------------------------------------
    //     Interface
    // ----------------------------------------------------------------------

    /** Retrieve the status.
     *
     *  @see #UNRESPONDED
     *  @see #NORMAL
     *  @see #ACTIVE_ABSTAIN
     *  @see #PASSIVE_ABSTAIN
     *  @see #ERROR
     *
     *  @return The status.
     */
    int getStatus();

    /** Retrieve the payload.
     *
     *  <p>
     *  This is only valid for {@link #NORMAL} responses.
     *  </p>
     *
     *  @see #getStatus
     *  @see #NORMAL
     *
     *  @return The response payload.
     */
    Object getPayload();
}
