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
            if(!(collectorState.equals(CollectorState.DEPLOY) && collector.getState().equals(CollectorState.VALVE_VENT))){
                collector.setDeployed(collectorState);
                if(collectorState.equals(CollectorState.DEPLOY)){
                    new InternalValveCommand(collector).start();
                }
            }
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
    
    private static class InternalValveCommand extends Command {
        private Collector collector;
        
        public InternalValveCommand(Collector collector) {
            this.collector = collector;
            setTimeout(1.0);
        }
        
        protected void initialize() {
        }

        protected void execute() {
        }

        protected boolean isFinished() {
            CollectorState collectorState = collector.getState();
            if(collectorState != null) {
                return !collectorState.equals(CollectorState.DEPLOY) || isTimedOut();
            }
            return true;
        }

        protected void end() {
            CollectorState collectorState = collector.getState();
            if(collectorState != null && collectorState.equals(CollectorState.DEPLOY)) {
                collector.setDeployed(CollectorState.VALVE_VENT);
            }
        }

        protected void interrupted() {
        }
    }
}
