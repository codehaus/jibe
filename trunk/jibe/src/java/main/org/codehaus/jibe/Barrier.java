package org.codehaus.jibe;

class Barrier
{
    private boolean reached;

    Barrier()
    {
        this.reached = false;
    }

    synchronized void waitOn()
        throws InterruptedException
    {
        while ( ! this.reached )
        {
            wait();
        }
    }

    synchronized void reach()
    {
        this.reached = true;
        notifyAll();
    }
}
