package org.codehaus.jibe.group;

import org.codehaus.jibe.JibeSessionFactory;
import org.codehaus.jibe.LocalSessionFactory;

import org.sysunit.SystemTestCase;

import java.util.Properties;

public class System_GroupTest
    extends SystemTestCase
{
    public void threadOne()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        Group group = Group.join( "node.one",
                                  "the.group",
                                  props );

        assertNotNull( group );
    }

    public void threadTwo()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        Group group = Group.join( "node.two",
                                  "the.group",
                                  props );
        assertNotNull( group );
    }

    public void threadThree()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        Group group = Group.join( "node.three",
                                  "the.group",
                                  props );

        assertNotNull( group );
    }
}
