package org.codehaus.jibe;

public class DefaultResponseTest
    extends JibeTestCase
{
    public void testConstruct()
    {
        Response response = new DefaultResponse( Response.NORMAL,
                                             "cheese" );

        assertEquals( Response.NORMAL,
                      response.getStatus() );

        assertEquals( "cheese",
                      response.getPayload() );
    }
}
