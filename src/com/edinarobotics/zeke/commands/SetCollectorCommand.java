package com.edinarobotics.zeke.commands;

import com.edinarobotics.zeke.Components;
import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Collector.CollectorState;
import com.edinarobotics.zeke.subsystems.Collector.CollectorWheelState;
import edu.wpi.first.wpilibj.command.Command;

public class SetCollectorCommand extends Command {
    
    private Collector collector;
    private CollectorState collectorState;
    private CollectorWheelState collectorWheelState;
    
    {
        this.collector = Components.getInstance().collector;
        requires(this.collector);
    }
    
    public SetCollectorCommand(CollectorState collectorState, CollectorWheelState collectorWheelState) {
        this.collectorState = collectorState;
        this.collectorWheelState = collectorWheelState;
    }
    
    public SetCollectorCommand(CollectorState collectorState) {
        this.collectorState = collectorState;
    }
    
    public SetCollectorCommand(CollectorWheelState collectorWheelState) {
        this.collectorWheelState = collectorWheelState;
    }
    
    protected void initialize() {
        if(collectorState != null) {
            collector.setDeployed(collectorState);
        }
        if(collectorWheelState != null) {
            collector.setWheelState(collectorWheelState);
        }
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
