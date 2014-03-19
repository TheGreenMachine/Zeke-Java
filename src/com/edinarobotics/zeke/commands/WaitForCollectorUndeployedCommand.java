package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;
import edu.wpi.first.wpilibj.command.Command;

public class WaitForCollectorUndeployedCommand extends Command {
    private Collector collector;
    
    public WaitForCollectorUndeployedCommand() {
        collector = Components.getInstance().collector;
        setInterruptible(false);
        setTimeout(4.5);
        requires(collector);
    }
    
    protected void initialize() {
        collector.setDeployed(Collector.CollectorState.RETRACT);
    }

    protected void execute() {
        collector.setDeployed(Collector.CollectorState.RETRACT);
    }

    protected boolean isFinished() {
        return collector.getUndeployed() || isTimedOut();
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
