package org.codehaus.jibe.group;

import java.io.Serializable;

public class Member
    implements Serializable
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
}
