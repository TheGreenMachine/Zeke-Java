package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;
import edu.wpi.first.wpilibj.command.Command;

public class CollectorDeployCommand extends Command {
    private Collector collector;

    public CollectorDeployCommand() {
        this.collector = Components.getInstance().collector;
        setInterruptible(true);
        setTimeout(2.0);
        requires(collector);
    }
    
    protected void initialize() {
        collector.setDeployed(Collector.CollectorState.DEPLOYING);
    }

    protected void execute() {
        collector.setDeployed(Collector.CollectorState.DEPLOYING);
    }

    protected boolean isFinished() {
        return !collector.getState().equals(Collector.CollectorState.DEPLOYING) || isTimedOut();
    }

    protected void end() {
        if(collector.getState().equals(Collector.CollectorState.DEPLOYING)) {
            collector.setDeployed(Collector.CollectorState.DEPLOYED);
        }
    }

    protected void interrupted() {
        end();
    }
    
}
