package org.codehaus.jibe;

/*
 $Id: JibeSessionFactory.java,v 1.3 2003-07-04 22:42:55 bob Exp $

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

import java.util.Properties;

import java.util.Map;
import java.util.HashMap;

/** Abstract factory for <code>JibeSession</code> instances.
 *
 *  @see JibeSession
 *
 *  @author <a href="mailto:bob@codehaus.org">bob mcwhirter</a>
 *
 *  @version $Id: JibeSessionFactory.java,v 1.3 2003-07-04 22:42:55 bob Exp $
 */
public abstract class JibeSessionFactory
{
    // ----------------------------------------------------------------------
    //     Constants
    // ----------------------------------------------------------------------

    /** <code>JibeSessionFactory</code> implementation configuration property. */
    public static final String SESSION_FACTORY_PROPERTY = JibeSessionFactory.class.getName();

    /** <code>JibeSessionFactory</code> instance name property. */
    public static final String FACTORY_NAME_PROPERTY = "jibe.factory.name";

    private static final Map factories = new HashMap();

    /** Transport used by the factory. */
    private Transport transport;

    private int counter;


    // ----------------------------------------------------------------------
    //     Constructors
    // ----------------------------------------------------------------------
    
    /** Construct.
     */
    protected JibeSessionFactory()
    {
        this.counter = 0;
    }

    // ----------------------------------------------------------------------
    //     Instance methods
    // ----------------------------------------------------------------------

    /** Initialize the <code>Transport</code> to be used by the factory.
     *
     *  @param properties The configuration properties.
     *
     *  @return The initialized transport.
     *
     *  @throws SessionFactoryException If an error occurs while attempting
     *          to initialize the transport.
     */
    protected abstract Transport initializeTransport(Properties properties)
        throws SessionFactoryException;

    /** Internal hook for transport initialization.
     *
     *  @param properties The configuration properties.
     *
     *  @throws SessionFactoryException If an error occurs while attempting
     *          to initialize the transport.
     */
    private void internalInitializeTransport(Properties properties)
        throws SessionFactoryException
    {
        this.transport = initializeTransport( properties );
    }

    /** Create a new <code>JibeSession</code>.
     *
     *  @return The new session.
     *
     *  @throws SessionException If an error occurs while attempting to create
     *          and initialize the session.
     */
    public JibeSession newSession()
        throws SessionException
    {
        return newSession( "session." + (++this.counter) );
    }

    public JibeSession newSession(String sessionId)
        throws SessionException
    {
        JibeSession session = new JibeSession( this.transport,
                                               sessionId );

        try
        {
            this.transport.register( session );
            
            return session;
        }
        catch (TransportException e)
        {
            throw new SessionException( e );
        }
    }

    /** Create a new <code>JibeSessionFactory</code> using
     *  system properties.
     *
     *  @see System#getProperties
     *
     *  @return The new session factory.
     *
     *  @throws SessionFactoryException If an error occurs while attempting
     *          to load or instantiate the session factory.
     */
    public static final JibeSessionFactory newSessionFactory()
        throws SessionFactoryException
    {
        return newSessionFactory( System.getProperties() );
    }

    /** Create a new <code>JibeSessionFactory</code> using properties
     *
     *  @param properties Configuration properties.
     *
     *  @return The new session factory.
     *
     *  @throws SessionFactoryException If an error occurs while attempting
     *          to load or instantiate the session factory.
     */
    public static synchronized final JibeSessionFactory newSessionFactory(Properties properties)
        throws SessionFactoryException
    {
        String factoryName = properties.getProperty( FACTORY_NAME_PROPERTY );

        JibeSessionFactory factory = null;

        if ( factoryName != null )
        {
            factory = lookupFactory( properties.getProperty( FACTORY_NAME_PROPERTY ) );
        }

        if ( factory == null )
        {
            String factoryClassName = properties.getProperty( SESSION_FACTORY_PROPERTY );

            if ( factoryClassName == null
                 ||
                 factoryClassName.trim().equals( "" ) )
            {
                throw new SessionFactoryException( "property " + SESSION_FACTORY_PROPERTY + " not set" );
            }
            
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            
            if ( cl == null )
            {
                cl = JibeSessionFactory.class.getClassLoader();
            }
            
            try
            {
                Class factoryClass = cl.loadClass( factoryClassName );
                
                if ( ! JibeSessionFactory.class.isAssignableFrom( factoryClass ) )
                {
                    throw new SessionFactoryException( factoryClass.getName() + " is not a subclass of JibeSessionFactory" );
                }
                
                factory = (JibeSessionFactory) factoryClass.newInstance();
                
                factory.internalInitializeTransport( properties );
            }
            catch (ClassNotFoundException e)
            {
                throw new SessionFactoryException( e );
            }
            catch (InstantiationException e)
            {
                throw new SessionFactoryException( e );
            }
            catch (IllegalAccessException e)
            {
                throw new SessionFactoryException( e );
            }

            if ( factoryName != null )
            {
                registerFactory( factoryName,
                                 factory );
            }
        }

        return factory;
    }

    protected static void registerFactory(String name,
                                          JibeSessionFactory factory)
    {
        factories.put( name,
                       factory );
    }

    protected static JibeSessionFactory lookupFactory(String name)
    {
        return (JibeSessionFactory) factories.get( name );
    }
}
