package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.vision.VisionServer;
import edu.wpi.first.wpilibj.command.Command;

public class WaitForVisionCommand extends Command {
    private VisionServer visionServer;
    
    private int hotGoalCounts;
    
    /**
     * Creates a new command that waits until the hot goal count becomes greater
     * than the number specified, or gets timed out. Vision data is read from
     * socket port 1180.
     * @param hotGoalCounts The minimum samples of hot goals that should be
     * counted. The count increases every 50 ms when a signal is being received.
     * Thus, a count of 20 implies 20*50 = 1000 ms (1 second).
     * @param timeout The time after which this command times out.
     */
    public WaitForVisionCommand(int hotGoalCounts, double timeout) {
        visionServer = Components.getInstance().visionServer;
        this.hotGoalCounts = hotGoalCounts;
        setInterruptible(true);
        setTimeout(timeout);
    }
    
    /**
     * Creates a new command that waits until the hot goal count becomes greater
     * than the number specified, or gets timed out.
     * @param visionPort The socket port to read from. (default = 1180)
     * @param hotGoalCounts The minimum samples of hot goals that should be
     * counted. The count increases every 50 ms when a signal is being received.
     * Thus, a count of 20 implies 20*50 = 1000 ms (1 second).
     * @param timeout The time after which this command times out.
     */
    public WaitForVisionCommand(int visionPort, int hotGoalCounts, double timeout) {
        this(hotGoalCounts, timeout);
        visionServer.setPort(visionPort);
    }
    
    protected void initialize() {
        visionServer.start();
        visionServer.startSamplingCounts();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return visionServer.getHotGoalCount() > hotGoalCounts || isTimedOut();
    }

    protected void end() {
        visionServer.stop();
    }

    protected void interrupted() {
        end();
    }
}
