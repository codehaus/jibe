package org.codehaus.jibe;

import java.util.Properties;

public class JibeSessionFactoryTest
    extends JibeTestCase
{
    public void testNewSessionFactory_UsingNoProperties()
        throws Exception
    {
        try
        {
            JibeSessionFactory.newSessionFactory();
            fail( "should have thrown SessionFactoryException" );
        }
        catch (SessionFactoryException e)
        {
            // expected and correct
        }
    }

    public void testNewSessionFactory_UsingSystemProperties()
        throws Exception
    {
        System.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                            LocalSessionFactory.class.getName() );
        
        JibeSessionFactory factory = JibeSessionFactory.newSessionFactory();
        
        assertNotNull( factory );

        System.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                            "deadbeef" );
    }

    public void testNewSessionFactory_NotAssignable()
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           Proposal.class.getName() );
        try
        {
            JibeSessionFactory.newSessionFactory( props );
            fail( "should have thrown SessionFactoryException" );
        }
        catch (SessionFactoryException e)
        {
            // expected and correct
        }
    }

    public void testNewSessionFactory_ClassNotFound()
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           "com.goober.CheeseHead" );

        try
        {
            JibeSessionFactory.newSessionFactory( props );
            fail( "should have thrown SessionFactoryException" );
        }
        catch (SessionFactoryException e)
        {
            // expected and correct
        }
    }

    public void testFactoryCaching()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        props.setProperty( JibeSessionFactory.FACTORY_NAME_PROPERTY,
                           "cheese" );

        JibeSessionFactory factory = JibeSessionFactory.newSessionFactory( props );

        assertNotNull( factory );

        Properties propsToo = new Properties();

        propsToo.setProperty( JibeSessionFactory.FACTORY_NAME_PROPERTY,
                              "cheese" );

        JibeSessionFactory factoryToo = JibeSessionFactory.newSessionFactory( propsToo );

        assertNotNull( factoryToo );

        assertSame( factory,
                    factoryToo );
    }
}
