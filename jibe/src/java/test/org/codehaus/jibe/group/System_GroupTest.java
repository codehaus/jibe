package org.codehaus.jibe.group;

import org.codehaus.jibe.JibeSessionFactory;
import org.codehaus.jibe.LocalSessionFactory;
import org.codehaus.jibe.JibeSystemTestCase;

import org.sysunit.SystemTestCase;

import java.util.Arrays;
import java.util.Properties;

public class System_GroupTest
    extends JibeSystemTestCase
{
    public void threadOne()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        Group group = Group.join( "node1",
                                  "the.group",
                                  props );

        assertNotNull( group );

        sync( "joining" );

        Thread.sleep( 12000 );

        System.err.println( "ONE: " + Arrays.asList( group.getMembers() ) );

        //assertLength( 2,
                      //group.getMembers() );
    }

    public void threadTwo()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        Group group = Group.join( "node2",
                                  "the.group",
                                  props );
        assertNotNull( group );

        sync( "joining" );

        Thread.sleep( 12000 );

        System.err.println( "TWO: " + Arrays.asList( group.getMembers() ) );

        //assertLength( 2,
                      //group.getMembers() );
    }

    public void threadThree()
        throws Exception
    {
        Properties props = new Properties();

        props.setProperty( JibeSessionFactory.SESSION_FACTORY_PROPERTY,
                           LocalSessionFactory.class.getName() );

        Group group = Group.join( "node3",
                                  "the.group",
                                  props );

        assertNotNull( group );

        sync( "joining" );

        Thread.sleep( 12000 );

        System.err.println( "THREE: " + Arrays.asList( group.getMembers() ) );
        // assertLength( 2,
        // group.getMembers() );
    }
}
