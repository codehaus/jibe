package org.codehaus.jibe.group;

import java.util.List;
import java.util.ArrayList;

public class GroupListMessage
    extends BaseMessage
{
    private List members;

    public GroupListMessage(Member[] members)
    {
        this.members = new ArrayList();

        for ( int i = 0 ; i < members.length ; ++i )
        {
            this.members.add( members[i] );
        }
    }

    public Member[] getMembers()
    {
        return (Member[]) this.members.toArray( Member.EMPTY_ARRAY );
    }
}
