package org.codehaus.jibe.group;

import java.io.Serializable;

public class Member
    implements Serializable, Comparable
{
    public static final Member[] EMPTY_ARRAY = new Member[0];

    private String id;
    
    public Member(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return this.id;
    }

    public int compareTo(Object that)
    {
        if ( that instanceof Member )
        {
            return getId().compareTo( ((Member)that).getId() );
        }

        return 0;
    }

    public String toString()
    {
        return "[Member: id=" + getId() + "]";
    }

    public boolean equals(Object that)
    {
        if ( that instanceof Member )
        {
            return getId().equals( ((Member)that).getId() );
        }

        return false;
    }
}
