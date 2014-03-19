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
        setInterruptible(true);
        setTimeout(2.0);
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
        if(collectorState != null) {
            collector.setDeployed(collectorState);
        }
        if(collectorWheelState != null) {
            collector.setWheelState(collectorWheelState);
        }
    }

    protected boolean isFinished() {
        if(collectorState != null){
            return !collectorState.equals(CollectorState.DEPLOY) || isTimedOut();
        }
        return true;
    }

    protected void end() {
        if(collectorState != null && collectorState.equals(CollectorState.DEPLOY)){
            collector.setDeployed(CollectorState.VALVE_VENT);
        }
    }

    protected void interrupted() {
    }
    
}
