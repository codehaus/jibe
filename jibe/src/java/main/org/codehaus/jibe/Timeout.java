package org.codehaus.jibe;

import java.util.TimerTask;

class Timeout
    extends TimerTask
{
    private InProgressProposal inProgress;

    Timeout(InProgressProposal inProgress)
    {
        this.inProgress = inProgress;
    }

    public void run()
    {
        this.inProgress.handleTimeout();
    }
}
